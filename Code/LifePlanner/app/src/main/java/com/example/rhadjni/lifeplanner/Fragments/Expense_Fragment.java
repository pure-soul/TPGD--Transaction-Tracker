/**
 * Implemented by Rhadjni Phipps
 */

package com.example.rhadjni.lifeplanner.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhadjni.lifeplanner.Activities.MainActivity;
import com.example.rhadjni.lifeplanner.Adapters.ExpenseAdapter;
import com.example.rhadjni.lifeplanner.Adapters.ProductAdapter;
import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;
import com.example.rhadjni.lifeplanner.Tables.Expenses;
import com.example.rhadjni.lifeplanner.Tables.Product;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.rhadjni.lifeplanner.R.id.fab;
import static com.example.rhadjni.lifeplanner.R.id.task_submit;

public class Expense_Fragment extends Fragment {
    private RecyclerView recyclerView;

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    final Fragment frag=this;
    private TextView incomeamt;
    private float incometotal;
    FloatingActionButton fab;
    Calendar mcalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_expense, container, false);

        FrameLayout fLayout = (FrameLayout) view.findViewById(R.id.activity_to_do);

        MyRecyclerView productView = (MyRecyclerView)view.findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);


         fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addExpenseDialog();
            }
        });

        incomeamt=(TextView)view.findViewById(R.id.prompter);
        incomeamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIncomeDialog();

            }
        });

        //RecyclerView productView = (RecyclerView)view.findViewById(R.id.product_list);
        mDatabase = new SqliteDatabase(getContext());
        List<Expenses> allExpenses = mDatabase.listExpenses();


        if(allExpenses.size() > 0){
            productView.setVisibility(View.VISIBLE);
            fab.show();
            float firstincome=allExpenses.get(0).getTotalincome();

            incomeamt.setText((String.valueOf(firstincome)));
            incomeamt.setVisibility(View.INVISIBLE);

            ExpenseAdapter mAdapter = new ExpenseAdapter(getContext(), allExpenses);
            productView.setAdapter(mAdapter);

        }else {

                    productView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "There are no Expenses in the Transactions Tracker.", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void addIncomeDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.add_income_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_inc);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Montlhy Income");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD Income", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                incomeamt.setText(nameField.getText());
                incomeamt.setVisibility(View.GONE);
                fab.show();




            }

        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Add Income Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }



    private void addExpenseDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.add_expense_layout, null);
        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final CheckBox chekbox=(CheckBox)subView.findViewById(R.id.paycheck);
        chekbox.setVisibility(View.GONE);






        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add new expense");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD EXPENSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final float amount = Float.parseFloat(quantityField.getText().toString());

                if(name.isEmpty() || quantityField.getText().length()<=0){
                    Toast.makeText(getContext(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    String into =incomeamt.getText().toString();
                    float totinc=Float.parseFloat(into);
                    Expenses newExpense = new Expenses(name,amount,totinc,"CK");
                    mDatabase.addExpense(newExpense);
                    FragmentTransaction frg=getFragmentManager().beginTransaction();
                    frg.detach(frag).attach(frag).commit();

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Expense Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }




}

