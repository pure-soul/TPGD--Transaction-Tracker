package com.example.rhadjni.lifeplanner.Adapters;
/**
 * Implemented by Dolieth Chambers
 */
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rhadjni.lifeplanner.Data.MenuItemData;
import com.example.rhadjni.lifeplanner.R;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class WheelTextAdapter extends CursorWheelLayout.CycleWheelAdapter {

    private Context mContext;
    private List<MenuItemData>menuItems;
    private LayoutInflater inflater;
    private int gravity;

    public WheelTextAdapter(Context mContext, List<MenuItemData> menuItems) {
        this.mContext = mContext;
        this.menuItems = menuItems;
        gravity = Gravity.CENTER;
        inflater=LayoutInflater.from(mContext);
    }

    public WheelTextAdapter(Context mContext, List<MenuItemData> menuItems, int gravity) {
        this.mContext = mContext;
        this.menuItems = menuItems;
        this.gravity = gravity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public View getView(View parent, int position) {
        MenuItemData itemData= getItem(position);
        View root= inflater.inflate(R.layout.wheel_text_layout,null,false);
        TextView textView = (TextView)root.findViewById(R.id.wheel_menu_item_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textView.setText(itemData.mTitle);
        if(textView.getLayoutParams() instanceof FrameLayout.LayoutParams)
            ((FrameLayout.LayoutParams)textView.getLayoutParams()).gravity = gravity;

        return root;

    }

    @Override
    public MenuItemData getItem(int position) {
        return menuItems.get(position);
    }
}
