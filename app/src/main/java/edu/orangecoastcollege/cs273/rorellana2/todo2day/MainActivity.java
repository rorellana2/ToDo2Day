package edu.orangecoastcollege.cs273.rorellana2.todo2day;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView;
    private Button clearAllTasksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW(TEMPORARY) delete the old database, then create a new one
        //this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Let's make a DBHelper reference:
        database = new DBHelper(this);

        // Let's add one dummy task
        //database.addTask(new Task("Walk Dog", 0));

        // Let's fill the list with Tasks from the database
        taskList = database.getAllTasks();

        // Let's create our custom task list adapter
        // (We want to associate the adapter with the context,
        // the layout, and the List)
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the List View to our Layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        // Associate the adapter with the List view
        taskListView.setAdapter(taskListAdapter);

        // Connect EditText with our Layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);


    }

    public void addTask(View view)
    {
        // Get description from the EditText
        String description = taskEditText.getText().toString();
        if (description.isEmpty()) //wont check for spaces of tabs
        {
            Toast.makeText(this, "Task description cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Let's make a new task
            Task newTask = new Task(description, 0);
            // Let's add it to the list
            taskListAdapter.add(newTask);
            // Let's add it to the database
            database.addTask(newTask);

            taskEditText.setText("");
        }
    }

    public void changeTaskStatus(View view)
    {
        if (view instanceof CheckBox)
        {
            CheckBox selectedCheckBox = (CheckBox) view;
            Task selectedTask = (Task) selectedCheckBox.getTag();
            selectedTask.setIsDone(selectedCheckBox.isChecked() ? 1 : 0);
            // Update the selectedBox in the database
            database.updateTask(selectedTask);
        }
    }

    public void clearAllTasks()
    {
        // Clear the list
        taskList.clear();
        // Delete all the records (Tasks) in the database
        database.deleteAllTasks();
        // Tell the TaskListAdapter to update itself
        taskListAdapter.notifyDataSetChanged();

    }

    public void dialogEvent(View view)
    {
        Button btn = (Button) findViewById(R.id.clearAllTasksButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!taskList.isEmpty())
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setMessage("Are you sure you want to erase all Tasks?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    clearAllTasks();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = alertDialog.create();
                    alert.setTitle("Delete all Tasks");
                    alert.show();
                }
            }
        });
    }

}
