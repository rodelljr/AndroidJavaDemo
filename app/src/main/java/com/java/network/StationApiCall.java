package com.java.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.java.interfaces.GetDataListener;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StationApiCall extends AsyncTask<String, Void, String> {

    GetDataListener mListener;
    Context mContext;

    public StationApiCall(Context context, GetDataListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        //TODO: Add progress bar/dialog to screen and set message
        //super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        return connect(strings[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mListener.onApiReturn(result);
        // TODO: dismiss dialog
    }

    private String connect(String url) {
        try {
            OkHttpClient conn = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = conn.newCall(request).execute();
            return response.body().string();
        } catch(IOException ex) {
            Log.e("API IOException", ex.getMessage());
            return "failed to connect";
        }
    }
}
