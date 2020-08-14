package com.hav.cigar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.hav.cigar.R;
import com.hav.cigar.model.Message;
import com.hav.cigar.model.MyListData;
import com.hav.cigar.model.Notification;
import com.hav.cigar.options.NotificationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    Context context;
    private int[] images;
    private String[] str;
    ArrayList<Message> notificationDataList;

    private MyListData[] listdata;

    public MyListAdapter(Context notificationActivity, ArrayList<Message> message) {
        this.context=notificationActivity;
        this.notificationDataList=message;
    }

    // RecyclerView recyclerView;
   // public MyListAdapter(MyListData[] listdata) {
      //  this.listdata = listdata;
    //}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.notification_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public MyListAdapter(Context context, int[] images, String []str) {
        this.images = images;
        this.context = context;
        this.str = str;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //","create_at":"2020-08-08 03:48:15"},
        holder.tv_payment.setText("Payment status: "+notificationDataList.get(position).getPaymentResponse());
        holder.tv_amount.setText("Amount: "+notificationDataList.get(position).getAmount());
        holder.tv_created.setText("Date, Time: "+setDate(notificationDataList.get(position).getCreateAt()));

    }
    public String setDate(String date)
    {
        // String dtStart = "2010-10-15T09:27:37Z";
        String formatedDate= "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date_ = format.parse(date);
            System.out.println(date_.toString());
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            formatedDate= output.format(date_);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }


    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_payment;
        public TextView tv_amount;
        public TextView tv_created;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_payment = (TextView) itemView.findViewById(R.id.tv_payment);
            this.tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            this.tv_created = (TextView) itemView.findViewById(R.id.tv_created);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}


















/*RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>{
    Context context;
    private int[] images;
    private String[] str;

    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    public RecyclerAdapter(Context context, int[] images, String []str) {
        this.images = images;
        this.context = context;
        this.str = str;
    }

    @Override
    public void onBindViewHolder( ImageViewHolder holder, int position) {
        int image_id = images[position];
        String str_id = str[position];
        holder.List.setImageResource(image_id);
        holder.ListAlbum.setText(str_id);
    }

    @Override
    public int getItemCount() {
            return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView List;
        TextView ListAlbum;
        public ImageViewHolder(View itemView) {
            super(itemView);
            List = itemView.findViewById(R.id.list_item);
            ListAlbum = itemView.findViewById(R.id.imgname);
            
        }
    }
}
*/