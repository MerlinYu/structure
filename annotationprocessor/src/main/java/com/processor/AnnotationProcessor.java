package com.processor;


import com.google.auto.service.AutoService;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author chaoyu
 * when compile,the system will invoke the sub class of AbstractProcessor then will auto generate you wanted class.
 *
 * */
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private Messager messager;

  private static final String ERROR_MESSAGE = "only class can be annotated with @%s";
  private static final String CLASS_PROCESSOR = "Processor";


  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    typeUtils = processingEnv.getTypeUtils();
    elementUtils = processingEnv.getElementUtils();
    filer = processingEnv.getFiler();
    messager = processingEnv.getMessager();
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }


  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = new LinkedHashSet<>();
    types.add(FactoryObject.class.getName());
    types.add(FactoryField.class.getName());
    return types;
  }

  private static String NOTE = " ===note=== ";

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      return true;
    }
    messager.printMessage(Diagnostic.Kind.NOTE, NOTE);

    for (Element element : roundEnv.getElementsAnnotatedWith(FactoryObject.class)) {
      if (element.getKind() != ElementKind.CLASS) {
        sendErrorMessage(element, ERROR_MESSAGE, FactoryObject.class.getSimpleName());
        return false;
      }
      String className = typeUtils.asElement(element.asType()) + CLASS_PROCESSOR;
      String name = element.getSimpleName().toString();

      messager.printMessage(Diagnostic.Kind.NOTE, NOTE + className);
      messager.printMessage(Diagnostic.Kind.NOTE, NOTE + " === name ==== " + name);

      try {
        JavaFileObject classFile = filer.createSourceFile(className, element);
        JavaWriter javaWriter = new JavaWriter(classFile.openWriter());
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        messager.printMessage(Diagnostic.Kind.NOTE, NOTE + packageName);

        javaWriter.emitPackage(packageName)
            .emitImports(List.class)
            .emitEmptyLine()
            .beginType(className, "class", EnumSet.of(Modifier.PUBLIC))
            .endType();



        javaWriter.close();
      } catch (IOException e) {
        messager.printMessage(Diagnostic.Kind.ERROR, "IOException");
        e.printStackTrace();
      } catch (IllegalStateException e) {
        messager.printMessage(Diagnostic.Kind.ERROR, "IllegalStateException");
      }
      finally {
//        javaWriter.close();

      }
    }
    return false;
  }

  private void sendErrorMessage(Element e, String msg, Object... args) {
    messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
  }


  public static void main(String[] args) {
 /*   JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    final JavaCompiler.CompilationTask task = compiler.getTask(
        null,
        null,
        null,
        null,
        Collections.singleton(AnnotationProcessor.class.getName()),
        Collections.EMPTY_SET
    );
    task.setProcessors(Collections.singleton(new AnnotationProcessor()));
    task.call();
*/
    ArrayList<String> capacityList = new ArrayList<>(2);
    capacityList.add("hello");
    capacityList.add(" world");
    capacityList.add(" !");
    System.out.println(capacityList.toString() + " length " + capacityList.size());


    try {
      Shape a = new Shape();
      a.name = "merlin";
      Shape b = (Shape)a.clone();
      b.name = "smart ";
      System.out.println("a " + a.name + " b " +b.name);
      // 输出结果 merlin start

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    // arraylist 的克隆
    ArrayList<String> bArray = (ArrayList<String>) capacityList.clone();
    bArray.add(" who are you?");
    System.out.println("a " +capacityList.toString() + " length " + capacityList.size());
    System.out.println("b "+bArray.toString() + " length " + bArray.size());
    // 输出结果：
    // a [hello,  world,  !] length 3
    // b [hello,  world,  !,  who are you?] length 4

    HashMap<String, String> cityMap = new HashMap<>();
    // hashmap的迭代方式
    Iterator<Map.Entry<String, String>> it = cityMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String,String> entry = it.next();
      entry.getValue();
      entry.getKey();
    }

  }

  // 影子克隆也称简单克隆当类中属性是简单的String 类型时可以使用，如果类中有数组或复杂的类时必须使用尝试克隆
  static class Shape implements Cloneable{
    String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

}
