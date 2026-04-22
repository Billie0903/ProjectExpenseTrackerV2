package com.example.projectexpensetrackerv2.models;


public class Project {
    private int id;
    private String projectCode;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String manager;
    private String status;
    private double budget;

    // Empty constructor (required for some operations)
    public Project() {}

    // Constructor with all fields
    public Project(int id, String projectCode, String name, String description,
                   String startDate, String endDate, String manager,
                   String status, double budget) {
        this.id = id;
        this.projectCode = projectCode;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
        this.status = status;
        this.budget = budget;
    }

    // Getters and Setters (Required to access private data)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProjectCode() { return projectCode; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
}
