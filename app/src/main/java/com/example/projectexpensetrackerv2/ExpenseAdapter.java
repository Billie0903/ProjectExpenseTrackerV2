package com.example.projectexpensetrackerv2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_item, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tvExpDate);
        TextView tvType = convertView.findViewById(R.id.tvExpType);
        TextView tvClaimant = convertView.findViewById(R.id.tvExpClaimant);
        TextView tvAmount = convertView.findViewById(R.id.tvExpAmount);
        TextView tvStatus = convertView.findViewById(R.id.tvExpStatus);

        if (expense != null) {
            tvDate.setText(expense.getDate());
            tvType.setText(expense.getType());
            tvClaimant.setText("By: " + expense.getClaimant());
            tvAmount.setText(expense.getCurrency() + " " + String.format("%.2f", expense.getAmount()));
            tvStatus.setText(expense.getPaymentStatus());
        }

        return convertView;
    }
}
