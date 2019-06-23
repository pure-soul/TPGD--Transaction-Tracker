
/**
 * Implemented by Rhadjni Phipps
 */


package com.example.rhadjni.lifeplanner.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.R;

public class ExpenseViewHolder extends MyRecyclerView.ViewHolder {

    public TextView name;
    public TextView amount;
    public ImageView  deleteExpense;
    public ImageView editExpense;

    public ExpenseViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        amount = (TextView)itemView.findViewById(R.id.amount_value);
        deleteExpense = (ImageView)itemView.findViewById(R.id.delete_product);

    }
}
