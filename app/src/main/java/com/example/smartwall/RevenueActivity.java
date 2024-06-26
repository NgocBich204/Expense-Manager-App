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

import com.example.smartwall.model.Income;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RevenueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle, editTextTotal;
    private Spinner spinnerCategories;
    private CalendarView calendarView;
    private Button buttonSubmit;
    private ImageView imageViewBack;

    private String selectedDate;
    private DatabaseReference databaseIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_evenue);

        // Initialize Firebase Database
        databaseIncome = FirebaseDatabase.getInstance().getReference("income");

        // Find Views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextTotal = findViewById(R.id.editTextTotal);
        spinnerCategories = findViewById(R.id.spinner_categories);
        calendarView = findViewById(R.id.calendarView2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        imageViewBack = findViewById(R.id.imageViewBackToHome);

        // Setup Spinner with Custom  Adapter
        String[] items = new String[]{"Lương", "Thưởng", "Tiền lãi", "Khác"};
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
                addIncome();
            }
        });

        // Back button listener
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng RevenueActivity và quay lại MainActivity
            }
        });
    }

    private void addIncome() {
        String title = editTextTitle.getText().toString().trim();
        String total = editTextTotal.getText().toString().trim();
        String category = spinnerCategories.getSelectedItem().toString();

        if (title.isEmpty() || total.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseIncome.push().getKey();
        Income income = new Income(id, title, total, category, selectedDate);

        databaseIncome.child(id).setValue(income).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RevenueActivity.this, "Khoản thu đã được thêm", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    editTextTitle.setText("");
                    editTextTotal.setText("");
                    spinnerCategories.setSelection(0);
                    calendarView.setDate(System.currentTimeMillis(), false, true);

                    // Trả về kết quả thành công và đóng Activity
                    Intent intent = new Intent();
                    intent.putExtra("addedIncome", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(RevenueActivity.this, "Thêm khoản thu thất bại", Toast.LENGTH_SHORT).show();
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
