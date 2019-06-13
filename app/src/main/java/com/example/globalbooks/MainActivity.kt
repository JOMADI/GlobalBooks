package com.example.globalbooks

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.*
import android.widget.ProgressBar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    private val TEST_ADMOB_APP_ID: String = "ca-app-pub-3940256099942544~6300978111"
    lateinit var mAdView: AdView
    lateinit var mWebView: WebView
    lateinit var mProgressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MobileAds.initialize(this, TEST_ADMOB_APP_ID)

        mProgressBar = findViewById(R.id.progressBar)

        mWebView = findViewById(R.id.webView)
        val webSetting: WebSettings = mWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.allowFileAccess = true
        webSetting.domStorageEnabled = true
        webSetting.loadWithOverviewMode = true
        webSetting.useWideViewPort = true
        webSetting.builtInZoomControls = true
        webSetting.displayZoomControls = false
        webSetting.setSupportZoom(true)
        webSetting.defaultTextEncodingName = "UTF-8"
        webSetting.setAppCacheEnabled(false)
        webSetting.databaseEnabled = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        mWebView.webViewClient = object: WebViewClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                mProgressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                mProgressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        mWebView.webChromeClient = WebChromeClient()
        mWebView.loadUrl("https://www.pdfdrive.com/")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onBackPressed() {
        if(mWebView.canGoBack())
            mWebView.goBack()
        else
            super.onBackPressed()
    }
}
