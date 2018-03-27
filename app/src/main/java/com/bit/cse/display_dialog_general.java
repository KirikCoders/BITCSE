package com.bit.cse;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

/**
 * Created by SharathBhargav on 02-02-2017.
 */

@Keep
@KeepClassMembers
@android.support.annotation.Keep
public class display_dialog_general {
    RecyclerView recyclerView;
    ArrayList<display> displayArrayList=new ArrayList<display>();
    View view;
    ArrayList<String> head_parameters=new ArrayList<String>();
    Context context;
    String optionalTextFaculty="";
    DisplayMetrics metrics;
    public display_dialog_general(){}
    public display_dialog_general(ArrayList<display> displayArrayList,View view,ArrayList<String> head_parameters,Context context,DisplayMetrics metrics)
    {
        // this.recyclerView=recyclerView;
        this.displayArrayList=displayArrayList;
        this.view=view;
        this.head_parameters=head_parameters;
        this.context=context;
        this.metrics=metrics;
        optionalTextFaculty="sd";

    }
    public display_dialog_general(ArrayList<display> displayArrayList,View view,ArrayList<String> head_parameters,Context context,String optional,DisplayMetrics metrics)
    {
        // this.recyclerView=recyclerView;
        this.displayArrayList=displayArrayList;
        this.view=view;
        this.head_parameters=head_parameters;
        this.context=context;
        this.optionalTextFaculty=optional;
        this.metrics=metrics;

    }
    public  class recycler_view_adapter extends RecyclerView.Adapter<recycler_view_adapter.myViewHolder> {
        public class myViewHolder extends RecyclerView.ViewHolder {
            public TextView head1, head2, head3, head4, disp1, disp2, disp3, disp4;
            CardView cardGeneral;
            LinearLayout linearInsideCard;
            public myViewHolder(View view) {
                super(view);
                head1 = (TextView) view.findViewById(R.id.customListHead1);
                head2 = (TextView) view.findViewById(R.id.customListHead2);
                head3 = (TextView) view.findViewById(R.id.customListHead3);
                head4 = (TextView) view.findViewById(R.id.customListHead4);
                disp1 = (TextView) view.findViewById(R.id.customListText1);
                disp2 = (TextView) view.findViewById(R.id.customListText2);
                disp3 = (TextView) view.findViewById(R.id.customListText3);
                disp4 = (TextView) view.findViewById(R.id.customListText4);
                cardGeneral=(CardView)view.findViewById(R.id.cardGeneral);
                linearInsideCard=(LinearLayout)view.findViewById(R.id.linearInsideCard);
            }
        }

        public recycler_view_adapter(ArrayList<display> displayArrayList1,ArrayList<String> head_params,Context context1)
        {
            displayArrayList=displayArrayList1;
            head_parameters=head_params;
            context=context1;
        }
        public  recycler_view_adapter(){}

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customlist, parent, false);

            return new myViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            holder.head1.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.head3.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.head4.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.head2.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.disp1.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.disp2.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.disp3.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.disp4.setTextColor(position%2!=0 ? Color.BLACK:Color.BLACK);
            holder.head1.setText(head_parameters.get(0));
            holder.head2.setText(head_parameters.get(1));
            holder.head3.setText(head_parameters.get(2));
            holder.head4.setText(head_parameters.get(3));
            holder.disp1.setText(new slotConvert().slotConversion(displayArrayList.get(position).string1) );
            if(displayArrayList.get(position).string2.length()<=3)
                holder.disp2.setText(displayArrayList.get(position).string2);
            else
                holder.disp2.setText(displayArrayList.get(position).string2.substring(0,displayArrayList.get(position).string2.length()-1));
            holder.disp3.setText(displayArrayList.get(position).string3);
            holder.disp4.setText(displayArrayList.get(position).string4);
            holder.linearInsideCard.setBackgroundResource(position%2==0 ? R.color.color2 : R.color.color1);
        }


        @Override
        public int getItemCount() {
            //return 5;
            return displayArrayList.size();
        }

    }


    public void showDialog() {
        AlertDialog alert;
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alertdialog_theme);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recycler_view_adapter adapter=new recycler_view_adapter();
        recyclerView.setAdapter(adapter);
        //builder.setTitle("Schedule");
        builder.setView(view);
        builder.setCancelable(true);
        //builder.setPositiveButton("OK", null);
        alert=builder.create();

        alert.show();
        // int height = metrics.heightPixels;
        // int width = metrics.widthPixels;
        // if(displayArrayList.size()>7)
        //     alert.getWindow().setLayout(width-100,height-100);
        // //alert.getWindow().setBackgroundDrawableResource(R.drawable.dialog_border);

    }





}
