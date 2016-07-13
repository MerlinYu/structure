package com.structure.main.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.main.data.weather.WeatherData;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.InjectView;

/**
 * Created by yuchao on 7/13/16.
 */
public class RxActivity extends BaseActivity<RxActivityPresenter> {

  @InjectView(R.id.flow_btn_layout)
  FlowLayout mFlowLayout;



  @Override
  public RxActivityPresenter createPresenter() {
    return new RxActivityPresenter(this);
  }

  @Override
  public int getContentViewId() {
    return R.layout.rx_activity_layout;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFlowlayout();

  }

  private void initFlowlayout() {
    mFlowLayout.removeAllViews();

    Button rxJavaBtn = new Button(this);
    rxJavaBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    rxJavaBtn.setText("rx java ");
    rxJavaBtn.setOnClickListener(v -> {
      mPresenter.createObservable();
      mPresenter.createSubscriber();
      mPresenter.subscribe();
    });
    mFlowLayout.addView(rxJavaBtn);

    Button operationBtn = new Button(this);
    operationBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    operationBtn.setText("操作符 ");
    operationBtn.setOnClickListener(v -> {
      mPresenter.operationalCharacter();
    });
    mFlowLayout.addView(operationBtn);

    Button errorCompleteBtn = new Button(this);
    errorCompleteBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    errorCompleteBtn.setText("错误和完成");
    errorCompleteBtn.setOnClickListener(v -> {
      mPresenter.errorAndCompleted();
    });
    mFlowLayout.addView(errorCompleteBtn);

    Button retrofitBtn = new Button(this);
    retrofitBtn.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    retrofitBtn.setText("主线程刷新UI");
    retrofitBtn.setOnClickListener(v -> {
      mPresenter.runThreadTime();
    });
    mFlowLayout.addView(retrofitBtn);
  }

  public void weatherToast(WeatherData weatherData) {
    Toast.makeText(this,"Retrofit RxJava\n " + weatherData.toString(),Toast.LENGTH_LONG).show();
  }
}
