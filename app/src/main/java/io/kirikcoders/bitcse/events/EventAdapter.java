package io.kirikcoders.bitcse.events;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.R;

/**
 * Created by Kartik on 05-Aug-18.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<URL> images;
    private ArrayList<String> eventNames;
    /*
    * Image View holder for the recycler view
    */
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView eventImage;
        TextView eventName;
        public ImageViewHolder(View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.currentEventImage);
            eventName = itemView.findViewById(R.id.currentEventName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context,ViewEventActivity.class);
            i.putExtra("event",eventName.getText().toString());
            context.startActivity(i);
            Toast.makeText(view.getContext(), "Clicked "+eventName != null ? eventName.getText().toString():"This", Toast.LENGTH_SHORT).show();
        }
    }
    public EventAdapter(Context context, ArrayList<URL> images, ArrayList<String> eventNames){
        this.context = context;
        this.images = images;
        this.eventNames = eventNames;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ImageViewHolder(inflater.inflate(R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        URL imageUrl = images.get(position);
        String eventName = eventNames.get(position);
        Glide.with(context)
                .load(imageUrl)
                .into(holder.eventImage);
        holder.eventName.setText(eventName);
    }

    @Override
    public int getItemCount() {
        return eventNames.size();
    }
}
