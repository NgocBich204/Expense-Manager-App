package com.example.smartwall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartwall.model.Goal;

import java.util.List;

public class GoalAdapter extends BaseAdapter {

    private Context context;
    private List<Goal> goalList;

    public GoalAdapter(Context context, List<Goal> goalList) {
        this.context = context;
        this.goalList = goalList;
    }

    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_goal, parent, false);
        }

        TextView textViewGoalName = convertView.findViewById(R.id.textViewGoalName);
        TextView textViewGoalAmount = convertView.findViewById(R.id.textViewGoalAmount);
        TextView textViewStartDate = convertView.findViewById(R.id.textViewStartDate);
        TextView textViewEndDate = convertView.findViewById(R.id.textViewEndDate);

        Goal goal = goalList.get(position);
        textViewGoalName.setText(goal.getGoalName());
        textViewGoalAmount.setText(goal.getGoalAmount());
        textViewStartDate.setText(goal.getStartDate());
        textViewEndDate.setText(goal.getEndDate());

        return convertView;
    }
}