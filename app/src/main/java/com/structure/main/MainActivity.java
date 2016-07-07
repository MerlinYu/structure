package com.structure.main;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.apmem.tools.layouts.FlowLayout;
import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.InjectView;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay {

  private final static String TAG = "===tag=== ";

  @InjectView(R.id.text)
  public TextView mTextView;
  @InjectView(R.id.image)
  public ImageView mImage;
  @InjectView(R.id.flow_btn_layout)
  public FlowLayout mFlowBtnLayout;
  @InjectView(R.id.image_circle)
  ImageView mCircleImage;
//  Activity


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogV("on create "+(savedInstanceState==null));
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
    personBtn.setText("person recyclerview");
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
        FileUtils.readFileByte(new File(FileUtils.getCacheDir(MainActivity.this), FileUtils.FILE_CACHE));
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

  }


  public void setTextView(String text) {
    mTextView.setText(text);
  }

  public void setImage(Uri uri) {
    LogV("uri path "+uri.getPath());
    Picasso.with(this).load(uri).into(mImage);
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
    LogV("on restore" + (savedInstanceState==null));
  }

  private void LogV(String log) {
    Log.v(TAG, log);
  }

}
