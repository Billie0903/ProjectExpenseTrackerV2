package com.example.projectexpensetrackerv2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class ProjectAdapter extends ArrayAdapter<Project> {

    public ProjectAdapter(Context context, List<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.project_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvProjectName);
        TextView tvCode = convertView.findViewById(R.id.tvProjectCode);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        TextView tvBudget = convertView.findViewById(R.id.tvBudget);
        ImageView ivEdit = convertView.findViewById(R.id.ivEdit);
        ImageView ivDelete = convertView.findViewById(R.id.ivDelete);

        // Populate the data into the template view using the Project object
        if (project != null) {
            tvName.setText(project.getName());
            tvCode.setText("Code: " + project.getProjectCode());
            tvStatus.setText("Status: " + project.getStatus());
            // Fixed the formatting string (removed the '目' character)
            tvBudget.setText("£" + String.format("%.2f", project.getBudget()));

            // 1. VIEW DETAILS: Clicking the whole card
            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
                intent.putExtra("PROJECT_ID", project.getId());
                getContext().startActivity(intent);
            });

            ivEdit.setOnClickListener(v -> {
                // We will handle the Edit logic here later
                Intent intent = new Intent(getContext(), EditProjectActivity.class);
                intent.putExtra("PROJECT_ID", project.getId());
                getContext().startActivity(intent);
            });


            ivDelete.setOnClickListener(v -> {
                // This calls the delete logic we discussed earlier
                if (getContext() instanceof ProjectListActivity) {
                    ((ProjectListActivity) getContext()).showDeleteDialog(project);
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
        return null;
    }
}
