package com.thoughtworks.hp;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomHpWebView extends WebViewClient {

    private WebViewInitiatorActivity webViewActivity;

    public CustomHpWebView(WebViewInitiatorActivity activity) {
        this.webViewActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).getHost().contains("www.google.co")) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        webViewActivity.startActivity(intent);
        return true;
    }

}
