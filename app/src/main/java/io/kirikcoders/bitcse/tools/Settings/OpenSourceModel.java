package io.kirikcoders.bitcse.tools.Settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.kirikcoders.bitcse.R;

/**
 * Created by Akash on 26-Jan-19.
 */
public class OpenSourceModel extends ArrayAdapter<String>
{
   private String[] name,url,license;
   private Activity context;


    public OpenSourceModel(Activity context, String[] name,String[] url,String[] license) {
        super(context, R.layout.opensource_item,name);
        this.context=context;
        this.name=name;
        this.url=url;
        this.license=license;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.opensource_item,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) r.getTag();
        }
        viewHolder.name.setText(name[position]);
        viewHolder.url.setText(url[position]);
        viewHolder.license.setText(license[position]);
        return r;
    }
}

class ViewHolder
{
    TextView name,url,license;
    ViewHolder(View v)
    {
        name=v.findViewById(R.id.open_name);
        url=v.findViewById(R.id.open_url);
        license=v.findViewById(R.id.open_license);
    }
}