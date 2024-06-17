package com.example.smartwall;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartwall.model.Expenses;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EvenueActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private EditText editTextTitle, editTextTotal;
    private Spinner spinnerCategories;
    private CalendarView calendarView;
    private Button buttonSubmit;

    private String selectedDate;

    private DatabaseReference databaseExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_revenue);
        databaseExpenses = FirebaseDatabase.getInstance().getReference("evenue");

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextTotal = findViewById(R.id.editTextTotal);
        spinnerCategories = findViewById(R.id.spinner_categories);
        calendarView = findViewById(R.id.calendarView2);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        String[] items = new String[]{"Lương", "tip", "Chưng khoán", "sổ số"};
        SpinnerAdapter adapter = new SpinnerAdapter(this, items);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(this);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvenue();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_revenue), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvenue() {
        String title = editTextTitle.getText().toString().trim();
        String total = editTextTotal.getText().toString().trim();
        String category = spinnerCategories.getSelectedItem().toString();

        if (title.isEmpty() || total.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseExpenses.push().getKey();
        Expenses expense = new Expenses(id, title, total, category, selectedDate);

        databaseExpenses.child(id).setValue(expense).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Khoản thu đã được thêm", Toast.LENGTH_SHORT).show();
                // Reset fields
                editTextTitle.setText("");
                editTextTotal.setText("");
                spinnerCategories.setSelection(0);
                calendarView.setDate(System.currentTimeMillis(), false, true);
            } else {
                Toast.makeText(this, "Thêm thu chi thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}