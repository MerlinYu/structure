package com.structure.base;

/**
 * Created by yuchao.
 * M-V-P 中的P负责逻辑处理
 */
public interface BasePresenter<V extends BaseDisplay, M extends BaseModule> {
    V getDisplay();
    M getModule();
}
