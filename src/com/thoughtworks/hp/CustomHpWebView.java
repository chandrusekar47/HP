package com.thoughtworks.hp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomHpWebView extends WebViewClient {

    private Activity webViewActivity;

    public CustomHpWebView(Activity activity) {
        this.webViewActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        webViewActivity.startActivity(intent);
        return true;
    }

}
