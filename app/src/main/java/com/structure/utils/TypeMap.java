package com.structure.utils;

import android.util.SparseArray;

/**
 * Created by yuchao.
 *用来保存viewholder对应的类型
 */
public class TypeMap {

    private SparseArray<Integer> typeSparse = new SparseArray<>();

    public int getCount() {
        return typeSparse.size();
    }

    public void putType(int index, int type){
        typeSparse.put(index,type);
    }

    public void clear() {
        typeSparse.clear();
    }

    public int getType(int index) {
        if (index >= typeSparse.size())
            return -1;
        return typeSparse.get(index);
    }

    public int getIndex(int type) {
        int size = typeSparse.size();
        for (int i=0; i< size; i++) {
            if (typeSparse.valueAt(i) == type) {
                return typeSparse.keyAt(i);
            }
        }
        return -1;
    }





}
