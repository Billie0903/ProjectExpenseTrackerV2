package com.example.projectexpensetrackerv2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class ProjectDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ProjectTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column Names
    public static final String TABLE_PROJECTS = "projects";
    public static final String COL_ID = "id";
    public static final String COL_CODE = "project_code";
    public static final String COL_NAME = "name";
    public static final String COL_DESC = "description";
    public static final String COL_START = "start_date";
    public static final String COL_END = "end_date";
    public static final String COL_MANAGER = "manager";
    public static final String COL_STATUS = "status";
    public static final String COL_BUDGET = "budget";

    public ProjectDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Enable foreign key support for CASCADE DELETE
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Project Table
        String createProjectTable = "CREATE TABLE " + TABLE_PROJECTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CODE + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_START + " TEXT, " +
                COL_END + " TEXT, " +
                COL_MANAGER + " TEXT, " +
                COL_STATUS + " TEXT, " +
                COL_BUDGET + " REAL)";

        db.execSQL(createProjectTable);

        // Note: Expense table creation will be added here in Section 5
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    //CRud Operations Here - FEAT B
    // Method to insert a new project
    public long addProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_CODE, project.getProjectCode());
        cv.put(COL_NAME, project.getName());
        cv.put(COL_DESC, project.getDescription());
        cv.put(COL_START, project.getStartDate());
        cv.put(COL_END, project.getEndDate());
        cv.put(COL_MANAGER, project.getManager());
        cv.put(COL_STATUS, project.getStatus());
        cv.put(COL_BUDGET, project.getBudget());

        return db.insert(TABLE_PROJECTS, null, cv);
    }

    //Getting all projects ffs
    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROJECTS, null);

        if (cursor.moveToFirst()) {
            do {
                Project project = new Project(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8)
                );
                projectList.add(project);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return projectList;
    }
    // Delete Func
    public void deleteProject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECTS, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}