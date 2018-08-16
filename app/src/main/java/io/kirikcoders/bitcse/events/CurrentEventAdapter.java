package io.kirikcoders.bitcse.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.kirikcoders.bitcse.R;

/**
 * Created by Kartik on 05-Aug-18.
 */
public class CurrentEventAdapter extends RecyclerView.Adapter<CurrentEventAdapter.ImageViewHolder> {

    /*
    * Image View holder for the recycler view
    */
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
    CurrentEventAdapter(){

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ImageViewHolder(inflater.inflate(R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
