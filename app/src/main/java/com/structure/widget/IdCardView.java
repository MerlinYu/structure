package com.structure.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.structure.R;
import com.structure.person.userdata.UserCard;
import com.structure.utils.FileUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by yuchao on 7/5/16.
 */
public class IdCardView {

  private static final String CARD_MOTTO= "smile every day";
  private static final int DEFAULT_RADIUS = 12;

  private String TAG = "===IdCardView=== ";

  private WeakReference<Context> ctx;
  private Paint paint;
  private int radius;
  private  UserCard userCard;
  private int limitHeight;
  private CardStyle cardStyle;
  // 默认图片分辨率为高清
  private int density = DisplayMetrics.DENSITY_HIGH;
  // 图片的大小和颜色的设置
  private CardSizeSetting cardSizeSetting;
  private CardColorSetting cardColorSetting;

  public IdCardView(Context context, final UserCard userCard) {
    this(context,userCard,null);
  }

  public IdCardView(Context context, final UserCard userCard,CardStyle style) {
    ctx = new WeakReference<>(context);
    this.userCard = userCard;
    this.cardStyle = style;
    radius = DEFAULT_RADIUS;
  }


  public IdCardView setBackgroundColor(@ColorInt int color) {
    if (null == cardColorSetting) {
      cardColorSetting = new CardColorSetting();
      cardColorSetting.backgroundColor = color;
    }
    return this;
  }

  public IdCardView setBackgroundRadius(int radius) {
    this.radius = radius;
    return this;
  }

  public IdCardView limitCardHeight(int height) {
    this.limitHeight = height;
    return this;
  }

  public int getBackoundRadius() {
    return radius;
  }

  public UserCard getUserCard() {
    return userCard;
  }

  public IdCardView setUserCard(UserCard userCard) {
    this.userCard = userCard;
    return this;
  }

  public String getCardStyle() {
    return cardStyle.name();
  }

  public IdCardView setCardStyle(CardStyle style) {
    this.cardStyle = style;
    return this;
  }

  public int getDensity() {
    return density;
  }

  public IdCardView setDensity(int density) {
    this.density = density;
    return this;
  }

