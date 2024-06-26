package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Expenses;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        // Đặt adapter cho ListViews
        listViewIncome.setAdapter(incomeAdapter);
        listViewExpense.setAdapter(expenseAdapter);

        // Tham chiếu tới Firebase Realtime Database
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
                            Log.e(TAG, "Error parsing income total: " + income.getTotal(), e);
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
                            Log.e(TAG, "Error parsing expense total: " + expense.getTotal(), e);
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

        // Xử lý sự kiện nhấn nút "Thêm Thu Nhập"
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nhấn nút "Thêm Chi Phí"
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện nhấn cho ListView thu nhập
        listViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expenses selectedIncome = incomeList.get(position);
                navigateToTransactionDetails(selectedIncome, "income");
            }
        });

        // Thiết lập sự kiện nhấn cho ListView chi phí
        listViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expenses selectedExpense = expenseList.get(position);
                navigateToTransactionDetails(selectedExpense, "expenses");
            }
        });

        // Khởi tạo và thiết lập sự kiện nhấn cho BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    Log.d(TAG, "Selected menu item: Home");
                    // Đang ở MainActivity, không cần điều hướng
                    return true;
                } else if (id == R.id.wallet) {
                    Log.d(TAG, "Selected menu item: Wallet");
                    Intent intentWallet = new Intent(MainActivity.this, SavingsActivity.class);
                    startActivity(intentWallet);
                    return true;
                } else if (id == R.id.budget) {
                    Log.d(TAG, "Selected menu item: Budget");
                    Intent intentLog = new Intent(MainActivity.this, LogActivity.class);
                    startActivity(intentLog);
                    return true;
                } else if (id == R.id.user) {
                    Log.d(TAG, "Selected menu item: User");
                    Intent intentUser = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intentUser);
                    return true;
                }
                return false;
            }
        });
    }

    // Phương thức điều hướng đến chi tiết giao dịch
    private void navigateToTransactionDetails(Expenses transaction, String transactionType) {
        Intent intent = new Intent(MainActivity.this, TransactionDetailsActivity.class);
        intent.putExtra("transactionType", transactionType);
        intent.putExtra("transactionId", transaction.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottmmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            Log.d("MainActivity", "Selected menu item: Home");
            // Đang ở MainActivity, không cần điều hướng
            return true;
        } else if (id == R.id.wallet) {
            Log.d("MainActivity", "Selected menu item: Wallet");
            Intent intentWallet = new Intent(MainActivity.this, SavingsActivity.class);
            startActivity(intentWallet);
            return true;
        } else if (id == R.id.budget) {
            Log.d("MainActivity", "Selected menu item: Budget");
            Intent intentLog = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intentLog);
            return true;
        } else if (id == R.id.user) {
            Log.d("MainActivity", "Selected menu item: User");
            Intent intentUser = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intentUser);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
