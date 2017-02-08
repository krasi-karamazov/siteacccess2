package com.kpkdev.wrapper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by krasimir.karamazov on 2/7/2017.
 */

public class LoadLoginPageTask extends AsyncTask<String, Void, String> {
    private LoginCallBack mLoginCallBack;
    public LoadLoginPageTask(LoginCallBack callBack) {
        mLoginCallBack = callBack;
    }

    @Override
    protected String doInBackground(String... strings) {
        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://connext.staywell.com/en/My-Account/Login.aspx")
                .build();
        String token = null;
        try {
            Response resp = client.newCall(request).execute();
            List<String> header = resp.networkResponse().headers("Set-Cookie");
            token = header.get(6).substring(header.get(6).indexOf('=') + 1, header.get(6).indexOf(';'));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(mLoginCallBack != null) {
            mLoginCallBack.onRetrievedToken(s);
        }

    }

    public interface LoginCallBack {
        void onRetrievedToken(String token);
    }
}
