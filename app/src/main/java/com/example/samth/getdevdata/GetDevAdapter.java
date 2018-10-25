package com.example.samth.getdevdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GetDevAdapter extends RecyclerView.Adapter<GetDevAdapter.ViewHolder> {

    Context context;
    List<UserData> listData;

    public GetDevAdapter(Context context, List<UserData> listData) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public GetDevAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GetDevAdapter.ViewHolder holder, int position) {
        holder.devName.setText(listData.get(position).getmName());
        holder.devRank.setText(listData.get(position).getmRank());

        Picasso.with(context)
                .load(listData.get(position).getImageView())
                .into(holder.devImage);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView devName;
        TextView devRank;
        ImageView devImage;

        public ViewHolder(View view) {
            super(view);

            devName = view.findViewById(R.id.dev_name);
            devRank = view.findViewById(R.id.dev_rank);
            devImage = view.findViewById(R.id.dev_image);
        }
    }
}