  public Bitmap generateIdCard() {
    Resources resources = ctx.get().getResources();
    paint = new Paint();
    prepareGenerateJob();
    Bitmap background;
    int marginTop = 0;
    int width = cardSizeSetting.picWidth;
    int height = cardSizeSetting.picHeight;
    LogV("card view width,height = "+ width + "," +height);

    try {
      background = decodeResizeBitmap(R.mipmap.ic_launcher,width, height);
      Canvas canvas = new Canvas(background);
      canvas.drawColor(cardColorSetting.backgroundColor);
      // motto
      String motto = userCard.getMotto();
      LogV(motto);
      marginTop = marginTop + cardSizeSetting.paddingTop;
      if (!TextUtils.isEmpty(motto)) {
        motto = CARD_MOTTO;
        paint.setColor(cardColorSetting.mottoTextColor);
        paint.setTextSize(cardSizeSetting.fontNormalSize);
        Rect textRect = new Rect();
        paint.getTextBounds(motto,0, motto.length(), textRect);
        canvas.drawText(motto, cardSizeSetting.paddingLeft, marginTop, paint);
        marginTop = marginTop + cardSizeSetting.fontNormalSize + cardSizeSetting.peaceDivider/2;
      }

      // avatar image
      String avatarUrl = userCard.getAvatar();
      if (!TextUtils.isEmpty(avatarUrl)) {
        String filePath = downloadImage(avatarUrl);
        Bitmap avatar = decodeResizeBitmap(filePath,
            cardSizeSetting.avatarSize,
            cardSizeSetting.avatarSize);
        canvas.drawBitmap(transformToCircle(avatar),
            (width-cardSizeSetting.avatarSize)/2,
            marginTop,
            null);
        marginTop = marginTop + cardSizeSetting.avatarSize + cardSizeSetting.peaceDivider;
      }

      // name
      String name = userCard.getName();
      if (!TextUtils.isEmpty(name)) {
        paint.setColor(cardColorSetting.nikeNameTextColor);
        paint.setTextSize(cardSizeSetting.fontNormalSize);
        Rect textRect = new Rect();
        paint.getTextBounds(name,0, name.length(), textRect);
        int textWidth = (int) paint.measureText(name, 0, name.length());
        canvas.drawText(name, (width - textWidth) / 2, marginTop, paint);
        marginTop = marginTop + cardSizeSetting.fontNormalSize + cardSizeSetting.peaceDivider/2;
      }

      // age phone number
      StringBuffer buffer = new StringBuffer();
      buffer.append(userCard.getAge() <= 0 ?
          "" : userCard.getAge() + resources.getString(R.string.year_old) + "   ");
      buffer.append(null == userCard.getPhoneNumber() ?
          "" :userCard.getPhoneNumber());
      if (!TextUtils.isEmpty(buffer.toString())) {
        paint.setColor(cardColorSetting.phoneTextColor);
        paint.setTextSize(cardSizeSetting.fontNormalSize);
        Rect rect = new Rect();
        paint.getTextBounds(buffer.toString(),0,buffer.length(),rect);
        int strWidth = (int)paint.measureText(buffer.toString(),0,buffer.length());
        canvas.drawText(buffer.toString(), (width-strWidth)/2, marginTop,paint);
        marginTop = marginTop + cardSizeSetting.fontNormalSize + cardSizeSetting.peaceDivider/2;
      }

      // address and email
      buffer.delete(0, buffer.length());
      String convince = "";
      if (null != userCard.getAddress() && null != userCard.getAddress().convince) {
        convince = userCard.getAddress().convince;
      }
      buffer.append(null == userCard.getEmail() ? "" : (userCard.getEmail() + " ")) ;
      buffer.append( convince);
      if (!TextUtils.isEmpty(buffer.toString())) {
        paint.setColor(cardColorSetting.addressTextColor);
        paint.setTextSize(cardSizeSetting.fontNormalSize);
        Rect rect = new Rect();
        paint.getTextBounds(buffer.toString(),0,buffer.length(),rect);
        int strWidth = (int)paint.measureText(buffer.toString(),0,buffer.length());
        canvas.drawText(buffer.toString(), (width-strWidth)/2, marginTop,paint);
        marginTop = marginTop + cardSizeSetting.fontNormalSize + cardSizeSetting.peaceDivider;
      }

      // describe summarize
      StaticLayout staticLayout = null;
      int descHeight = 0;
      buffer.delete(0, buffer.length());
      if (!TextUtils.isEmpty(userCard.getDescribe())) {
        buffer.append("\t" + userCard.getDescribe());
        TextPaint tPaint = new TextPaint();
        tPaint.setTextSize(cardSizeSetting.fontNormalSize);
        tPaint.setAntiAlias(true);
        staticLayout = new StaticLayout(buffer.toString(),
            tPaint,
            width - cardSizeSetting.paddingLeft * 2,
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0.5f,
            false);
        descHeight = staticLayout.getHeight();
      }

      if (staticLayout != null) {
        paint.setTextSize(cardSizeSetting.fontNormalSize);
        canvas.save();
        canvas.translate(cardSizeSetting.paddingLeft, marginTop);
        staticLayout.draw(canvas);
        marginTop = marginTop + descHeight;
        canvas.restore();
      }
      LogV("card content margin top " + marginTop);
      Bitmap roundBitmap = transformToRoundCircle(background, radius);
      return roundBitmap;
    } catch (OutOfMemoryError e) {
      e.printStackTrace();
    }
    return null;
  }

