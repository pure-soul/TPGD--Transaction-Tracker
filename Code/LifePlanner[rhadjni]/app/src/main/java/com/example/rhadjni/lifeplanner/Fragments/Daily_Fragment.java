/**
 * Implemented by Jason Manning
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rhadjni.lifeplanner.Activities.MainActivity;
import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.Tables.Product;
import com.example.rhadjni.lifeplanner.Adapters.ProductAdapter;
import com.example.rhadjni.lifeplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.rhadjni.lifeplanner.R.id.task_submit;

public class Daily_Fragment extends Fragment {
    private MyRecyclerView recyclerView;

    private static final String TAG = MainActivity.class.getSimpleName();
    private String realdate;
    private SqliteDatabase mDatabase;
    final Fragment frag=this;
    Calendar mcalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_daily, container, false);

        FrameLayout fLayout = (FrameLayout) view.findViewById(R.id.activity_to_do);

        MyRecyclerView productView = (MyRecyclerView)view.findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });
        //RecyclerView productView = (RecyclerView)view.findViewById(R.id.product_list);
        mDatabase = new SqliteDatabase(getContext());
        List<Product> allProducts = mDatabase.dateOrderProducts();

        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(getContext(), allProducts);
            productView.setAdapter(mAdapter);

        }else {
            productView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "There are no Transactions in the Transactions Tracker.", Toast.LENGTH_LONG).show();
        }

        return view;
    }



    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final Button datebutton=(Button)subView.findViewById(task_submit);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mcalendar.set(Calendar.YEAR,year);
                mcalendar.set(Calendar.MONTH,month);
                mcalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String myform="YYYY-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myform, Locale.US );
                datebutton.setText( "Transaction Date : "+ sdf.format( mcalendar.getTime()));
                realdate=sdf.format( mcalendar.getTime());


            }
        };
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog( getContext(),date,mcalendar.get(Calendar.YEAR),mcalendar.get(Calendar.MONTH),
                        mcalendar.get(Calendar.DAY_OF_MONTH )).show();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add new transaction");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD TRANSACTION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final float quantity = Float.parseFloat(quantityField.getText().toString());
                //final String datentered = datebutton.getText().toString();
                final String datentered=realdate;

                if(name.isEmpty() || quantityField.getText().length()<=0){
                    Toast.makeText(getContext(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Product newProduct = new Product(name, quantity,datentered);
                    mDatabase.addProduct(newProduct);
                    FragmentTransaction frg=getFragmentManager().beginTransaction();
                    frg.detach(frag).attach(frag).commit();

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Transaction Cancelled", Toast.LENGTH_LONG).show();
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
    private void updateLabel(){
        Button datebutton=(Button)getView().findViewById( task_submit );
        String myform="MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myform, Locale.US );
        datebutton.setText( "The date is : "+ sdf.format( mcalendar.getTime()));
    }



}

