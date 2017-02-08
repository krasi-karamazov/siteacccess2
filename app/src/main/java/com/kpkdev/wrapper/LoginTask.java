package com.kpkdev.wrapper;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by krasimir.karamazov on 2/7/2017.
 */

public class LoginTask extends AsyncTask<String, Void, String> {

    private LoginCallback mCallback;

    public LoginTask(LoginCallback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        FormBody formBody = new FormBody.Builder().add("__RequestVerificationToken", token)
                .add("Username", "osutest1").add("Password", "Test1234!").build();
        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .url("https://connext.staywell.com/en/My-Account/Login.aspx")
                .build();
        StringBuilder contentBuilder = null;
        try {
            Response resp = client.newCall(request).execute();
            String line = "";
            contentBuilder = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(resp.body().byteStream()));
            while ((line = rd.readLine()) != null) {
                contentBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(mCallback != null) {
            mCallback.onHTMLLoaded(s);
        }
    }

    public interface LoginCallback {
        void onHTMLLoaded(String html);
    }
}
