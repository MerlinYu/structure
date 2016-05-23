package com.processor;


import com.google.auto.service.AutoService;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private Messager messager;

  private static final String ERROR_MESSAGE = "only class can be annotated with @%s";


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
      String className = typeUtils.asElement(element.asType()) + "Processor";

      messager.printMessage(Diagnostic.Kind.NOTE, NOTE + className);

      try {
        JavaFileObject classFile = filer.createSourceFile(className, element);
        JavaWriter javaWriter = new JavaWriter(classFile.openWriter());
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        messager.printMessage(Diagnostic.Kind.NOTE, NOTE + packageName);

        javaWriter.emitPackage(packageName)
            .emitImports(List.class)
            .emitEmptyLine()
            .beginType(className, "class", EnumSet.of(Modifier.PUBLIC))
            .endControlFlow();


        javaWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
//        sendErrorMessage();

//        System.out.println(e.printStackTrace());
      }
    }
    return false;
  }

  private void sendErrorMessage(Element e, String msg, Object... args) {
    messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
  }


  public static void main(String[] args) {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    final JavaCompiler.CompilationTask task = compiler.getTask(
        null,
        null,
        null,
        null,
        Collections.singleton(TestProcessor.class.getName()),
        Collections.EMPTY_SET
    );
    task.setProcessors(Collections.singleton(new TestProcessor()));
    task.call();
  }

}