package com.example.project7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class yogaadapter extends RecyclerView.Adapter<yogaadapter.myviewHolder> {
    private Context context;
    private ArrayList<yogamodel> yogamodels;
    public yogaadapter(Context context, ArrayList<yogamodel>yogamodels) {
        this.context = context;
        this.yogamodels = yogamodels;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yoga , parent, false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewHolder holder, final int position) {
        final String link = yogamodels.get(position).getLink();
        final String engName = yogamodels.get(position).getEngName();
        final String sanName = yogamodels.get(position).getSanName();

        if(link != null) {
            Glide.with(holder.itemView.getContext()).load(link).into(holder.imageView);
        }
        else
            holder.imageView.setImageResource(R.drawable.noimage);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, zoom.class);
                intent.putExtra("link", link);
                intent.putExtra("engName", engName);
                intent.putExtra("sanName", sanName);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(yogamodels != null)
            return yogamodels.size();
        else
            return 0;
    }



    public class myviewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}
