package edu.orangecoastcollege.cs273.rorellana2.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day";
    static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    // gets called when we upgrade the Schemas or the design.
    // deletes the old table and create a new one. Plus migrate
    // all data to new one.
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    // Create a method to add a brand new task to the database:
    public void addTask(Task newTask)
    {
        // Step 1) Create a reference to our database:
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // Step 3) Insert values into our database
        db.insert(DATABASE_TABLE, null, values);

        // Step 4) Close Database!!
        db.close();
    }

    // Create a method to get all the tasks in the database
    public ArrayList<Task> getAllTasks()
    {
        // Step 1) Create a reference to our database:
        SQLiteDatabase db = this.getReadableDatabase(); // instead of writable it is readable!

        // Step 2) Make a new empty ArrayList
        ArrayList<Task> allTasks = new ArrayList<>();

        // Step 3) Query the database for all records all rows and all fields (all columns)
        // The return type of a query is Cursor
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);


        // Step 4) Loop through the results, create Task objects, add to the ArrayList
        if(results.moveToFirst())
        {
            do {
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);
                allTasks.add(new Task(id, description, isDone));

            } while(results.moveToNext());

        }
        // Step 4) Close Database!!
        db.close();

        return allTasks;
    }

}