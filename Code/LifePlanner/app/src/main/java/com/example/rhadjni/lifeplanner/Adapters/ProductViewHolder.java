/**
 * Implemented by Jason Manning
 */


package com.example.rhadjni.lifeplanner.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhadjni.lifeplanner.MyRecyclerView;
import com.example.rhadjni.lifeplanner.R;

public class ProductViewHolder extends MyRecyclerView.ViewHolder {

    public TextView name;
    public ImageView deleteProduct;
    public ImageView editProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
    }
}
