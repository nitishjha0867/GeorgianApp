package com.futureadymedia.alumni.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.fragment.FragmentUserProfile;
import com.google.gson.Gson;

/**
 * Created by Nitish on 13/11/15.
 */
public class ServiceAsync  extends AsyncTask {
    private String request;
    private ServiceStatus serviceStatus;
    private String url,methodName;
    private Context context;
    private  ServiceCall serviceCall;
    ProgressDialog pDialog;

    public ServiceAsync(Context context, String request, String url , String methodName, ServiceStatus serviceStatus) {
        this.request=request;
        this.serviceStatus= serviceStatus;
        this.url=url;
        this.methodName=methodName;
        this.context=context;
    }


    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please Wait..");
        pDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
           serviceCall = new ServiceCall();
          String response= serviceCall.getServiceResponse(url,request,methodName);
        Log.e("mainresponse", ""+response);
        try {
            if(response!=null){
                Gson gson = new Gson();
                ServiceResponse servicesResponse=  gson.fromJson(response, ServiceResponse.class);
                Log.e("ASYNCRESPONSE", ""+servicesResponse);
                return servicesResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(o!=null){
            pDialog.dismiss();
            ServiceResponse servicesResponse= (ServiceResponse)o;
            if(serviceCall.getStatusCode()==200) {
                serviceStatus.onSuccess(servicesResponse);
            }else {
                serviceStatus.onFailed(servicesResponse);
            }
        }else{

            ServiceResponse servicesResponse = new ServiceResponse();
            servicesResponse.response_code="0";
            servicesResponse.response_message="Server not responding, please try later";
            serviceStatus.onFailed(servicesResponse);
        }

    }
}
