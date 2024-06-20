package com.example.smartwall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartwall.model.Expenses;

import java.util.ArrayList;

public class ExpenseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Expenses> expenseList;

    public ExpenseAdapter(Context context, ArrayList<Expenses> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            viewHolder.tvCategoryExpense = convertView.findViewById(R.id.tvCategoryExpense);
            viewHolder.tvDateExpense = convertView.findViewById(R.id.tvDateExpense);
            viewHolder.tvTotalExpense = convertView.findViewById(R.id.tvTotalExpense);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expenses expense = expenseList.get(position);

        viewHolder.tvCategoryExpense.setText(expense.getCategory());
        viewHolder.tvDateExpense.setText(expense.getDate());
        viewHolder.tvTotalExpense.setText("-" + expense.getTotal() + " VND");

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView tvCategoryExpense;
        TextView tvDateExpense;
        TextView tvTotalExpense;
    }
}
