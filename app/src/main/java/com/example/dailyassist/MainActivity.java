package com.example.dailyassist;
import android.content.DialogInterface;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailyassist.Reminder;
import com.example.dailyassist.ReminderAdapter;
import com.example.dailyassist.ReminderDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ReminderAdapter.OnReminderEditListener, ReminderAdapter.OnReminderDeleteListener {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFabAdd;
    private List<Reminder> mRemindersList;
    private ReminderAdapter mReminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily assist");



        // Initialize RecyclerView and its adapter
        mRecyclerView = findViewById(R.id.recycler_view);
        mRemindersList = new ArrayList<>(); // Initialize the list
        mReminderAdapter = new ReminderAdapter(this, mRemindersList, this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Apply item decoration (spacing) to RecyclerView
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mRecyclerView.setAdapter(mReminderAdapter);

        // Initialize FloatingActionButton and set click listener
        mFabAdd = findViewById(R.id.fab_add);
        mFabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddReminderActivity.class);
            startActivity(intent);
        });

        // Load reminders from the database and update the RecyclerView
        loadReminders();
    }

    private void loadReminders() {
        try {
            ReminderDatabaseHelper dbHelper = new ReminderDatabaseHelper(this);
            mRemindersList.clear(); // Clear the list before loading new reminders
            mRemindersList.addAll(dbHelper.getAllReminders());
            mReminderAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("MainActivity", "Error loading reminders: " + e.getMessage());
            e.printStackTrace();
            // Handle the error, display a message to the user, etc.
        }
    }

    // Inflate the menu resource file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            // Open AboutActivity when the about menu item is clicked
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.add1) {
            // Open FirstAddReminderActivity when the first "Add Reminder" menu item is clicked
            Intent intent = new Intent(MainActivity.this, FirstAddReminderActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.add2) {
            // Open SecondAddReminderActivity when the second "Add Reminder" menu item is clicked
            Intent intent = new Intent(MainActivity.this, SecondAddReminderActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    // Implement the methods from OnReminderEditListener and OnReminderDeleteListener
    @Override
    public void onReminderEdit(Reminder reminder) {
        // Handle edit reminder action
    }

    @Override
    public void onReminderDelete(Reminder reminder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this reminder?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete reminder from the database
                try {
                    ReminderDatabaseHelper dbHelper = new ReminderDatabaseHelper(MainActivity.this);
                    dbHelper.deleteReminder(reminder.getId()); // Call the deleteReminder method with the reminder ID

                    // Remove reminder from the list and update the RecyclerView
                    mRemindersList.remove(reminder);
                    mReminderAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("MainActivity", "Error deleting reminder: " + e.getMessage());
                    e.printStackTrace();
                    // Handle the error, display a message to the user, etc.
                }
            }
        });
        builder.setNegativeButton("No", null); // Do nothing if "No" is clicked
        builder.show();
    }
}


