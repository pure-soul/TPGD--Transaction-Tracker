/**
 * Implemented by Rhadjni Phipps
 */
package com.example.rhadjni.lifeplanner.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;
import com.example.rhadjni.lifeplanner.Tables.Expenses;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class GraphFragment extends android.support.v4.app.Fragment {
    final android.support.v4.app.Fragment frag=this;
    private SqliteDatabase mDatabase;
    private BarChart barChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_graph, container, false);
        // Inflate the layout for this fragment

        mDatabase = new SqliteDatabase(getContext());
        List<Expenses> allExpenses = mDatabase.listExpenses();

         barChart = (BarChart) view.findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i=0;i<allExpenses.size();i++){
            entries.add( new BarEntry(allExpenses.get(i).getAmount(),i));
        }
        entries.add(new BarEntry(mDatabase.CalcSpending(),allExpenses.size()));

        BarDataSet bardataset = new BarDataSet(entries, "Expenses");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i=0;i<allExpenses.size();i++) {
        labels.add(allExpenses.get(i).getName());
        }
        labels.add("Dailies");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Analysis Graph");  // set the description

        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);

        barChart.animateY(5000);


        return view;
    }

}



