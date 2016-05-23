package com.processor;

import java.io.IOException;

import javax.lang.model.element.TypeElement;

/**
 * Created by yuchao on 5/23/16.
 */
public class FactoryProduce {



  TypeElement typeElement;

  public FactoryProduce(TypeElement typeElement) {
    this.typeElement = typeElement;


  }

  void produceClass() throws IOException{

    FactoryObject meal = typeElement.getAnnotation(FactoryObject.class);


  }

}
