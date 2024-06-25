package com.example.smartwall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class createSavingActivity extends AppCompatActivity {

    private EditText editStartDate, editEndDate, editGoalName, editGoalAmount, editNotes;
    private Button btnAddGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_saving);

        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editGoalName = findViewById(R.id.editGoalName);
        editGoalAmount = findViewById(R.id.editGoalAmount);
        editNotes = findViewById(R.id.editNotes);
        btnAddGoal = findViewById(R.id.btnAddGoal);
//        editStartDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(editStartDate);
//            }
//        });
//
//        editEndDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog(editEndDate);
//            }
//        });
//

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createSaving), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}