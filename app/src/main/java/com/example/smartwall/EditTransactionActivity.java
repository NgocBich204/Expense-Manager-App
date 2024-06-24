package com.example.smartwall;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditTransactionActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private EditText editTextCategory, editTextTotal, editTextDate;
    private Button buttonSave, buttonCancel;

    private DatabaseReference databaseReference;
    private String transactionType;
    private String transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        // Initialize views
        textViewTitle = findViewById(R.id.textView_title);
        editTextCategory = findViewById(R.id.editText_category);
        editTextTotal = findViewById(R.id.editText_total);
        editTextDate = findViewById(R.id.editText_date);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get transactionType and transactionId from Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionType = bundle.getString("transactionType");
            transactionId = bundle.getString("transactionId");
            editTextCategory.setText(bundle.getString("category"));
            editTextTotal.setText(bundle.getString("total"));
            editTextDate.setText(bundle.getString("date"));
            textViewTitle.setText(bundle.getString("title"));
        }

        // Disable editing for textViewTitle
        textViewTitle.setEnabled(false);

        // Set event listener for Save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });

        // Set event listener for Cancel button
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set event listener for Date EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void updateTransaction() {
        String newCategory = editTextCategory.getText().toString().trim();
        String newTotal = editTextTotal.getText().toString().trim();
        String newDate = editTextDate.getText().toString().trim();

        // Validate and update transaction
        if (!newCategory.isEmpty() && !newTotal.isEmpty() && !newDate.isEmpty()) {
            DatabaseReference transactionRef = databaseReference.child(transactionType).child(transactionId);
            transactionRef.child("category").setValue(newCategory);
            transactionRef.child("total").setValue(newTotal);
            transactionRef.child("date").setValue(newDate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditTransactionActivity.this, "Cập nhật giao dịch thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditTransactionActivity.this, "Cập nhật giao dịch thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
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
                editTextDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
