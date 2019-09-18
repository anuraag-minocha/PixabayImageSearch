package com.android.pixabaysample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    Context mContext;

    public ImageRecyclerAdapter(Context mContext, ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(mContext).inflate(R.layout.item_image_list, parent, false);
            return new SimpleViewHolder(v);

    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public SimpleViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
        }
    }



    @Override public void onBindViewHolder(final RecyclerView.ViewHolder Holder, final int pos) {

        WindowManager windowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
//        int height = windowManager.getDefaultDisplay().getHeight();

        try {


            if (Holder instanceof SimpleViewHolder) {


                SimpleViewHolder holder = ((SimpleViewHolder) Holder);

                holder.ivPhoto.getLayoutParams().height = width/2;

                Picasso.get().load(arrayList.get(pos)).into(holder.ivPhoto);

                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override public int getItemCount() {

            return arrayList.size();

    }

    public void updateList(ArrayList<String> arraylist){
        this.arrayList = arraylist;
        notifyDataSetChanged();
    }

}

