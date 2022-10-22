package playduin.newsfeed.ui;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return !"source1.local".equals(request.getUrl().getHost()) && !"www.source2.local".equals(request.getUrl().getHost());
    }
}
