package com.example.project7;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class showadapter extends RecyclerView.Adapter<showadapter.myviewHolder> {
    private Context context;
    private String categories[] = {"ninties","partysongs", "spritual", "coolmind"};
    private String searchresult[] = {"Ninties Hindi song playlists with highest views","Hindi Party song playlists with highest views","Hindi Bhakti song playlists with highest views", "Cool Mind song playlists with highest views"};
    public showadapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, final int position) {
        String st = "@drawable/" + categories[position];
        int id = context.getResources().getIdentifier(st, "drawable", context.getPackageName());
        holder.imageView.setImageResource(id);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                intent.putExtra(SearchManager.QUERY, searchresult[position]);
                Intent chooser = Intent.createChooser(intent,"Play with...");
                context.startActivity(chooser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class myviewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}
