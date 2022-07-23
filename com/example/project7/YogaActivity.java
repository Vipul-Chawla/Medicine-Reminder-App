package com.example.project7;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class YogaActivity extends AppCompatActivity {
    private RecyclerView resultvw;
    private yogaadapter searchadapter;
    private ProgressBar loadingPB;
    private ArrayList<yogamodel> yogamodels ;
    private String engName, sanName, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_yoga);
        loadingPB = findViewById(R.id.loading);
        yogamodels = new ArrayList<>();
        resultvw = findViewById(R.id.recyclerview);
        getSearchResult();
        searchadapter = new yogaadapter(this, yogamodels);
        resultvw.setLayoutManager(new LinearLayoutManager(YogaActivity.this, LinearLayoutManager.VERTICAL, false));
        resultvw.setAdapter(searchadapter);
    }

    private void getSearchResult(){
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://lightning-yoga-api.herokuapp.com/yoga_poses";
        RequestQueue queue = Volley.newRequestQueue(YogaActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try{
                    Log.w("Correct", "Api is Working");
                    JSONArray organicArray = response.getJSONArray("items");
                    for(int i=0; i<organicArray.length(); i++){
                        JSONObject organicObj = organicArray.getJSONObject(i);
                        if(organicObj.has("sanskrit_name")){
                            sanName = organicObj.getString("sanskrit_name");
                        }
                        if(organicObj.has("img_url")){
                            link = organicObj.getString("img_url");
                        }
                        if(organicObj.has("english_name")){
                            engName = organicObj.getString("english_name");
                        }
                        yogamodels.add(new yogamodel(engName, sanName, link));
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
                new AlertDialog.Builder(YogaActivity.this)
                        .setTitle("No Result Found")
                        .setMessage("Please Reload")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(YogaActivity.this, YogaActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).create().show();

            }
        });
        queue.add(jsonObjectRequest);
    }
}