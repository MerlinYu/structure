package com.structure.main;

import android.content.ComponentName;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.person.PersonActivity;
import com.structure.person.userdata.UserCard;
import com.structure.tab.SimpleTabActivity;
import com.structure.test.MyJniClass;
import com.structure.utils.FileUtils;
import com.structure.utils.GenerateBitmapTask;
import com.structure.widget.LoadingDialog;
import com.structure.widget.TouchLayout;
import com.structure.widget.TouchView;

import org.apmem.tools.layouts.FlowLayout;

import java.io.File;

import butterknife.InjectView;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay {

  private final static String TAG = "===activity=== ";

  @InjectView(R.id.text)
  public TextView mTextView;
  @InjectView(R.id.image)
  public ImageView mImage;
  @InjectView(R.id.flow_btn_layout)
  public FlowLayout mFlowBtnLayout;
  @InjectView(R.id.image_circle)
  ImageView mCircleImage;
  @InjectView(R.id.touch_layout)
  TouchLayout mTouchLayout;
  @InjectView(R.id.touch_text_view)
  TouchView mTouchView;
//  Activity

  //TODO:数据库，RXAndroid 

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogV("on create "+(savedInstanceState==null));
    setTouchListener();

  }

  @Override
  public MainPresenter createPresenter() {
    return new MainPresenter(this);
  }

  @Override
  public int getContentViewId() {
    return R.layout.activity_main;
  }

  @Override
  public void onPreCreate() {
  }

  @Override
  public void onViewCreated() {
    initFlowBtnLayout();
  }


  private void initFlowBtnLayout() {
    mFlowBtnLayout.removeAllViews();

    final Button tabBtn = new Button(this);
    tabBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    tabBtn.setText("tab activity");
    tabBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(SimpleTabActivity.buildIntent(MainActivity.this));
      }
    });
    mFlowBtnLayout.addView(tabBtn);


    Button picassoBtn = new Button(this);
    picassoBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    picassoBtn.setText("picasso load image");
    picassoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String filePath = "http://pic74.nipic.com/file/20150728/18138004_201107753000_2.jpg";
        Picasso.with(MainActivity.this)
            .load(filePath)
            .placeholder(R.mipmap.image_loading)
            .into(mImage);
      }
    });
    mFlowBtnLayout.addView(picassoBtn);

    Button personBtn = new Button(this);
    personBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    personBtn.setText("person zone");
    personBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(PersonActivity.buildIntent(MainActivity.this, " merlin"));

      }
    });
    mFlowBtnLayout.addView(personBtn);

    Button jniBtn = new Button(this);
    jniBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    jniBtn.setText(" jni btn");
    jniBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MyJniClass jniClass = new MyJniClass();
        jniClass.JniPrint();
        Toast.makeText(MainActivity.this, jniClass.getJniDisplayName(), Toast.LENGTH_SHORT).show();
        int d = mTextView.getCompoundDrawablePadding();
        float space = mTextView.getLetterSpacing();
        TextPaint textPaint = mTextView.getPaint();
      }
    });

    mFlowBtnLayout.addView(jniBtn);

    Button fileBtn = new Button(this);
    jniBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    fileBtn.setText("write file");
    fileBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String data = "Hello I am a system builder!";
        FileUtils.writeCacheFile(MainActivity.this,FileUtils.FILE_CACHE, data.getBytes());
        File file = new File(FileUtils.getCacheDir(MainActivity.this), FileUtils.FILE_CACHE);
        FileUtils.readFileByte(file);
        Toast.makeText(MainActivity.this, file.getAbsolutePath(), Toast.LENGTH_LONG).show();
      }
    });
    mFlowBtnLayout.addView(fileBtn);

    Button circleBtn = new Button(this);
    circleBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    circleBtn.setText("自定义圆角图片");
    circleBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String filePath = "http://pic74.nipic.com/file/20150728/18138004_201107753000_2.jpg";
        Picasso.with(MainActivity.this)
            .load(filePath)
            .placeholder(R.mipmap.image_loading)
            .into(mCircleImage);
      }
    });
    mFlowBtnLayout.addView(circleBtn);


    Button canvasBtn = new Button(this);
    canvasBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    canvasBtn.setText("canvas生成名片");
    canvasBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String describe = MainActivity.this.getResources().getString(R.string.life_describe);
        GenerateBitmapTask task = new GenerateBitmapTask(MainActivity.this);
        UserCard userCard = new UserCard("Merlin",
            24,
            "http://www.sougou.com/img/dog/0101.jpg",
            "185890537",
            describe,
            "yuchao.lucky@gmail.com",
            null);
        task.execute(userCard);
        }
    });
    mFlowBtnLayout.addView(canvasBtn);

    Button animBtn = new Button(this);
    animBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    animBtn.setText("loading 动画");
    animBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LoadingDialog dialog = LoadingDialog.getIntent(MainActivity.this);
        dialog.show();
       }
    });
    mFlowBtnLayout.addView(animBtn);
  }


  public void setTextView(String text) {
    mTextView.setText(text);
  }

  public void setImage(Uri uri) {
    Toast.makeText(this,"名片地址：" + uri.getPath(),Toast.LENGTH_LONG).show();
    final FrameLayout frameLayout = new FrameLayout(this);
    FrameLayout.LayoutParams frameLayoutParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    frameLayout.setLayoutParams(frameLayoutParams);
    frameLayout.setBackground(new ColorDrawable(0x5f000000));
    frameLayout.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        frameLayout.setVisibility(View.GONE);
        return true;
      }
    });

    ImageView cardView = new ImageView(this);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    cardView.setLayoutParams(params);
    params.gravity = Gravity.CENTER;
    frameLayout.addView(cardView);
    Picasso.with(this).load(uri).into(cardView);

//    getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
//    getWindow().setContentView(cardView);
    getWindow().addContentView(frameLayout,frameLayoutParams);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    LogV("on save " + (outState==null));

  }

  @Override
  protected void onRestart() {
    super.onRestart();
    LogV("on restart");
  }

  @Override
  protected void onStop() {
    super.onStop();
    LogV("on stop");
  }

  @Override
  protected void onStart() {
    super.onStart();
    boolean isTaskRoot = isTaskRoot();
    int taskId = getTaskId();
    ComponentName componentName = getComponentName();
    componentName.toString();
    LogV("on start" + " is task root " + isTaskRoot + " task id " + taskId + componentName.toString());
  }


  @Override
  protected void onPause() {
    super.onPause();
    LogV("on pause");

  }

  @Override
  protected void onResume() {
    super.onResume();
    LogV("on resume");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogV("on destroy");

  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    LogV("on restore " + (savedInstanceState==null));
  }

  private void LogV(String log) {
    Log.v(TAG, log);
  }


  @Override
  public void setFinishOnTouchOutside(boolean finish) {
    super.setFinishOnTouchOutside(finish);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    LogV("onTouchEvent action = " + MotionEvent.actionToString(event.getAction()));
    return super.onTouchEvent(event);
  }


  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    LogV("dispatchTouchEvent action = " + MotionEvent.actionToString(ev.getAction()));
    return super.dispatchTouchEvent(ev);
  }


  private void setTouchListener() {
//    mTouchLayout.setOnTouchListener(new );
    mTouchLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogV("click component name = "+v.getClass().getName());
      }
    });

    mTouchView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogV("click component name = "+v.getClass().getName());
      }
    });
  }







}
