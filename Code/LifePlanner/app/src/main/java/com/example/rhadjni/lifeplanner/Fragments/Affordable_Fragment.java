/**
 * Implemented by Dolieth Chambers
 */


package com.example.rhadjni.lifeplanner.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.example.rhadjni.lifeplanner.Activities.Calculator;

import com.example.rhadjni.lifeplanner.Adapters.WheelTextAdapter;
import com.example.rhadjni.lifeplanner.Data.MenuItemData;
import com.example.rhadjni.lifeplanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class Affordable_Fragment extends Fragment implements CursorWheelLayout.OnMenuSelectedListener {
    CursorWheelLayout wheel_text;
    List<MenuItemData> lstText;

    private ImageButton fab;
    private Button button;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_affordable, container, false);
        initViews(view);
        loadData();

        //TextView wheelitem=(TextView)view.findViewById(R.id.wheel_menu_center_item);
        wheel_text.setOnMenuSelectedListener(this);
        fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Calculator.class);
                startActivity(intent);
            }});
        return view;


    }
    private void loadData() {
        lstText= new ArrayList<>();
        lstText.add(new MenuItemData("Do not do it"));
        lstText.add(new MenuItemData("Just this once"));
        lstText.add(new MenuItemData("You cant afford it"));
        lstText.add(new MenuItemData("Treat yourself"));
        lstText.add(new MenuItemData("You know better"));
        lstText.add(new MenuItemData("Buy it"));
        WheelTextAdapter adapter = new WheelTextAdapter(getContext(),lstText);
        wheel_text.setAdapter(adapter);
    }

    private void initViews(View view) {
        wheel_text = (CursorWheelLayout)view.findViewById(R.id.wheel_text);
    }

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
        if(parent.getId()==R.id.wheel_text)
            Toast.makeText(getContext(),lstText.get(pos).mTitle + "!!!",Toast.LENGTH_SHORT).show();
    }

}
