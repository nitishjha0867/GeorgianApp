package com.futureadymedia.alumni.fragment;

/**
 * Created by developer on 9/15/2016.
 */
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.futureadymedia.alumni.R;

public class FragmentDialog extends DialogFragment {
    private Button btnCancel;
    static FragmentDialog newInstance() {
        return new FragmentDialog();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment, container,
                false);
        btnCancel = (Button)rootView.findViewById(R.id.btnCancel);
        getDialog().setTitle("Terms and Services");
        getDialog().setCancelable(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        // Do something else
        return rootView;
    }
}