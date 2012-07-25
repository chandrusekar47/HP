package com.thoughtworks.hp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewInitiatorActivity extends Activity {

    private WebView customWebView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        customWebView = (WebView) findViewById(R.id.webview);
        customWebView.setWebViewClient(new CustomHpWebView(this));
        customWebView.getSettings().setJavaScriptEnabled(true);
        customWebView.addJavascriptInterface(new CustomJavaScriptInterface(this), "AndroidJS");
        customWebView.loadUrl("http://google.com");
    }

}
