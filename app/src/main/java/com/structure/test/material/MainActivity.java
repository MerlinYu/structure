package com.structure.test.material;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.squareup.picasso.Picasso;
import com.structure.R;
import com.structure.StructureApplication;
import com.structure.base.BaseActivity;
import com.structure.main.MainDisplay;
import com.structure.main.MainPresenter;
import com.structure.main.data.weather.WeatherData;
import com.structure.main.rx.RxActivity;
import com.structure.person.PersonActivity;
import com.structure.person.userdata.UserCard;
import com.structure.tab.SimpleTabActivity;
import com.structure.test.GifActivity;
import com.structure.test.MyJniClass;
import com.structure.test.database.DbManager;
import com.structure.test.database.TradeHistory;
import com.structure.test.database.TradeHistoryTable;
import com.structure.test.opengl.OpenGlActivity;
import com.structure.test.sensor.SensorActivity;
import com.structure.utils.BitmapHelper;
import com.structure.utils.FileUtils;
import com.structure.utils.GenerateBitmapTask;
import com.structure.utils.LogUtils;
import com.structure.web.WebActivity;
import com.structure.widget.LoadingDialog;
import com.structure.widget.TouchLayout;
import com.structure.widget.TouchView;
import com.structure.widget.view.ViewActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.InjectView;
import timber.log.Timber;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay {

  private final static String TAG = "===activity=== ";

  @InjectView(R.id.main_content)
  public RelativeLayout mMainContent;
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
  @InjectView(R.id.blur_image)
  ImageView blurImage;

  public static Intent createIntent(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTouchListener();
    // 设置系统状态栏的状态 http://blog.csdn.net/stevenhu_223/article/details/12428591
//    多种方式的隐藏or多种方式的显示
//    mMainContent.setSystemUiVisibility(View.INVISIBLE);
//    mMainContent.onScreenStateChanged(View.SCREEN_STATE_OFF);
    // 硬件加速or 软件加速
    int layerType = mMainContent.getLayerType();
    LogV("layer type " + layerType);
    mMainContent.setKeepScreenOn(true);
    mMainContent.setSoundEffectsEnabled(true);

    LogUtils.log(Build.VERSION.RELEASE);
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
    showDisplay();
  }

  private void initFlowBtnLayout() {
    mFlowBtnLayout.removeAllViews();
    final Button tabBtn = new Button(this);
    tabBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    tabBtn.setText(R.string.view_anim);
    tabBtn.setOnClickListener(v -> startActivity(SimpleTabActivity.buildIntent(MainActivity.this)));
    mFlowBtnLayout.addView(tabBtn);

    Button personBtn = new Button(this);
    personBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    personBtn.setText(R.string.style_design);
    personBtn.setOnClickListener(v -> startActivity(PersonActivity.buildIntent(MainActivity.this, " merlin")));
    mFlowBtnLayout.addView(personBtn);

    Button jniBtn = new Button(this);
    jniBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    jniBtn.setText(" jni btn");
    jniBtn.setOnClickListener(v -> {
      MyJniClass jniClass = new MyJniClass();
      jniClass.JniPrint();
      Toast.makeText(MainActivity.this, jniClass.getJniDisplayName(), Toast.LENGTH_SHORT).show();
      LogUtils.log(" jni btn click ");
    });

    mFlowBtnLayout.addView(jniBtn);

    Button fileBtn = new Button(this);
    jniBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    fileBtn.setText("write file");
    fileBtn.setOnClickListener(v -> {
      String data = "Hello I am a system builder!";
      FileUtils.writeCacheFile(MainActivity.this, FileUtils.FILE_CACHE, data.getBytes());
      File file = new File(FileUtils.getCacheDir(MainActivity.this), FileUtils.FILE_CACHE);
      FileUtils.readFileByte(file);
      Toast.makeText(MainActivity.this, file.getAbsolutePath(), Toast.LENGTH_LONG).show();
    });
    mFlowBtnLayout.addView(fileBtn);

    Button viewBtn = new Button(this);
    viewBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    viewBtn.setText("view");
    viewBtn.setOnClickListener(v -> mPresenter.startActivity(ViewActivity.createIntent(MainActivity.this)));
    mFlowBtnLayout.addView(viewBtn);


    Button circleBtn = new Button(this);
    circleBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    circleBtn.setText("picasso + 自定义圆角图片");
    circleBtn.setOnClickListener(v -> {
      String filePath = "http://pic74.nipic.com/file/20150728/18138004_201107753000_2.jpg";
      Picasso.with(MainActivity.this)
          .load(filePath)
          .resize(300, 300)
          .centerCrop()
          .placeholder(R.mipmap.image_loading)
          .into(mCircleImage);
    });
    mFlowBtnLayout.addView(circleBtn);


    Button canvasBtn = new Button(this);
    canvasBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    canvasBtn.setText("canvas生成名片");
    canvasBtn.setOnClickListener(v -> {
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
    });
    mFlowBtnLayout.addView(canvasBtn);

    Button animBtn = new Button(this);
    animBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    animBtn.setText("loading 动画");
    animBtn.setOnClickListener(v -> {
      LoadingDialog dialog = LoadingDialog.getIntent(MainActivity.this);
      dialog.show();
    });
    mFlowBtnLayout.addView(animBtn);

    final Button databaseBtn = new Button(this);
    databaseBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    databaseBtn.setText("数据库读写");
    databaseBtn.setOnClickListener(v -> {
      DbManager dbManager = DbManager.getInstance();
      TradeHistory trade = new TradeHistory("2014", "2015", "hello database!");
      dbManager.Put(trade);
      TradeHistory tradeHistory = dbManager.Get(TradeHistory.class, TradeHistoryTable.queryByName("2014"));
      Toast.makeText(StructureApplication.sApplication, "write and read  " + tradeHistory.toString(), Toast.LENGTH_LONG).show();
      //  dbManager.Delete(trade);
      dbManager.Delete(DeleteQuery.builder().table(TradeHistoryTable.TABLE).build());
      tradeHistory = dbManager.Get(TradeHistory.class, TradeHistoryTable.queryByName("2014"));
      String deleteResult = "删除成功";
      if (null != tradeHistory) {
        deleteResult = "删除失败";
      }
      Toast.makeText(StructureApplication.sApplication, "delete database " + deleteResult, Toast.LENGTH_LONG).show();
    });
    mFlowBtnLayout.addView(databaseBtn);

    Button retrofitBtn = new Button(this);
    retrofitBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    retrofitBtn.setText("retrofit 2.0 网络请求");
    retrofitBtn.setOnClickListener(v -> {
      mPresenter.getShenZhenWeather();
      mPresenter.getTestKeyWord();
    });
    mFlowBtnLayout.addView(retrofitBtn);

    Button rxBtn = new Button(this);
    rxBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    rxBtn.setText("RXJAVA ");
    rxBtn.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, RxActivity.class);
      startActivity(intent);
    });
    mFlowBtnLayout.addView(rxBtn);


    Button coorinatorBtn = new Button(this);
    coorinatorBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    coorinatorBtn.setText("coordinator ");
    coorinatorBtn.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, CoodinatorActivity.class);
      startActivity(intent);
    });
    mFlowBtnLayout.addView(coorinatorBtn);


    Button openGlBtn = new Button(this);
    coorinatorBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    openGlBtn.setText("open gl ");
    openGlBtn.setOnClickListener(v -> {
      startActivity(OpenGlActivity.createIntent(this));
    });
    mFlowBtnLayout.addView(openGlBtn);

    Button sensorBtn = new Button(this);
    coorinatorBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    sensorBtn.setText("传感器");
    sensorBtn.setOnClickListener(v -> {
//      mPresenter.retrofitRequest();
//      startActivity(new Intent(this, PayPalActivity.class));
//      startActivity(SensorActivity.createIntent(this));
      Intent intent = new Intent(this, WebActivity.class);
      startActivity(intent);
    });
    mFlowBtnLayout.addView(sensorBtn);


    Button gifButton = new Button(this);
    gifButton.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    gifButton.setText("gif view ");
    gifButton.setOnClickListener(v -> {
      startActivity(new Intent(this, GifActivity.class));
    });
    mFlowBtnLayout.addView(gifButton);


    String path = "http://image.momoso.com/cached/1bca7744406b3af5d0f5fa1c8e923623-434x650";
    String path1 = "http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg";
    BitmapHelper.setBlurImage(new WeakReference<View>(blurImage), path1, 300, 300);

  }

  public void showDisplay() {

    Display display = getWindowManager().getDefaultDisplay();
    if (null != display) {
      int state = display.getState();
      int flag = display.getFlags();
      Rect rect = new Rect();
      display.getRectSize(rect);
      Point point = new Point();
      display.getSize(point);
      LogV("state,flag " + state + " , " + flag + " rect " + rect.toString() + " point " + point.toString());
    }
  }


  public void setTextView(String text) {
    mTextView.setText(text);
  }

  public void setImage(Uri uri) {
    Toast.makeText(this, "名片地址：" + uri.getPath(), Toast.LENGTH_LONG).show();
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
    getWindow().addContentView(frameLayout, frameLayoutParams);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    LogV("on save " + (outState == null));

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
    testGc();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogV("on destroy");

  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    LogV("on restore " + (savedInstanceState == null));
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
//    LogV("onTouchEvent action = " + MotionEvent.actionToString(event.getAction()));
    return super.onTouchEvent(event);
  }


  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
//    LogV("dispatchTouchEvent action = " + MotionEvent.actionToString(ev.getAction()));
    return super.dispatchTouchEvent(ev);
  }


  private void setTouchListener() {
    mTouchLayout.setOnClickListener(v -> LogV("click component name = " + v.getClass().getName()));

    mTouchView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogV("click component name = " + v.getClass().getName());
      }
    });
  }


  public void showWeather(WeatherData data) {
    Toast.makeText(this, "shen zhen weather \n" + data, Toast.LENGTH_LONG).show();
  }


  public void testGc() {
    Object o = new Object();
    // 默认的构造函数，会使用ReferenceQueue.NULL 作为queue
    WeakReference<Object> wr = new WeakReference<Object>(o);
    Log.v("===leaks===", " null ?" + (wr.get() == null));
    o = null;
    System.gc();
    Log.v("===leaks===", " null ?" + (wr.get() == null));
  }
}
