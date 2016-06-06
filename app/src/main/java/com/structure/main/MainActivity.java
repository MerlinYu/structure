package com.structure.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.person.PersonActivity;
import com.structure.test.MyJniClass;
import com.structure.test.TestActivity;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay {

  @InjectView(R.id.text)
  public TextView mTextView;
  @InjectView(R.id.btn)
  public Button mBtn;
  @InjectView(R.id.image)
  public ImageView mImage;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
    setImage();
  }

  public void refreshUI() {

  }

  @OnClick(R.id.btn)
  void btnClick(View v) {
    Log.d("tag","=======================");
    MyJniClass jniClass = new MyJniClass();
    jniClass.JniPrint();

    //mPresenter.getTestKeyWord();


    //this.startActivity(PersonActivity.buildIntent(this, "yucaho"));
  /*  Intent intent = new Intent(this, TestActivity.class);
    this.startActivity(intent);
  */

  }

  public void setTextView(String text) {
    mTextView.setText(text);
  }


  void setImage() {
    String filePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20160314_163549.jpg";
    Picasso.with(this).load(filePath).into(mImage);
  }


  void test() {
    String test = "test";
    Set<String> setList = new HashSet<>();
    setList.add(test);
    setList.add(test);
//        getClass();
    getClass().getSimpleName();
    try {
      getClass().getName();
      getClass().newInstance();
      Class.forName("Shape");
    } catch (Exception e) {

    }
  }

  class Shape {

  }


}
