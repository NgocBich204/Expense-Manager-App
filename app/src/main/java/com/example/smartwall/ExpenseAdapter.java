package com.example.smartwall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartwall.model.Expenses;

import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter<Expenses> {

    // Hàm khởi tạo cho adapter, nhận vào context và danh sách chi tiêu
    public ExpenseAdapter(@NonNull Context context, ArrayList<Expenses> expenses) {
        super(context, 0, expenses);
    }

    // Lớp ViewHolder giúp tái sử dụng các view con bên trong ListView để tăng hiệu năng
    private static class ViewHolder {
        TextView tvCategoryExpense;
        TextView tvDateExpense;
        TextView tvTotalExpense;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Lấy đối tượng Expenses tại vị trí hiện tại
        Expenses expense = getItem(position);

        // Khai báo ViewHolder
        ViewHolder viewHolder;

        // Nếu convertView là null, nghĩa là chưa được tái sử dụng
        if (convertView == null) {
            // Nạp layout item_expense
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);

            // Khởi tạo ViewHolder và gán các view con
            viewHolder = new ViewHolder();
            viewHolder.tvCategoryExpense = convertView.findViewById(R.id.tvCategoryExpense);
            viewHolder.tvDateExpense = convertView.findViewById(R.id.tvDateExpense);
            viewHolder.tvTotalExpense = convertView.findViewById(R.id.tvTotalExpense);

            // Gán ViewHolder cho convertView
            convertView.setTag(viewHolder);
        } else {
            // Nếu convertView đã được tái sử dụng, lấy ViewHolder ra
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Kiểm tra xem đối tượng expense có null không và cập nhật dữ liệu vào các view con
        if (expense != null) {
            viewHolder.tvCategoryExpense.setText(expense.getCategory());
            viewHolder.tvDateExpense.setText(expense.getDate());
            viewHolder.tvTotalExpense.setText(String.format("-%s VND", expense.getTotal()));
        }

        // Trả về convertView đã được cập nhật dữ liệu
        return convertView;
    }
}
