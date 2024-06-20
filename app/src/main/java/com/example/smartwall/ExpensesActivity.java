package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Expenses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExpensesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle, editTextTotal;
    private Spinner spinnerCategories;
    private CalendarView calendarView;
    private Button buttonSubmit;
    private ImageView imageViewBack;

    private String selectedDate;
    private DatabaseReference databaseExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expenses);

        // Initialize Firebase Database
        databaseExpenses = FirebaseDatabase.getInstance().getReference("expenses");

        // Find Views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextTotal = findViewById(R.id.editTextTotal);
        spinnerCategories = findViewById(R.id.spinner_categories);
        calendarView = findViewById(R.id.calendarView2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        imageViewBack = findViewById(R.id.imageViewBack);

        // Setup Spinner with Custom Adapter
        String[] items = new String[]{"Mua sắm", "Đồ ăn", "Thuê nhà", "Hóa đơn"};
        SpinnerAdapter adapter = new SpinnerAdapter(this, items);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(this);

        // Calendar View Listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        // Button Click Listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

        // Back button listener
        imageViewBack.setOnClickListener(view -> {
            Intent intent = new Intent(ExpensesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addExpense() {
        String title = editTextTitle.getText().toString().trim();
        String total = editTextTotal.getText().toString().trim();
        String category = spinnerCategories.getSelectedItem().toString();

        if (title.isEmpty() || total.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseExpenses.push().getKey();
        Expenses expense = new Expenses(id, title, total, category, selectedDate);

        databaseExpenses.child(id).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ExpensesActivity.this, "Khoản chi đã được thêm", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    editTextTitle.setText("");
                    editTextTotal.setText("");
                    spinnerCategories.setSelection(0);
                    calendarView.setDate(System.currentTimeMillis(), false, true);

                    // Quay lại MainActivity và thông báo đã thêm khoản chi
                    Intent intent = new Intent(ExpensesActivity.this, MainActivity.class);
                    intent.putExtra("addedExpense", true);
                    startActivity(intent);
                } else {
                    Toast.makeText(ExpensesActivity.this, "Thêm khoản chi thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // No action needed
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // No action needed
    }
}
