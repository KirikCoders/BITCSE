package io.kirikcoders.bitcse.tools.FindProfessor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.kirikcoders.bitcse.R;
/**
 * Created by Akash on 25-Jan-19.
 */

public class FacSearchModel extends ArrayAdapter<String> {

    private  String[] names;
    private Activity context;
    public FacSearchModel(Activity context, String[] names) {
        super(context, R.layout.facsearch_item,names);
        this.context=context;
        this.names=names;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        viewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.facsearch_item,null,true);
            viewHolder=new viewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(viewHolder) r.getTag();
        }
        viewHolder.tw.setText(names[position]);
        return r;
    }
}

class viewHolder
{
    TextView tw;
    viewHolder(View v)
    {
        tw=v.findViewById(R.id.facsearch_item);
    }
}
