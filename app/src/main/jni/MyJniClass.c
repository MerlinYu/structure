#include <jni.h>
#include "com_structure_test_MyJniClass.h"
#include <android/log.h>
#include <stdio.h>

#ifndef LOG_TAG
#define LOG_TAG "ANDROID_LAB"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#endif


JNIEXPORT void JNICALL Java_com_structure_test_MyJniClass_DisplayHello
        (JNIEnv *env, jobject obj)
{
    LOGE("log string from ndk.");
    return;
}

JNIEXPORT jstring JNICALL Java_com_structure_test_MyJniClass_getDisplayName
        (JNIEnv *env, jobject obj)
{
    char *str="String from native C";
    return (*env)->NewStringUTF(env, str);
}

JNIEXPORT jint JNICALL Java_com_structure_test_MyJniClass_getNativeName
        (JNIEnv * env, jobject obj)
{
    return 5;
}



