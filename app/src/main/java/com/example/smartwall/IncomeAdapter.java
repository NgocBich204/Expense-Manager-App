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

public class IncomeAdapter extends ArrayAdapter<Expenses> {

    // Hàm khởi tạo cho adapter, nhận vào context và danh sách thu nhập
    public IncomeAdapter(@NonNull Context context, ArrayList<Expenses> incomes) {
        super(context, 0, incomes);
    }

    // Lớp ViewHolder giúp tái sử dụng các view con bên trong ListView để tăng hiệu năng
    private static class ViewHolder {
        TextView tvCategoryIncome;
        TextView tvDateIncome;
        TextView tvTotalIncome;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Lấy đối tượng Expenses tại vị trí hiện tại
        Expenses income = getItem(position);

        // Khai báo ViewHolder
        ViewHolder viewHolder;

        // Nếu convertView là null, nghĩa là chưa được tái sử dụng
        if (convertView == null) {
            // Nạp layout item_income
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_income, parent, false);

            // Khởi tạo ViewHolder và gán các view con
            viewHolder = new ViewHolder();
            viewHolder.tvCategoryIncome = convertView.findViewById(R.id.tvCategoryIncome);
            viewHolder.tvDateIncome = convertView.findViewById(R.id.tvDateIncome);
            viewHolder.tvTotalIncome = convertView.findViewById(R.id.tvTotalIncome);

            // Gán ViewHolder cho convertView
            convertView.setTag(viewHolder);
        } else {
            // Nếu convertView đã được tái sử dụng, lấy ViewHolder ra
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Kiểm tra xem đối tượng income có null không và cập nhật dữ liệu vào các view con
        if (income != null) {
            viewHolder.tvCategoryIncome.setText(income.getCategory());
            viewHolder.tvDateIncome.setText(income.getDate());
            viewHolder.tvTotalIncome.setText(String.format("+%s VND", income.getTotal()));
        }

        // Trả về convertView đã được cập nhật dữ liệu
        return convertView;
    }
}
