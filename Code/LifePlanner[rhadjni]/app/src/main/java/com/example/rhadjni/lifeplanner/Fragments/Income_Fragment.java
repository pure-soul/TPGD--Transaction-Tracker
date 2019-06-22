/**
 * Implemented by Dolieth Chambers
 */

package com.example.rhadjni.lifeplanner.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rhadjni.lifeplanner.PlannerDb.SqliteDatabase;
import com.example.rhadjni.lifeplanner.R;
import com.example.rhadjni.lifeplanner.Tables.Expenses;
import com.example.rhadjni.lifeplanner.Tables.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Income_Fragment extends Fragment {

    PieChartView pieChartView;
    TextView tottrans;
    final Fragment frag=this;
    private SqliteDatabase mDatabase;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_income, container, false);

        pieChartView = (PieChartView) view.findViewById(R.id.chart);

        tottrans=(TextView)view.findViewById(R.id.totdaily);
        mDatabase = new SqliteDatabase(getContext());
        List<Expenses> allExpenses = mDatabase.checkedExpenses();
        List<Product> allProducts = mDatabase.dateOrderProducts();
        final float tots = mDatabase.CalcSpending();
        final float etots=mDatabase.CalcExpenses();
        final List pieData = new ArrayList<>();

        tottrans.setText("Total Daily Transactions : " +String.valueOf(allProducts.size()));





        if (tots <=0  ) {
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("No Transaction Data").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
            pieChartView.setPieChartData(pieChartData);

        }
        else if(allExpenses.size() <=0){
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("No Expense Data").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
            pieChartView.setPieChartData(pieChartData);


        }
        else {
            float inc2=tots;
            float inc3=etots;
            float fincome=(allExpenses.get(0).getTotalincome()-inc3)-inc2;
            pieData.add(new SliceValue(tots,colorscheme()).setLabel("Daily Spending:"+ String.valueOf(tots)));
            for (int i=0;i<allExpenses.size();i++) {
                pieData.add(new SliceValue(allExpenses.get(i).getAmount(),colorscheme()).setLabel(allExpenses.get(i).getName()+ String.valueOf(allExpenses.get(i).getAmount())));
            }

            pieData.add(new SliceValue(fincome,colorscheme()).setLabel("Remaining Income:"+String.valueOf(fincome)));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("Spent & Remaining").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
            pieChartView.setPieChartData(pieChartData);

        }

        return view;
    }
    public int colorscheme(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }
}