  // 准备工作
  private void prepareGenerateJob() {
    cardSizeSetting = new CardSizeSetting(density);
    if (null == cardStyle) {
      cardStyle = CardStyle.NATURE;
    }
    cardColorSetting = new CardColorSetting(cardStyle);
    //计算card view 的高度
    if (limitHeight > 0) {
      cardSizeSetting.picHeight = limitHeight;
      return;
    }
    int picViewHeight = 0;
    int divider = cardSizeSetting.peaceDivider;
    // padding height
    picViewHeight = picViewHeight + cardSizeSetting.paddingTop;
    // motto height
    if (!TextUtils.isEmpty(userCard.getMotto())) {
      picViewHeight += cardSizeSetting.fontNormalSize + divider/2;
    }
    // avatar height
    if (!TextUtils.isEmpty(userCard.getAvatar())) {
      picViewHeight += cardSizeSetting.avatarSize + divider;
    }
    // name height
    if (!TextUtils.isEmpty(userCard.getName())) {
      picViewHeight += cardSizeSetting.fontNormalSize + divider/2;
    }
    // phone height
    if (!TextUtils.isEmpty(userCard.getPhoneNumber())) {
      picViewHeight += cardSizeSetting.fontNormalSize + divider/2;
    }

    // address height
    if (null != userCard.getAddress()
        && !TextUtils.isEmpty(userCard.getAddress().convince)
        || !TextUtils.isEmpty(userCard.getEmail())) {
      picViewHeight += cardSizeSetting.fontNormalSize + divider;
    }
    // describe height
    // describe summarize
    int descHeight = 0;
    StringBuffer buffer = new StringBuffer();
    if (!TextUtils.isEmpty(userCard.getDescribe())) {
      buffer.append("\t" + userCard.getDescribe());
      TextPaint tPaint = new TextPaint();
      tPaint.setTextSize(cardSizeSetting.fontNormalSize);
      tPaint.setAntiAlias(true);
      StaticLayout staticLayout = new StaticLayout(buffer.toString(),
          tPaint,
          cardSizeSetting.picWidth - cardSizeSetting.paddingLeft * 2,
          Layout.Alignment.ALIGN_NORMAL,
          1.0f,
          0.5f,
          false);
      descHeight = staticLayout.getHeight();
      picViewHeight += descHeight;
    }
    cardSizeSetting.picHeight = reCalculateHeight(picViewHeight - cardSizeSetting.paddingLeft);
  }

//  private int calculateWidth() {
//    return PIC_WIDTH;
//  }
//
//  private int calculateHeight() {
//    return PIC_HEIGHT;
//  }


