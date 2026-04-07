package com.example.projectexpensetrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // Declare view variables
    private TextInputLayout tilProjectId, tilProjectName;
    private EditText etProjectId, etProjectName, etDescription, etStartDate, etEndDate, etManager, etBudget;
    private Spinner spinnerStatus;
    private Button btnSave, btnViewAll;
    private ProjectDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Database Helper
        dbHelper = new ProjectDatabaseHelper(this);

        // Connect views to variables
        initViews();
        // Setup the Project Status Spinner
        setupStatusSpinner();

        // Setup submit button
        btnSave.setOnClickListener(v -> {
            if (validateInputs()) {
                saveToDatabase();
            }
        });

        // Setup view all button (will lead to ProjectListActivity in Section 4)
        btnViewAll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProjectListActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        tilProjectId = findViewById(R.id.tilProjectId);
        tilProjectName = findViewById(R.id.tilProjectName);
        etProjectId = findViewById(R.id.etProjectId);
        etProjectName = findViewById(R.id.etProjectName);
        etDescription = findViewById(R.id.etDescription);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etManager = findViewById(R.id.etManager);
        etBudget = findViewById(R.id.etBudget);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSave = findViewById(R.id.btnSave);
        btnViewAll = findViewById(R.id.btnViewAll);
    }

    private void setupStatusSpinner() {
        String[] statusOptions = {"Active", "Completed", "On Hold"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private boolean validateInputs() {
        boolean valid = true;

        // Requirement: Fields must show error if empty
        if (etProjectId.getText().toString().trim().isEmpty()) {
            etProjectId.setError("Project ID is required");
            valid = false;
        }
        if (etProjectName.getText().toString().trim().isEmpty()) {
            etProjectName.setError("Project Name is required");
            valid = false;
        }
        if (etBudget.getText().toString().trim().isEmpty()) {
            etBudget.setError("Budget is required");
            valid = false;
        }

        return valid;
    }
    // Save to data to DB
    private void saveToDatabase() {
        // 1. Collect data from fields
        String code = etProjectId.getText().toString().trim();
        String name = etProjectName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String start = etStartDate.getText().toString().trim();
        String end = etEndDate.getText().toString().trim();
        String manager = etManager.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();
        double budget = Double.parseDouble(etBudget.getText().toString().trim());

        // 2. Create Project object with data
        Project newProject = new Project(0, code, name, desc, start, end, manager, status, budget);

        // 3. Use Canvas Database Helper to save
        long id = dbHelper.addProject(newProject);

        if (id != -1) {
            Toast.makeText(this, "Project Saved Successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Failed to save project", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etProjectId.setText("");
        etProjectName.setText("");
        etDescription.setText("");
        etStartDate.setText("");
        etEndDate.setText("");
        etManager.setText("");
        etBudget.setText("");
        spinnerStatus.setSelection(0);
    }
}