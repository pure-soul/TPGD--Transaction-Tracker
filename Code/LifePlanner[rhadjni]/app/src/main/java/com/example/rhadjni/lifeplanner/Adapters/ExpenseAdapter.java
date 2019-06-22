/**
 * Implemented by Rhadjni Phipps
 */

package com.example.rhadjni.lifeplanner.Adapters;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;
import com.example.rhadjni.lifeplanner.Tables.Expenses;
import com.example.rhadjni.lifeplanner.Tables.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.rhadjni.lifeplanner.R.id.incomeshow;
import static com.example.rhadjni.lifeplanner.R.id.task_submit;

public class ExpenseAdapter extends MyRecyclerView.Adapter<ExpenseViewHolder>{

    private Context context;
    private List<Expenses> listExpenses;
    private String chekstate;

    private SqliteDatabase mDatabase;
    Calendar mcalendar = Calendar.getInstance();


    public ExpenseAdapter(Context context, List<Expenses> listExpenses) {
        this.context = context;
        this.listExpenses = listExpenses;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_layout, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        final Expenses singleExpense = listExpenses.get(position);

        holder.name.setText(  singleExpense.getName());
        holder.amount.setText("Expense Amount : " +String.valueOf(singleExpense.getAmount()));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editExpenseDialog( singleExpense);
            }
        });
        holder.amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editExpenseDialog( singleExpense);
            }
        });


        holder.deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(((Activity)context)
                        );

                // set title
                alertDialogBuilder.setTitle("Delete");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you really want to delete this transaction?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, clear DB
                                //  refresh current activity
                                mDatabase.deleteExpense( singleExpense.getId());

                                //refresh the activity page.
                                ((Activity)context).finish();
                                context.startActivity(((Activity) context).getIntent());


                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }


    private void editExpenseDialog(final Expenses expense){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_expense_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final EditText incomefield = (EditText)subView.findViewById(R.id.incomeshow);
        final CheckBox cheks=(CheckBox)subView.findViewById(R.id.paycheck);




        if(expense != null){
            nameField.setText(expense.getName());
            quantityField.setText( String.valueOf(expense.getAmount()));
            incomefield.setText(String.valueOf(expense.getTotalincome()));
            chekstate=expense.getChecker();
            if (chekstate.equals("CK")){
                cheks.setChecked(true);
            }
            else{
                cheks.setChecked(false);
            }



        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Expense");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Save Expense", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final float quantity = Float.parseFloat(quantityField.getText().toString());
                //final String datentered = datebutton.getText().toString();




                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else if (cheks.isChecked()){
                    mDatabase.updateExpense(new Expenses(expense.getId(),name,quantity,expense.getTotalincome(),"CK"));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());

                }
                else{
                    mDatabase.updateExpense(new Expenses(expense.getId(),name,quantity,expense.getTotalincome(),"NCK"));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, " cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
