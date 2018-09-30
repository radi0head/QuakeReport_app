/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeItemClass>>{

    public static final String USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        // Create a fake list of earthquake locations.
//        final ArrayList<EarthquakeItemClass> earthquakes = QueryUtils.extractEarthquakes();
//        new EarthquakeTask().execute(USGS_REQUEST_URL);
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=(activeNetwork!=null)&&(activeNetwork.isConnectedOrConnecting());
        if(isConnected)
            getLoaderManager().initLoader(0,null,this);
        else{
            ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar_view);
            progressBar.setVisibility(View.GONE);
            TextView textView=(TextView)findViewById(R.id.empty_text_view);
            textView.setText("Please check your internet connection");
        }
    }

//    private class EarthquakeTask extends AsyncTask<String,Void,List<EarthquakeItemClass>>{
//        @Override
//        protected List<EarthquakeItemClass> doInBackground(String... strings) {
//            if(strings.length<1||strings[0]==null)
//                return null;
//
//            return QueryUtils.fetchData(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(final List<EarthquakeItemClass> earthquakeItemClasses) {
//            // Find a reference to the {@link ListView} in the layout
//            ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//            // Create a new {@link ArrayAdapter} of earthquakes
//            EarthquakeAdapter adapter=new EarthquakeAdapter(EarthquakeActivity.this,earthquakeItemClasses);
//
//            // Set the adapter on the {@link ListView}
//            // so the list can be populated in the user interface
//            earthquakeListView.setAdapter(adapter);
//
//            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent details=new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeItemClasses.get(i).getmUrl()));
//                    if(details.resolveActivity(getPackageManager())!=null){
//                        startActivity(details);
//                    }
//                }
//            });
//        }
//    }

    @Override
    public Loader<List<EarthquakeItemClass>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeItemClass>> loader, final List<EarthquakeItemClass> earthquakeItemClasses) {
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar_view);
        progressBar.setVisibility(View.GONE);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter=new EarthquakeAdapter(EarthquakeActivity.this,earthquakeItemClasses);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent details=new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeItemClasses.get(i).getmUrl()));
                if(details.resolveActivity(getPackageManager())!=null){
                    startActivity(details);
                }
            }
        });

        TextView textView=(TextView)findViewById(R.id.empty_text_view);
        textView.setText("No earthquakes found...");
        earthquakeListView.setEmptyView(textView);
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeItemClass>> loader) {
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(new EarthquakeAdapter(EarthquakeActivity.this,new ArrayList<EarthquakeItemClass>()));
    }
}
