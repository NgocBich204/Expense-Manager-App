package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Expenses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnAddIncome, btnAddExpense;
    private ListView listViewIncome, listViewExpense;
    private TextView totalIncomeTextView, totalExpenseTextView;

    private IncomeAdapter incomeAdapter;
    private ExpenseAdapter expenseAdapter;
    private ArrayList<Expenses> incomeList;
    private ArrayList<Expenses> expenseList;

    private DatabaseReference databaseReference;

    private int totalIncome = 0;
    private int totalExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Khởi tạo các view
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        listViewIncome = findViewById(R.id.listViewIncome);
        listViewExpense = findViewById(R.id.listViewExpense);
        totalIncomeTextView = findViewById(R.id.totalIncomeTextView);
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView);

        // Khởi tạo danh sách và adapter cho thu nhập và chi phí
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(this, incomeList);
        expenseAdapter = new ExpenseAdapter(this, expenseList);

        // Gán adapter cho ListView
        listViewIncome.setAdapter(incomeAdapter);
        listViewExpense.setAdapter(expenseAdapter);

        // Tham chiếu đến Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Lấy dữ liệu thu nhập từ Firebase
        databaseReference.child("income").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeList.clear();
                totalIncome = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses income = dataSnapshot.getValue(Expenses.class);
                    if (income != null) {
                        try {
                            int incomeTotal = Integer.parseInt(income.getTotal());
                            incomeList.add(income);
                            totalIncome += incomeTotal;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Lỗi khi chuyển đổi tổng thu nhập: " + income.getTotal(), e);
                        }
                    }
                }
                totalIncomeTextView.setText(String.format("+ %,d VND", totalIncome));
                incomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi lấy dữ liệu thu nhập!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lấy dữ liệu chi phí từ Firebase
        databaseReference.child("expenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseList.clear();
                totalExpense = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses expense = dataSnapshot.getValue(Expenses.class);
                    if (expense != null) {
                        try {
                            int expenseTotal = Integer.parseInt(expense.getTotal());
                            expenseList.add(expense);
                            totalExpense += expenseTotal;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Lỗi khi chuyển đổi tổng khoản chi: " + expense.getTotal(), e);
                        }
                    }
                }
                totalExpenseTextView.setText(String.format("- %,d VND", totalExpense));
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi lấy dữ liệu chi phí!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm Thu nhập"
        btnAddIncome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RevenueActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm Chi phí"
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(intent);
            }
        });
        // Đặt sự kiện onClick cho ListView thu nhập
        listViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TransactionDetailsActivity.class);
                // truyen du lieu sang TransactionDetailsActivity thông qua putExtra.
                intent.putExtra("id" , 1);
                startActivity(intent);
            }
        });

        // Đặt sự kiện onClick cho ListView chi tiêu
        listViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi item trên listViewExpense được chọn
                // Ví dụ:
                Toast.makeText(MainActivity.this, "Selected: ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
