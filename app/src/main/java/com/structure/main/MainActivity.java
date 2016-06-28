package com.structure.main;

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
import com.structure.tab.SimpleTabActivity;
import com.structure.test.MyJniClass;

import org.apmem.tools.layouts.FlowLayout;

import java.util.HashSet;
import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay {

  @InjectView(R.id.text)
  public TextView mTextView;
  @InjectView(R.id.image)
  public ImageView mImage;
  @InjectView(R.id.flow_btn_layout)
  public FlowLayout mFlowBtnLayout;


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
    initFlowBtnLayout();
  }


  private void initFlowBtnLayout() {
    mFlowBtnLayout.removeAllViews();

    Button tabBtn = new Button(this);
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

  }


  public void setTextView(String text) {
    mTextView.setText(text);
  }




}
