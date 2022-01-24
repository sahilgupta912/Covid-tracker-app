package com.example.covid19.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;

import com.example.covid19.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private static RecyclerView rcv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_notifications,container,false);

        rcv=view.findViewById(R.id.rvNews);
        rcv.setLayoutManager(new LinearLayoutManager(container.getContext()));

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
    public void getData() throws IOException {

        ArrayList<ModelClassNotificationRecycler> newsList=new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://covid-19-news.p.rapidapi.com/v1/covid?q=coronavirus&lang=en&sort_by=date&search_in=summary&country=in&media=True")
                .get()
                .addHeader("x-rapidapi-host", "covid-19-news.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "2fcc6335a0msh22ea24e70c03e94p1e4c3ejsn46b6582f71ce")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    final String myResponse= Objects.requireNonNull(response.body()).string();


                    try {
                        JSONObject jsonObject=new JSONObject(myResponse);

                        JSONArray articles=jsonObject.getJSONArray("articles");

                        for(int i=0;i<50;i++) {

                            JSONObject a = articles.getJSONObject(i);
                            newsList.add(new ModelClassNotificationRecycler(
                                    a.getString("summary"),a.getString("published_date")));

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rcv.setAdapter(new AdapterNotification(newsList));
                            }
                        });

                    }
                    catch (JSONException e ) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}