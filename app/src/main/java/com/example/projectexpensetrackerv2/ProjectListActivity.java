package com.example.projectexpensetrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {

    private ListView listView;
    private ProjectDatabaseHelper dbHelper;
    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list);

        listView = findViewById(R.id.listViewProjects);
        dbHelper = new ProjectDatabaseHelper(this);

        // Removed listView.setOnItemClickListener because ProjectAdapter handles routing

        findViewById(R.id.btnAddProject).setOnClickListener(v -> {
            Intent intent = new Intent(ProjectListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        refreshList();
    }

    private void refreshList() {
        // 1. Get all projects from the DatabaseHelper
        List<Project> projectList = dbHelper.getAllProjects();

        // 2. Setup the adapter with the list of projects
        adapter = new ProjectAdapter(this, projectList);

        // 3. Attach the adapter to the ListView
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list every time we come back to this screen
        refreshList();
    }

    public void showDeleteDialog(Project project) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Project")
                .setMessage("Are you sure you want to delete \"" + project.getName() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteProject(project.getId());
                    Toast.makeText(this, "Project deleted", Toast.LENGTH_SHORT).show();
                    refreshList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}