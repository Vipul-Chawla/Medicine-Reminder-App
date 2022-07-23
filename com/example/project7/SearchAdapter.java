package com.example.project7;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SearchModel> searchModels;
    TextToSpeech textToSpeech;
    public SearchAdapter(Context context, ArrayList<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SearchModel searchModelobj =searchModels.get(position);
        String searchresult ;
        holder.title.setText(searchModelobj.getTitle());
        holder.description.setText(searchModelobj.getSnippet());
        holder.snippet.setText(searchModelobj.getLink());
        searchresult = holder.title.getText().toString()+holder.description.getText().toString();
        holder.snippet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(searchModelobj.getLink()));
                context.startActivity(i);
            }
        });
        final String finalSearchresult = searchresult;
        Log.w("String", searchresult);
        holder.speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeachActivity.class);
                intent.putExtra("Speach", finalSearchresult);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, snippet, description;
        private ImageView speaker;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            snippet = itemView.findViewById(R.id.Snippet);
            description = itemView.findViewById(R.id.Description);
            speaker = itemView.findViewById(R.id.speaker);
        }
    }
//    class ThreadRunnable implements Runnable{
//        String searchresult;
//
//        public ThreadRunnable(String searchresult) {
//            this.searchresult = searchresult;
//        }
//        @Override
//        public void run() {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                textToSpeech.speak(searchresult, TextToSpeech.QUEUE_FLUSH, null, null);
//            }else{
//                textToSpeech.speak(searchresult, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        }
//    }
}
