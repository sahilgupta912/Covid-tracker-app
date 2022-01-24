package com.example.covid19.ui.data;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class DataFragment extends Fragment {

    //private DashboardViewModel dashboardViewModel;
    //private FragmentDashboardBinding binding;

    private  static RecyclerView rvData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_data, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        rvData=view.findViewById(R.id.rvData);
        Context thisContext = container.getContext();
        rvData.setLayoutManager(new LinearLayoutManager(thisContext));

        try {
            getData();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return view;

    }
    public static void getData() throws ParseException {

        URL url = null;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        // ----------- try block "main block" -------------

        try {

            ArrayList<ModelClassDataRecycler> stateList=new ArrayList<>();

            url=new URL("https://api.rootnet.in/covid19-in/stats/latest");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();

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

            System.out.println(responseContent.toString());
            JSONObject jsonObject=new JSONObject(responseContent.toString());


            JSONObject data;
            data=jsonObject.getJSONObject("data");


            JSONArray regional;
            regional=data.getJSONArray("regional");



            int size=regional.length();




            for(int i=0;i<size;i++)
            {
                JSONObject a=regional.getJSONObject(i);

                stateList.add(new ModelClassDataRecycler(a.getString("loc"),a.getString("totalConfirmed"),
                        a.getString("discharged"),a.getString("deaths")));
            }

            rvData.setAdapter(new AdapterData(stateList));

        }

        // ------------ try ended----------

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is some error");
        }
    }

}