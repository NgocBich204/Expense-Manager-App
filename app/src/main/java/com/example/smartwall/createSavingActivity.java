package com.example.smartwall;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Goal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class createSavingActivity extends AppCompatActivity {

    private EditText editStartDate, editEndDate, editGoalName, editGoalAmount, editNotes;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving);

        databaseReference = FirebaseDatabase.getInstance().getReference("goals");

        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editGoalName = findViewById(R.id.editGoalName);
        editGoalAmount = findViewById(R.id.editGoalAmount);
        editNotes = findViewById(R.id.editNotes);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editStartDate);
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editEndDate);
            }
        });

        ImageView imageViewBackToHome = findViewById(R.id.imageViewBackToHome);
        imageViewBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createSavingActivity.this, MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnAddGoal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoalToFirebase();
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                createSavingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void addGoalToFirebase() {
        String goalName = editGoalName.getText().toString().trim();
        String goalAmount = editGoalAmount.getText().toString().trim();
        String startDate = editStartDate.getText().toString().trim();
        String endDate = editEndDate.getText().toString().trim();
        String notes = editNotes.getText().toString().trim();

        if (goalName.isEmpty() || goalAmount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        Goal goal = new Goal(id, goalName, goalAmount, startDate, endDate, notes);
        databaseReference.child(id).setValue(goal);

        Toast.makeText(this, "Mục tiêu đã được thêm", Toast.LENGTH_SHORT).show();

        // Sau khi thêm thành công, có thể thêm các logic tiếp theo ở đây nếu cần
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Optionally add additional logic here if needed
    }
}
