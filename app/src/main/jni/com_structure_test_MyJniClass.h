/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_structure_test_MyJniClass */

#ifndef _Included_com_structure_test_MyJniClass
#define _Included_com_structure_test_MyJniClass
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_structure_test_MyJniClass
 * Method:    DisplayHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_structure_test_MyJniClass_DisplayHello
  (JNIEnv *, jobject);

/*
 * Class:     com_structure_test_MyJniClass
 * Method:    getDisplayName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_structure_test_MyJniClass_getDisplayName
  (JNIEnv *, jobject);

/*
 * Class:     com_structure_test_MyJniClass
 * Method:    getNativeName
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_structure_test_MyJniClass_getNativeName
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
