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

public class IncomeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Expenses> incomeList;

    public IncomeAdapter(Context context, ArrayList<Expenses> incomeList) {
        this.context = context;
        this.incomeList = incomeList;
    }

    @Override
    public int getCount() {
        return incomeList != null ? incomeList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return incomeList != null ? incomeList.get(position) : null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_income, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            viewHolder.tvCategoryIncome = convertView.findViewById(R.id.tvCategoryIncome);
            viewHolder.tvDateIncome = convertView.findViewById(R.id.tvDateIncome);
            viewHolder.tvTotalIncome = convertView.findViewById(R.id.tvTotalIncome);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expenses income = incomeList.get(position);

        viewHolder.tvCategoryIncome.setText(income.getCategory());
        viewHolder.tvDateIncome.setText(income.getDate());
        viewHolder.tvTotalIncome.setText("+" + income.getTotal() + " VND");

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView tvCategoryIncome;
        TextView tvDateIncome;
        TextView tvTotalIncome;
    }
}
