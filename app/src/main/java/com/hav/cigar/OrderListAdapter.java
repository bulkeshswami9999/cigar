package com.hav.cigar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hav.cigar.model.OrderListData;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    Context context;
    private int[] orderimages;
    private String[] string;

    private OrderListData[] orderListData;

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.order_list_items, parent, false);
        OrderListAdapter.ViewHolder viewHolder = new OrderListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    public OrderListAdapter(Context context, int[] orderimages, String []string) {
        this.orderimages = orderimages;
        this.context = context;
        this.string = string;
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, final int position) {

        //  final OrderListData orderListData = orderimages.length;
        holder.textView.setText(string[position]);
        holder.imageView.setImageResource(orderimages[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Toast.makeText(view.getContext(),"click on item: "+holder.textView.getText(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, New.class);
//               /* intent.putExtra("image",orderimages.length);
//                intent.putExtra("text",string.length);*/
//                context.startActivity(new Intent(intent));
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderimages.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.order_image);
            this.textView = (TextView) itemView.findViewById(R.id.order_imgdesc);
            cardView = (CardView) itemView.findViewById(R.id.orderrelativeLayout);
        }
    }


}
