package com.example.projectexpensetrackerv2.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.projectexpensetrackerv2.R;
import com.example.projectexpensetrackerv2.database.ProjectDatabaseHelper;
import com.example.projectexpensetrackerv2.models.Project;

public class EditProjectActivity extends AppCompatActivity {

    private EditText etName, etCode, etDesc, etStart, etEnd, etManager, etBudget;
    private Spinner spinnerStatus;
    private Button btnUpdate;
    private int projectId;
    private ProjectDatabaseHelper dbHelper;
    private String[] statusOptions = {"Active", "Completed", "On Hold"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        dbHelper = new ProjectDatabaseHelper(this);

        // Retrieve the ID passed from the previous screen
        projectId = getIntent().getIntExtra("PROJECT_ID", -1);

        initViews();
        setupToolbar();
        setupStatusSpinner();
        loadProjectData();

        btnUpdate.setOnClickListener(v -> updateData());
    }

    private void initViews() {
        etName = findViewById(R.id.etProjectName);
        etCode = findViewById(R.id.etProjectId);
        etDesc = findViewById(R.id.etDescription);
        etStart = findViewById(R.id.etStartDate);
        etEnd = findViewById(R.id.etEndDate);
        etManager = findViewById(R.id.etManager);
        etBudget = findViewById(R.id.etBudget);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Project");
        }
        // Handle the Back icon click
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupStatusSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void loadProjectData() {
        Project p = dbHelper.getProjectById(projectId);
        if (p != null) {
            etName.setText(p.getName());
            etCode.setText(p.getProjectCode());
            etDesc.setText(p.getDescription());
            etStart.setText(p.getStartDate());
            etEnd.setText(p.getEndDate());
            etManager.setText(p.getManager());
            etBudget.setText(String.valueOf(p.getBudget()));

            // Set Spinner to the correct status
            for (int i = 0; i < statusOptions.length; i++) {
                if (statusOptions[i].equalsIgnoreCase(p.getStatus())) {
                    spinnerStatus.setSelection(i);
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Error loading project", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateData() {
        // 1. Collect updated data
        String name = etName.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String start = etStart.getText().toString().trim();
        String end = etEnd.getText().toString().trim();
        String manager = etManager.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();
        String budgetStr = etBudget.getText().toString().trim();

        if (name.isEmpty() || code.isEmpty() || budgetStr.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double budget = Double.parseDouble(budgetStr);

        // 2. Create updated Project object
        Project updatedProject = new Project(projectId, code, name, desc, start, end, manager, status, budget);

        // 3. Update in Database
        int result = dbHelper.updateProject(updatedProject);

        if (result > 0) {
            Toast.makeText(this, "Project Updated Successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and return to details
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
