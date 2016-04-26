package com.structure.scheme;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by yuchao.
 * scheme 自定义跳转，无显示界面，通过SchemeParser解析跳转
 */
public class SchemeSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        Uri uri = getIntent().getData();
        if (null == uri) {
            finish();
            return;
        }

        SchemeParser.startSchemeActivity(this, uri.toString());

    }
}
