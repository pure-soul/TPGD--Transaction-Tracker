/**
 * Implemented by Jason Manning
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;
import com.example.rhadjni.lifeplanner.Tables.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.rhadjni.lifeplanner.R.id.task_submit;

public class ProductAdapter extends MyRecyclerView.Adapter<ProductViewHolder>{

    private Context context;
    private List<Product> listProducts;
    private String realdate;

    private SqliteDatabase mDatabase;
    Calendar mcalendar = Calendar.getInstance();

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
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
                                mDatabase.deleteProduct(singleProduct.getId());

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
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final Button datebutton=(Button)subView.findViewById(task_submit);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mcalendar.set( Calendar.YEAR,year);
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
                new DatePickerDialog( context,date,mcalendar.get(Calendar.YEAR),mcalendar.get(Calendar.MONTH),
                        mcalendar.get(Calendar.DAY_OF_MONTH )).show();
            }
        });

        if(product != null){
            nameField.setText(product.getName());
            quantityField.setText( String.valueOf(product.getQuantity()));
            datebutton.setText("Transaction Date : "+product.getPurchasedate());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Transaction");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Save Transaction", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final float quantity = Float.parseFloat(quantityField.getText().toString());
                //final String datentered = datebutton.getText().toString();
                final String datentered=realdate;




                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateProduct(new Product(product.getId(), name, quantity,datentered));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
