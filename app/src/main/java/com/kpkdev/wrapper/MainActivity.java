package com.kpkdev.wrapper;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    private WebView webView;
    private Button mLoginButton;
    private EditText mUserName;
    private EditText mPassword;
    private View mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);
        mLoginButton = (Button)findViewById(R.id.btn_login);
        mUserName = (EditText)findViewById(R.id.et_username);
        mPassword = (EditText)findViewById(R.id.et_password);
        mForm = findViewById(R.id.form);
        mUserName.setText("osutest1");
        mPassword.setText("Test1234!");
        mLoginButton.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebClient());
        webView.loadUrl("https://connext.staywell.com");
    }

    public class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoginButton.setOnClickListener(getOnClickListener(view));
            mLoginButton.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener getOnClickListener(final WebView webView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mUserName.getText().toString();
                String pass = mPassword.getText().toString();
                webView.loadUrl("javascript:var x = document.getElementById('username').value = '" + user +"';");
                webView.loadUrl("javascript:var x = document.getElementById('password').value = '" + pass +"';");
                webView.loadUrl("javascript:document.getElementsByClassName('submit')[0].click();");
                mForm.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        };
    }


}
