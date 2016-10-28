package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.adapter.DirectoryListAdapter;
import com.futureadymedia.alumni.listeners.RecyclerItemClickListener;
import com.futureadymedia.alumni.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/3/2016.
 */
public class FragmentDirectoryListing extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<UsersModel> usersmodel;
    private UsersModel usersmodel1;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_directory, null);

       usersmodel = new ArrayList<>();

        findId();
        setFont();
        setListener();

         String[] user_names = {"John", "Ross", "Alex", "Ryan"};
         String[] email_id = {"John@asd.com", "Ross@asd.com", "Alex@asd.com", "Ryan@asd.com"};

        for(int i = 0; i <4; i++){
            usersmodel1 = new UsersModel();
            usersmodel1.user_name = user_names[i];
            usersmodel1.email_id = email_id[i];
            usersmodel.add(usersmodel1);
        }

        //usersmodel1.user_image = R.drawable.arrow_right;




       /* String[] Datalist = new String[30];
        for (int i = 0; i < 30; i++) {
            Datalist[i] = "Hello" + i;
        }*/

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DirectoryListAdapter(context, usersmodel, new RecyclerItemClickListener() {

            @Override
            public void onRecyclerItemClickListener(int position) {
                Log.e("OnRecyclerItemClick", ""+(usersmodel.get(position).email_id));
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void findId() {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.directroyListing);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {

    }
}
