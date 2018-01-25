package com.bartovapps.gpstriprec.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartovapps.gpstriprec.R;

@SuppressLint("NewApi")
public class CustomMapFragment extends com.google.android.gms.maps.MapFragment {

    private static final String LOG_TAG = "CustomMapFragment";
    private static View view;

    public CustomMapFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        if(savedInstanceState == null){
            Log.i(LOG_TAG, "savedInstanceState is null");
        }else {
            Log.i(LOG_TAG, "savedInstanceState is not null");
        }

//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i(LOG_TAG, "onDestryView");
//
//        CustomMapFragment fragment = (CustomMapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
//
//        if(fragment !=null){
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LOG_TAG, "onDetach..");
    }
}
