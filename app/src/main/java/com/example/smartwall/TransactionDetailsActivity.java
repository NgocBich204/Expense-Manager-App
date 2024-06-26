// TransactionDetailsActivity.java
package com.example.smartwall;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Expenses;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TransactionDetailsActivity extends AppCompatActivity {
    private Button buttonClose, buttonEdit, buttonDelete;
    private TextView textViewCategory, textViewDate, textViewTitle, textViewTotal;

    private DatabaseReference databaseReference;
    private String transactionType;
    private String transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        // Initialize views
        buttonClose = findViewById(R.id.button_close);
        buttonEdit = findViewById(R.id.button_edit);
        buttonDelete = findViewById(R.id.button_delete);
        textViewCategory = findViewById(R.id.textView_category);
        textViewDate = findViewById(R.id.textView_date);
        textViewTitle = findViewById(R.id.textView_title);
        textViewTotal = findViewById(R.id.editText_total);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get transactionType and transactionId from Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionType = bundle.getString("transactionType");
            transactionId = bundle.getString("transactionId");

            if (transactionType != null && transactionId != null) {
                fetchTransactionDetails(transactionType, transactionId);
            } else {
                Log.e("TransactionDetailsActivity", "Transaction type or ID is missing");
            }
        } else {
            Log.e("TransactionDetailsActivity", "Intent extras (bundle) is null");
        }

        // Set event listener for Close button
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set event listener for Edit button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionDetailsActivity.this, EditTransactionActivity.class);
                intent.putExtra("transactionType", transactionType);
                intent.putExtra("transactionId", transactionId);
                intent.putExtra("category", textViewCategory.getText().toString());
                intent.putExtra("total", textViewTotal.getText().toString());
                intent.putExtra("date", textViewDate.getText().toString());
                intent.putExtra("title", textViewTitle.getText().toString());

                startActivity(intent);
            }
        });

        // Set event listener for Delete button
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        // Set event listener for Date TextView
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void fetchTransactionDetails(String transactionType, String transactionId) {
        // Define the path based on transaction type
        DatabaseReference transactionRef = databaseReference.child(transactionType).child(transactionId);

        transactionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Expenses transaction = snapshot.getValue(Expenses.class);
                if (transaction != null) {
                    // Display transaction details in your TextView or other UI elements
                    textViewCategory.setText(transaction.getCategory());
                    textViewTotal.setText(String.valueOf(transaction.getTotal())); // Ensure total is displayed correctly
                    textViewDate.setText(transaction.getDate());
                    textViewTitle.setText(transaction.getTitle());
                } else {
                    Log.e("TransactionDetailsActivity", "Transaction not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TransactionDetailsActivity.this, "Error fetching transaction details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa giao dịch")
                .setMessage("Bạn có chắc chắn muốn xóa giao dịch này?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTransaction();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteTransaction() {
        DatabaseReference transactionRef = databaseReference.child(transactionType).child(transactionId);
        transactionRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TransactionDetailsActivity.this, "Xóa giao dịch thành công", Toast.LENGTH_SHORT).show();
                        finish(); // or navigate back to previous screen
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TransactionDetailsActivity", "Failed to delete transaction", e);
                        Toast.makeText(TransactionDetailsActivity.this, "Failed to delete transaction", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                textViewDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
