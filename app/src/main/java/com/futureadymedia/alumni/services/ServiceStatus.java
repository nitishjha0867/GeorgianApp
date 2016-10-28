package com.futureadymedia.alumni.services;

/**
 * Created by developer on 05/10/16.
 */
public abstract class ServiceStatus {

    public abstract  void onSuccess(Object o);
    public abstract  void onFailed(Object o);

}
