package com.example.projectexpensetrackerv2;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {

    private ListView listView;
    private ProjectDatabaseHelper dbHelper;
    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using the name you provided: project_list.xml
        setContentView(R.layout.project_list);

        listView = findViewById(R.id.listViewProjects);
        dbHelper = new ProjectDatabaseHelper(this);

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
}