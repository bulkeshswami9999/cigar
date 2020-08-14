package com.hav.cigar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
//import android.R;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.hav.cigar.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PartnerActivity extends AppCompatActivity {
    WebView web_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        //text1= findViewById(R.id.text1);
         web_view = findViewById(R.id.text1);
        // fit the width of screen
        //web_view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
// remove a weird white line on the right size
        //web_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings ws = web_view.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setUserAgentString("Android");
        web_view.setWebViewClient(new myWebClient());
       web_view.loadUrl("file:///android_asset/content.html");
       // web_view.loadData(readTextFromResource(R.raw.content), "text/html", "utf-8");

        //WebSettings ws = text1.getSettings();
        //text1.setJavaScriptEnabled(true);
        // text1.loadUrl("content.html");
        //text1.loadData(readTextFromResource(R.raw.content), "text/html", "utf-8");
        //text1.setText(Html.fromHtml(getString(R.string.associate_string)));
    }
    private String readTextFromResource(int resourceID)
    {
        InputStream raw = getResources().openRawResource(resourceID);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int i;
        try
        {
            i = raw.read();
            while (i != -1)
            {
                stream.write(i);
                i = raw.read();
            }
            raw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stream.toString();
    }
    public class myWebClient extends WebViewClient
    {
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
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_view.canGoBack()) {
            web_view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
