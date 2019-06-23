/**
 * Implemented by Dolieth Chambers
 */

package com.example.rhadjni.lifeplanner.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rhadjni.lifeplanner.Fragments.Affordable_Fragment;
import com.example.rhadjni.lifeplanner.Fragments.Daily_Fragment;
import com.example.rhadjni.lifeplanner.Fragments.Expense_Fragment;
import com.example.rhadjni.lifeplanner.Fragments.GraphFragment;
import com.example.rhadjni.lifeplanner.Fragments.Income_Fragment;
import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Transaction Tracker");
        mDatabase = new SqliteDatabase(this);

        BottomNavigationView bottomNav= findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Daily_Fragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent intent2;
                intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent2);
                    return true;


            case R.id.Emptytrans:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                alertDialogBuilder.setTitle("Clear Transactions");
                alertDialogBuilder
                        .setMessage("Do you really want to delete all transactions?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mDatabase.ClearDB();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;

            case R.id.Emptyex:
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
                        this);
                alertDialogBuilder2.setTitle("Clear Expenses");
                alertDialogBuilder2
                        .setMessage("Do you really want to delete ALL expenses?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mDatabase.ClearExpensesDB();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog2 = alertDialogBuilder2.create();

                // show it
                alertDialog2.show();
                return true;



            default:

                return super.onOptionsItemSelected(item);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = new Affordable_Fragment() ;

            switch(menuItem.getItemId()){
                case R.id.daily:
                    selected= new Daily_Fragment();
                    break;

                case R.id.income:
                    selected= new Income_Fragment();
                    break;

                case R.id.affordable:
                    selected= new Affordable_Fragment();
                    break;
                case R.id.expense:
                    selected= new Expense_Fragment();
                    break;
                case R.id.graph:
                    selected= new GraphFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();
            return true;
        }
    };

}
