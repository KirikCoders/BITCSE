package io.kirikcoders.bitcse;

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

/**
 * Created by Akash on 15-Jan-19.
 */

public class FacultyAdapter extends ArrayAdapter<String>{

    private String[] facname;
    private String[] facdesig;
    private String[] facqual;
    private String[] facmail;
    private String[] facph;
    private Integer[] imgid;
    private Activity context;
    public FacultyAdapter( Activity context,String[] facname,String[] facdesig,String[] facqual,String[] facmail,String[] facph,Integer[] imgid) {
        super(context,R.layout.faculty_item,facname);
        this.context=context;
        this.facname=facname;
        this.facdesig=facdesig;
        this.facqual=facqual;
        this.facmail=facmail;
        this.facph=facph;
        this.imgid=imgid;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {

            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.faculty_item,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder) r.getTag();

        }
        viewHolder.faci.setImageResource(imgid[position]);
        viewHolder.facn.setText("Name:-"+facname[position]);
        viewHolder.facd.setText("Designation:-"+facdesig[position]);
        viewHolder.facq.setText("Qualifications:-"+facqual[position]);
        viewHolder.facm.setText("Email ID:-"+facmail[position]);
        viewHolder.facp.setText("Mobile No.:-"+facph[position]);
        return r;
    }

class ViewHolder
{
   TextView facn;
   TextView facd;
   TextView facq;
   TextView facm;
   TextView facp;
   ImageView faci;
   ViewHolder(View v)
   {

        facn=(TextView) v.findViewById(R.id.f_name);
        facd=(TextView) v.findViewById(R.id.f_desig);
        facq=(TextView) v.findViewById(R.id.f_qual);
        facm=(TextView) v.findViewById(R.id.f_mail);
        facp=(TextView) v.findViewById(R.id.f_ph);
        faci=(ImageView) v.findViewById(R.id.f_iv);

   }
}

}
