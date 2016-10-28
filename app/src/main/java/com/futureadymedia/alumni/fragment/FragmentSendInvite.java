package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.futureadymedia.alumni.R;

/**
 * Created by developer on 10/4/2016.
 */
public class FragmentSendInvite extends BaseFragment implements View.OnClickListener{

    private Context context;
    private View view;
    private Button btnWatsappShare, btnEmailShare, btnMessageShare;
    public String inviteCode = "59106";

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = LayoutInflater.from(context).inflate(R.layout.fragment_sendinvite, null);

        findId();
        setFont();
        setListener();

        return view;
    }

    @Override
    public void findId() {
        btnWatsappShare = (Button)view.findViewById(R.id.btnWatsappShare);
        btnEmailShare = (Button)view.findViewById(R.id.btnEmailShare);
        btnMessageShare = (Button)view.findViewById(R.id.btnMessageShare);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnWatsappShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."+inviteCode);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
                break;

            case R.id.btnMessageShare:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body","This is my text to send."+inviteCode);
                startActivity(smsIntent);
                break;

            case R.id.btnEmailShare:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Georgian Invite Code");
                intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."+inviteCode);
                startActivity(Intent.createChooser(intent, ""));
                break;
        }

    }

    @Override
    public void setListener() {
        btnWatsappShare.setOnClickListener(this);
        btnEmailShare.setOnClickListener(this);
        btnMessageShare.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }
}
