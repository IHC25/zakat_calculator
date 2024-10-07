package com.imran.zakatcalculator;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebLoader extends AppCompatActivity{

    WebView browser;
    Button backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        browser = findViewById(R.id.browser);
        backButton = findViewById(R.id.backButton);

        // Get the URL that was passed from MainActivity
        String url = getIntent().getStringExtra("url");
        // Load the website in the WebView
        loadWebsite(url);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear WebView resources
                if (browser != null) {
                    browser.clearHistory();
                    browser.clearCache(true);
                    browser.loadUrl("about:blank");
                    browser.onPause();
                    browser.removeAllViews();
                    browser.destroy();
                }
                // Finish the activity and return to MainActivity
                finish();
            }
        });


    }

    // Method to load the website into the WebView
    public void loadWebsite(String url) {
        // Enable JavaScript in the WebView
        if(url.contains("https")){
            WebSettings webSettings = browser.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(false);  // Disable file access for added security
        }

        //Enable Cookies
        CookieManager.getInstance().setAcceptCookie(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(browser, true);

        // Set a WebViewClient to handle the loading within the app instead of the default browser
        browser.setWebViewClient(new WebViewClient());

        // Load the provided URL
        browser.loadUrl(url);
    }

    // OnBackPress


    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {

        // Clear WebView resources
        if (browser != null) {
            browser.clearHistory();
            browser.clearCache(true);
            browser.loadUrl("about:blank");
            browser.onPause();
            browser.removeAllViews();
            browser.destroy();
        }
        return super.getOnBackInvokedDispatcher();
    }
}