  private Bitmap decodeResizeBitmap(int resID, int targetWidth,int targetHeight) {
    Resources res = ctx.get().getResources();
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resID, options);
    options.inSampleSize = calculateSample(options, targetWidth,targetHeight);
    options.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeResource(res,resID,options);
    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth,targetHeight, false);
    bitmap.recycle();
    return scaleBitmap;
  }

  private Bitmap decodeResizeBitmap(String filePath, int targetWidth,int targetHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filePath,options);
    options.inSampleSize = calculateSample(options, targetWidth,targetHeight);
    options.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);
    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth,targetHeight, false);
    bitmap.recycle();
    return scaleBitmap;
  }


  // 圆形图片
  private Bitmap transformToCircle(Bitmap bitmap) {
    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(circleBitmap);
    Paint paint = new Paint();
    Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
    paint.setAntiAlias(true);
    canvas.drawARGB(0,0,0,0);
    canvas.drawOval(new RectF(rect),paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap,rect,rect,paint);
    bitmap.recycle();
    return circleBitmap;
  }

  // 圆角图片
  private Bitmap transformToRoundCircle(Bitmap bitmap,int radius) {
    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(circleBitmap);
    Paint paint = new Paint();
    Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
    paint.setAntiAlias(true);
    canvas.drawARGB(0,0,0,0);
    canvas.drawRoundRect(new RectF(rect), radius, radius,paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap,rect,rect,paint);
    bitmap.recycle();
    return circleBitmap;
   }


  private int calculateSample(BitmapFactory.Options options, float targetWidth, float targetHeight) {
    int sample = 1;
    int srcWidth = options.outWidth;
    int srcHeight = options.outHeight;
    if (srcHeight > targetHeight || srcWidth > targetWidth) {
      sample = Math.min(Math.round(srcHeight/targetHeight), Math.round(srcWidth/targetWidth));
    }
    return sample == 0 ? 1 : sample;
  }

  private String downloadImage(String url) {
    if(TextUtils.isEmpty(url)) {
      return null;
    }
    HttpURLConnection connection = null;
    String path = null;
    try {
      URL urlObj = new URL(url);
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setConnectTimeout(10*1000);
      connection.setReadTimeout(10*1000);
      int responseCode = connection.getResponseCode();
      if (responseCode >= 300) {
        connection.disconnect();
        throw new IOException("load image error");
      }
      InputStream inputStream = connection.getInputStream();
      path = FileUtils.writeFile(inputStream,true);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (null != connection) {
        connection.disconnect();
      }
    }
    return path;
  }

  // 黄金比例计算高度
  private int reCalculateHeight(final int contentHeight) {
    // 黄金比例为0.618 ===0.62
    int goldRateHeight = (contentHeight * 100) / 62;
    return goldRateHeight;
  }

  private void LogV(String log) {
    if (TextUtils.isEmpty(log)) {
      return;
    }
    Log.v(TAG, log);
  }

  enum CardStyle {
      NATURE,
      PERSON,
      LIGHT ,
      CUSTOMER // 自定义
  }

  class CardColorSetting {
    int backgroundColor;
    int nikeNameTextColor;
    int phoneTextColor;
    int addressTextColor;
    int mottoTextColor;
    int describeTextColor;
    int fontNormalColor;
    int dividerColor;

    public CardColorSetting(CardStyle style) {
      if (null == style) {
        return;
      }
      //TODO: 风格定义
      switch(style) {
        case NATURE:
          break;
        case PERSON:
          break;
        case LIGHT:
          break;
        case CUSTOMER:
          break;
        default:break;
      }
      Resources resources = ctx.get().getResources();
      backgroundColor = resources.getColor(R.color.green);
      nikeNameTextColor = resources.getColor(R.color.black);
      addressTextColor = resources.getColor(R.color.white);
      mottoTextColor = resources.getColor(R.color.yellow);
      describeTextColor = resources.getColor(R.color.black);
      fontNormalColor = resources.getColor(R.color.black);
      dividerColor = resources.getColor(R.color.white_gray);
      phoneTextColor = resources.getColor(R.color.black);

    }
    public CardColorSetting() {}

  }

  class CardSizeSetting {
    private static final int DEFAULT_PX = 160;
    // hpdi 高清
    private static final float DEFAULT_TIMES = 1.5f;

    int picWidth = 360;
    int picHeight = 500;

    int fontSmallSize = 12;
    int fontNormalSize = 14;
    int fontBigSize = 16;

    int peaceDivider  = 20;
    int paddingLeft = 20;
    int avatarSize = 80;
    int paddingTop = 30;
    int paddingBottom = 40;

    public CardSizeSetting(int density) {
      // android手机最低分辨率为高清
      float times = (density / DEFAULT_PX) <= DEFAULT_TIMES ?
          DEFAULT_TIMES : (density / DEFAULT_PX) ;
      picWidth = (int)(picWidth * times);
      picHeight = (int)(picHeight * times);
      peaceDivider = (int)(peaceDivider * times);
      paddingLeft = (int)(paddingLeft * times);
      avatarSize = (int)(avatarSize * times);
      paddingTop = (int)(paddingTop * times);
      paddingBottom = (int)(paddingBottom * times);
      fontSmallSize = (int)(fontSmallSize * times);
      fontNormalSize = (int)(fontNormalSize * times);
      fontBigSize = (int)(fontBigSize * times);
    }

    public CardSizeSetting() {}

    @Override
    public String toString() {
      StringBuffer buffer = new StringBuffer(512);
      buffer.append("pic width " + picWidth + "px\n ");
      buffer.append("pic height " + picHeight + "px\n");
      buffer.append("peaceDivider " + peaceDivider + "px\n");
      buffer.append("paddingLeft " + paddingLeft + "px\n");
      buffer.append("avatarSize " + avatarSize + "px\n");
      buffer.append("paddingBottom " + paddingBottom + "px\n");
      buffer.append("fontSmallSize " + fontSmallSize + "px\n");
      buffer.append("fontNormalSize " + fontNormalSize + "px\n");
      return buffer.toString();
    }
  }
}
