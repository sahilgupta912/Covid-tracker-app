package com.example.covid19.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.covid19.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private static TextView json;
    private static TextView numberNationalActive;
    private static TextView numberNationalConfirm;
    private static TextView numberNationalRecovered;
    private static TextView numberNationalDeceased;
    private static TextView numberNationalVaccinated1;
    private static TextView numberNationalVaccinated2;
    private static TextView todayConfirmed;
    private static TextView todayRecovered;
    private static TextView todayDeceased;
    private static TextView todayVaccination;
    private static TextView lastUpdated;
    private static PieChart pieChart;
    private static LineChart confirmLineChart;
    private static LineChart recoveredLineChart;
    private static LineChart deceasedLineChart;
    private static LineChart vaccinated1LineChart;
    private static LineChart vaccinated2LineChart;


    //private HomeViewModel homeViewModel;
    //private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // homeViewModel =
        //new ViewModelProvider(this).get(HomeViewModel.class);

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
/*
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        */
        json = view.findViewById(R.id.txtCorona);
        numberNationalActive=view.findViewById(R.id.numberNationalActive);
        numberNationalConfirm=view.findViewById(R.id.numberNationalConfirm);
        numberNationalRecovered=view.findViewById(R.id.numberNationalRecovered);
        numberNationalDeceased=view.findViewById(R.id.numberNationalDeceased);
        numberNationalVaccinated1=view.findViewById(R.id.numberNationalVaccinated1);
        numberNationalVaccinated2=view.findViewById(R.id.numberNationalVaccinated2);
        todayConfirmed=view.findViewById(R.id.todayConfirmed);
        todayRecovered=view.findViewById(R.id.todayRecovered);
        todayDeceased=view.findViewById(R.id.todayDeceased);
        todayVaccination=view.findViewById(R.id.todayVaccination);
        lastUpdated=view.findViewById(R.id.txtLastUpdated);
        pieChart=view.findViewById(R.id.pieChart);
        confirmLineChart=view.findViewById(R.id.confirmLineChart);
        recoveredLineChart=view.findViewById(R.id.recoveredLineChart);
        deceasedLineChart=view.findViewById(R.id.deceasedLineChart);
        vaccinated1LineChart=view.findViewById(R.id.vaccinated1LineChart);
        vaccinated2LineChart=view.findViewById(R.id.vaccinated2LineChart);


        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public static void getData() throws ParseException {


        // Method 1

        URL url = null;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {

            url = new URL("https://data.covid19india.org/v4/min/data.min.json");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();

            System.out.println(connection.getResponseCode());
            if (responseCode > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null)
                responseContent.append(line);

            reader.close();


            JSONObject jsonObject=new JSONObject(responseContent.toString()); //this is the full jsonObject of the api


            JSONObject TT;
            TT=jsonObject.getJSONObject("TT");  //this is the jsonObject of only "TT" key inside of full json


            JSONObject total;
            total=TT.getJSONObject("total");   // this is the jsonObject of only "total" key inside of TT object

            JSONObject delta;
            delta=TT.getJSONObject("delta");

            JSONObject meta;
            meta=TT.getJSONObject("meta");

            // web scraping block

            Document doc=Jsoup.connect("https://www.mygov.in/covid-19").get();

            String updatedDate=doc.select("div.updated-date  span ").text();

            System.out.println("updated date is: "+updatedDate.substring(8,updatedDate.length()-15));

            String ttVaccine= doc.select("div.total-vcount:not(.yday) strong").text();

            String tdVaccine=doc.select("strong.vaccine-live-count").text();

            String ttConfirmed=doc.select("div.iblock.t_case div span").text();

            String ttRecovered= doc.select("div.iblock.discharge div span").text();

            String ttDeceased= doc.select("div.iblock.death_case div span").text();

            String ttActive=doc.select("div.block-active-cases span").text();

            String tdConfirmed=doc.select("div.iblock.t_case div div.increase_block div").text();

            String tdRecovered=doc.select("div.iblock.discharge div div.increase_block div").text();

            String tdDeceased=doc.select("div.iblock.death_case div div.increase_block div").text();

            String tdActive=doc.select("div.block-active-cases div div").text();

            /*
            int confirmed=total.getInt("confirmed");
            int deceased=total.getInt("deceased");
            int recovered= total.getInt("recovered");

            int other=total.getInt("other");
            int todayConfirmedInt=delta.getInt("confirmed");
            int todayRecoveredInt=delta.getInt("recovered");
            int todayDeceasedInt=delta.getInt("deceased");
            String lastUpdatedString=meta.getString("date");
            int active=confirmed-recovered-deceased-other;*/

            /*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat month_date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            Date date= sdf.parse(lastUpdated);*/

            /*assert date != null;
            String fullDate =month_date.format(date);*/

            int vaccinated1=total.getInt("vaccinated1");
            int vaccinated2=total.getInt("vaccinated2");

            numberNationalConfirm.setText(ttConfirmed);
            numberNationalDeceased.setText(ttDeceased);
            numberNationalRecovered.setText(ttRecovered);
            numberNationalActive.setText(ttActive);
            numberNationalVaccinated1.setText(String.valueOf(vaccinated1));
            numberNationalVaccinated2.setText(String.valueOf(vaccinated2));
            todayConfirmed.setText(tdConfirmed);
            todayRecovered.setText(tdRecovered);
            todayDeceased.setText(tdDeceased);
            todayVaccination.setText(ttVaccine);
            String substring = updatedDate.substring(8, updatedDate.length() - 15);
            lastUpdated.setText("Last Updated : "+substring);

            /*int[] cases ={Integer.parseInt(tdConfirmed),Integer.parseInt(tdRecovered),Integer.parseInt(tdDeceased)};*/

            NumberFormat format=NumberFormat.getInstance(Locale.UK);

            Number ttConfirmedInt=format.parse(ttConfirmed);
            Number ttRecoveredInt=format.parse(ttRecovered);
            Number ttDeceasedInt=format.parse(ttDeceased);

            /*int ttConfirmedInt= Integer.parseInt(ttConfirmed.toString());
            int ttRecoveredInt=Integer.parseInt(ttRecovered.toString());
            int ttDeceasedInt=Integer.parseInt(ttDeceased.toString());
*/
            int[] cases ={ttConfirmedInt.intValue(),ttRecoveredInt.intValue(),ttDeceasedInt.intValue()};
            String[] names ={"Confirmed","Recovered","Deceased"};
            int[] colors={Color.parseColor("#1d7ebf"),Color.parseColor("#27cc0e"),
                    Color.parseColor("#e36c17")};

            List<PieEntry> pieEntries=new ArrayList<>();
            for(int i = 0; 3 > i; i++)
                pieEntries.add(new PieEntry(cases[i],names[i]));

            PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
            pieDataSet.setColors(colors);
            pieDataSet.setValueTextSize(12f);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
            pieChart.setDrawEntryLabels(false);
            pieChart.setContentDescription("");
            pieChart.setEntryLabelTextSize(12);
            pieChart.setHoleRadius(75);

            Legend legend=pieChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setTextSize(12);
            legend.setFormSize(20);
            legend.setFormToTextSpace(2);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            legend.setOrientation(Legend.LegendOrientation.VERTICAL);
            legend.setDrawInside(false);
            legend.setYEntrySpace(12);

            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawSliceText(false);
            pieChart.getData().setDrawValues(false);

            getLineChart();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is some error " + e);
        }



    }
    public static void getLineChart(){

        URL url=null;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try
        {
            url = new URL("https://data.covid19india.org/v4/min/timeseries.min.json");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();

            System.out.println(connection.getResponseCode());
            if (responseCode > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null)
                responseContent.append(line);

            reader.close();


            JSONObject jsonObject=new JSONObject(responseContent.toString());

            JSONObject TT;
            TT=jsonObject.getJSONObject("TT");

            int size=TT.length();
            JSONObject datesObj;
            datesObj=TT.getJSONObject("dates");


            List<String> dates=new ArrayList<>();
            ArrayList<Entry> entriesConfirm=new ArrayList<>();
            ArrayList<Entry> entriesRecovered=new ArrayList<>();
            ArrayList<Entry> entriesDeceased=new ArrayList<>();
            ArrayList<Entry> entriesVaccinated1=new ArrayList<>();
            ArrayList<Entry> entriesVaccinated2=new ArrayList<>();

            int i=0;
            for (Iterator<String> it = datesObj.keys(); it.hasNext(); ) {
                String key = it.next();
                dates.add(key);

                JSONObject newDate;
                newDate=datesObj.getJSONObject(key);

                JSONObject total;
                total=newDate.getJSONObject("delta");

                if(total.has("confirmed"))
                    entriesConfirm.add(new Entry(0f+i,total.getInt("confirmed")));
                else
                    entriesConfirm.add(new Entry(0f+i,0));
                i++;
                if(total.has("recovered"))
                    entriesRecovered.add(new Entry(0f+i,total.getInt("recovered")));
                else
                    entriesRecovered.add(new Entry(0f+i,0));

                if(total.has("deceased"))
                    entriesDeceased.add(new Entry(0f+i,total.getInt("deceased")));
                else
                    entriesDeceased.add(new Entry(0f+i,0));

                if(total.has("vaccinated1"))
                    entriesVaccinated1.add(new Entry(0f+i,total.getInt("vaccinated1")));
                else
                    entriesVaccinated1.add(new Entry(0f+i,0));

                if(total.has("vaccinated2"))
                    entriesVaccinated2.add(new Entry(0f+i,total.getInt("vaccinated2")));
                else
                    entriesVaccinated2.add(new Entry(0f+i,0));

            }

            chart(confirmLineChart,entriesConfirm);
            chart(recoveredLineChart,entriesRecovered);
            chart(deceasedLineChart, entriesDeceased);
            chart(vaccinated1LineChart,entriesVaccinated1);
            chart(vaccinated2LineChart,entriesVaccinated2);





        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is some error");
        }
    }

    public static void chart(LineChart lc,ArrayList<Entry> entries){
        LineDataSet lineDataSet=new LineDataSet(entries,"");
        LineData lineData=new LineData(lineDataSet);
        lc.setData(lineData);
        if(lc==confirmLineChart)
            lineDataSet.setColors(Color.parseColor("#78233e"));
        else if(lc==recoveredLineChart)
            lineDataSet.setColors(Color.parseColor("#27cc0e"));
        else if(lc==deceasedLineChart)
            lineDataSet.setColors(Color.parseColor("#e36c17"));
        else
            lineDataSet.setColors(Color.parseColor("#195944"));

        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        Legend legend=lc.getLegend();
        legend.setEnabled(false);
        lc.getAxisLeft().setDrawLabels(false);
        lc.getAxisRight().setDrawLabels(false);
        lc.getXAxis().setDrawLabels(false);
        lc.setTouchEnabled(false);
        lc.getAxisLeft().setDrawGridLines(false);
        lc.getXAxis().setDrawGridLines(false);
        lc.getAxisRight().setDrawGridLines(false);
        lc.getDescription().setEnabled(false);

        lc.getAxisLeft().setDrawAxisLine(false);
        lc.getAxisRight().setDrawAxisLine(false);
        lc.getXAxis().setDrawAxisLine(false);

        lc.getHighestVisibleX();
        lc.getLowestVisibleX();


    }
}
