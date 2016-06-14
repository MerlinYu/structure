# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/imaygou/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-verbose            #混淆时记录日志
-dontpreverify      #混淆时

-keep public class * extends android.app.Activity # 保持不被混淆
#忽略警告
-dontwarn android.support.**
-dontwarn com.google.auto.common.*
-dontwarn javax.annotation.**
-dontwarn com.processor.AnnotationProcessor
-dontwarn com.google.auto.service.processor.*
-dontwarn com.google.common.**
-dontwarn om.processor.AnnotationProcesso
-dontwarn com.processor.FactoryProduce
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn com.squareup.picasso.*
-dontwarn okio.Okio
-dontwarn okio.DeflaterSink
-dontwarn rx.internal.util.**
-dontwarn retrofit2.Platform$Java8
-dontwarn okhttp3.internal.Platform

#butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#gson

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*
-keep class * {
  @com.google.gson.annotations.Expose <fields>;
  @com.google.gson.annotations.Expose <init>(...);
}

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }




