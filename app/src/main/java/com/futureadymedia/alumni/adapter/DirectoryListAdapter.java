package com.futureadymedia.alumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.listeners.RecyclerItemClickListener;
import com.futureadymedia.alumni.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/3/2016.
 */
public class DirectoryListAdapter extends RecyclerView.Adapter<DirectoryListAdapter.ViewHolder>{
    private Context context;
    private RecyclerItemClickListener listener;
    private List<UsersModel> usersmodel;


    public DirectoryListAdapter(Context context, List<UsersModel> usersmodel, RecyclerItemClickListener listener){
        this.context=context;
        this.listener = listener;
        this.usersmodel =  new ArrayList<>(usersmodel);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectoryListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,parent,false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.llCustomtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerItemClickListener(position);
            }
        });

        UsersModel model = usersmodel.get(position);

        //Data set here
        holder.mTextView.setText(model.user_name);
        holder.ivProfileThumb.setBackgroundResource(model.user_image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usersmodel.size();
        //return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ImageView ivProfileThumb;
        public LinearLayout llCustomtv;
        public ViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.textView13);
            ivProfileThumb = (ImageView) itemView.findViewById(R.id.ivProfileThumb);
            llCustomtv = (LinearLayout) itemView.findViewById(R.id.llCustomtv);


        }
    }
}
