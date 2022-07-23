package com.example.project7;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    String searchquery = "abc";
    TextToSpeech textToSpeech;
    private RecyclerView resultvw;
    private SearchAdapter searchadapter;
    private ProgressBar loadingPB;
    private ArrayList<SearchModel> searchModels;
    String title1, link1, displayedLink1, snippet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);
        resultvw = findViewById(R.id.searchresults);
        searchModels = new ArrayList<>();

        searchadapter = new SearchAdapter(SearchActivity.this, searchModels);
        loadingPB = findViewById(R.id.loading);

        Intent intent = getIntent();
        searchquery = intent.getStringExtra("EXTRA_MESSAGE");

        getSearchResult(searchquery);
        if(searchquery.length() != 0)
            Log.w("Searched", searchquery);
        searchadapter = new SearchAdapter(SearchActivity.this, searchModels);
        resultvw.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
        resultvw.setAdapter(searchadapter);

    }

    private void getSearchResult(String searchQuery){
        searchModels.clear();
        searchadapter.notifyDataSetChanged();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://serpapi.com/search.json?q="+searchQuery+"&location=Delhi,India&hl=en&gl=us&google_domain=google.com&api_key=37523620ff03b66cc32218d97b55a27c7e7ce379d80b3b57b03f4842c8701820";
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try{
                    Log.w("Correct", "Api is Working");
                    JSONArray organicArray = response.getJSONArray("organic_results");
                    for(int i=0; i<organicArray.length(); i++){
                        JSONObject organicObj = organicArray.getJSONObject(i);
                        if(organicObj.has("title")){
                            title1 = organicObj.getString("title");
                        }
                        if(organicObj.has("link")){
                            link1 = organicObj.getString("link");
                        }
                        if(organicObj.has("displayed_link")){
                            displayedLink1 = organicObj.getString("displayed_link");
                        }
                        if(organicObj.has("snippet")){
                            snippet1 = organicObj.getString("snippet");
                        }
                        searchModels.add(new SearchModel(title1, link1, displayedLink1, snippet1));
                    }
                    searchadapter.notifyDataSetChanged();
                    Log.w("Correct", "Api is correct");
                }catch(JSONException e){

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("No Result Found")
                        .setMessage("Please Scan AGAIN!!")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SearchActivity.this, HomePage.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).create().show();

            }
        });
        queue.add(jsonObjectRequest);
    }
}