package com.android.pixabaysample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    Context mContext;
    Activity mActivity;

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
                        openFullScreen(arrayList.get(pos));
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

    public void openFullScreen(String url){
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_full_screen);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        ImageView imageView = dialog.findViewById(R.id.imageView);

        Picasso.get().load(url).into(imageView);

        dialog.show();
    }

}

