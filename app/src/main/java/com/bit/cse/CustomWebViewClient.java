package com.bit.cse;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by SharathBhargav on 24-02-2017.
 */
public class CustomWebViewClient extends WebViewClient {
    /*
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
       view.loadUrl(request.getUrl().toString());
        return true;
    }
*/
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        Log.v("web123",url);
        return true;
    }

}
