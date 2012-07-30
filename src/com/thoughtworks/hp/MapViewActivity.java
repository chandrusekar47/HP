package com.thoughtworks.hp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class MapViewActivity extends Activity {

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
        String json = getIntent().getExtras().getString("json");
        String url = "http://sheltered-hollows-2894.herokuapp.com/indoor_map?item_details=" + json;
        Log.e("WebView", "Hitting Url :-" + url);
        customWebView.loadUrl(url);
    }

}
