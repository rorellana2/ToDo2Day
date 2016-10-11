package edu.orangecoastcollege.cs273.rorellana2.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW(TEMPORARY) delete the old database, then create a new one
        this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Let's make a DBHelper reference:
        DBHelper db = new DBHelper(this);

        // let's make a new task and add to database:
        db.addTask(new Task(1, "Study for 273 Midterm", 0));
        db.addTask(new Task(2, "Wash Car", 0));
        db.addTask(new Task(3, "Walk Dog", 0));
        db.addTask(new Task(4, "Study for Math Exam", 0));
        db.addTask(new Task(5, "Go Surfing with Brother", 0));

        // Let's get all the tasks from the database and print them
        // with Log.i();
        ArrayList<Task> allTasks = db.getAllTasks();

        // Loop through each task, point to Log.i
        for (Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());
    }
}
