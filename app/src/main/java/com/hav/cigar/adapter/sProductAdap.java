package com.hav.cigar.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.hav.cigar.R;
import com.hav.cigar.model.sProduct;
import com.hav.cigar.options.SearchResultActivity;
import java.util.ArrayList;
import java.util.List;


public class sProductAdap extends RecyclerView.Adapter<sProductAdap.ViewHolder> {
    public Context context;
    private List<sProduct> sProduct;
    public sProductAdap(ArrayList<sProduct> sProduct, Context context) {
        this.sProduct = sProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sProduct sProduct1 = sProduct.get(position);
        holder.ImgTitle.setText(sProduct1.getName());
        holder.ImgDesc.setText(sProduct1.getDescription());
        holder.Imgprice.setText(sProduct1.getOriginal_price());
        Glide.with(context).load(sProduct1.getImage()).placeholder(R.drawable.actdot)
                .into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return sProduct.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image1;
        TextView ImgTitle,ImgDesc,Imgprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgTitle = (TextView)itemView.findViewById(R.id.ImgTitle);
            ImgDesc = (TextView)itemView.findViewById(R.id.ImgDesc);
            Imgprice = (TextView)itemView.findViewById(R.id.Imgprice);
            image1 = (ImageView) itemView.findViewById(R.id.image1);

        }

    }
}
