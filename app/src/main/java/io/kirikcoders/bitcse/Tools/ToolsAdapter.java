package io.kirikcoders.bitcse.Tools;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.R;
/**
 * Created by Akash on 18-Jan-19.
 */


public class ToolsAdapter extends ArrayAdapter<String>{
private String[] items;
private Integer[] imgid;
private Activity context;
    public ToolsAdapter(Activity context, String[] items,Integer[] imgid) {
        super(context, R.layout.tools_item,items);
        this.context=context;
        this.items=items;
        this.imgid=imgid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if (r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.tools_item,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.tiv.setImageResource(imgid[position]);
        viewHolder.ttv.setText(items[position]);
        return r;
    }
    class ViewHolder
    {
        TextView ttv;
        ImageView tiv;
        ViewHolder(View v)
        {
            ttv=v.findViewById(R.id.tools_tv);
            tiv=v.findViewById(R.id.tools_iv);
        }
    }
}
