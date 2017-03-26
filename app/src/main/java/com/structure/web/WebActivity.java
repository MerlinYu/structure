package com.structure.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.scheme.SchemeParser;
import com.structure.scheme.SchemeSelectActivity;
import com.structure.test.material.MainActivity;

import butterknife.InjectView;
import timber.log.Timber;

import static android.R.attr.id;

/**
 * Created by yuchao on 17/3/23.
 */

public class WebActivity extends BaseActivity<WebActivityPresenter> {






  @InjectView(R.id.webview)
  WebView webView;

  Context context;


  @Override
  public WebActivityPresenter createPresenter() {
    return null;
  }

  @Override
  public int getContentViewId() {
    return R.layout.webview;
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    initWebView();
  }


  @Override
  protected void onResume() {
    super.onResume();
    webView.loadDataWithBaseURL(null, getHtmlString(), "text/html", "utf-8", null);
  }


  private String getHtmlString() {
    StringBuilder builder = new StringBuilder();

    builder.append("<html>")
        .append("<body>")
        .append("<h2>欢迎访问")
        .append("<a href=\"cloudmall://webview\"> cloud mall </a>")
        .append("</h2>")

        .append("<h2>Cloudmall ")
        .append("<a href=\"https://m.cloudmall.co/home\"> cloud mall </a>")
        .append("</h2>")

        .append("</body>")
        .append("</html>")
        ;

    return builder.toString();


  }

  private void initWebView() {
    webView.setWebViewClient(createWevClient());
    webView.setWebChromeClient(createChromeClient());
    setupForMomosoDomain();
  }


  @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "InlinedApi"})
  private void setupForMomosoDomain() {
    WebSettings settings = webView.getSettings();
    setupCookie(context);
    /**支持js*/
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    /**重设user-agent*/
    String userAgent = settings.getUserAgentString();
    Timber.d("===tag==== user agent " + userAgent + ";AndroidCloudmall");

    settings.setUserAgentString(String.valueOf(userAgent) + ";AndroidCloudmall");

    /**支持js打开新窗口*/
    settings.setJavaScriptCanOpenWindowsAutomatically(true);
    settings.setAppCacheEnabled(true);
    settings.setDatabaseEnabled(true);
    /**设置可以访问文件*/
    settings.setAllowFileAccess(true);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
      //noinspection deprecation
      settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    }
    /**webview 显示图片NARROW_COLUMNS 适应内容大小, SINGLE_COLUMN 适应屏幕，内容自动缩放*/
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    /**适应屏幕*/
    settings.setUseWideViewPort(true);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      //noinspection deprecation
      settings.setEnableSmoothTransition(true);
    }
    settings.setSaveFormData(true);
    /**编码*/
    settings.setDefaultTextEncodingName("UTF-8");
    // Fix image doesn't load. See http://stackoverflow.com/a/31513802/3962533
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
  }


  private WebViewClient createWevClient() {
    return new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
          return false;
        }
        Timber.d("===tag=== url " + url);
        if (url.startsWith("http")) {
          view.loadUrl(url);
          return true;
        }

        try {
          Uri uri = Uri.parse(url);
          String scheme = uri.getScheme();
//          if (scheme.startsWith(SchemeParser.SCHEME)) {
//             context.startActivity(SchemeSelectActivity.buildIntent(context));
////            SchemeParser.startSchemeActivities(itemView.getContext(), url);
//            return true;//当前程序处理
//          }
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          context.startActivity(intent);
          return true;
        } catch (Exception ex) {
          return false;
        }
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }
    };
  }

  private WebChromeClient createChromeClient() {
    return new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

      }
    };

  }

  @SuppressWarnings("deprecation")
  private void setupCookie(Context context) {
    /*CookieSyncManager csm = CookieSyncManager.createInstance(context.getApplicationContext());
    CookieManager cookieManager = CookieManager.getInstance();
    try {
      cookieManager.setAcceptCookie(true);
      String cookieString = AccountManager.getAuthCookie();
      cookieManager.setCookie(DEFAULT_LINK, cookieString);
    } catch (Exception ignore) {
      cookieManager.removeAllCookie();
    } finally {
      csm.sync();
    }*/
  }
}
