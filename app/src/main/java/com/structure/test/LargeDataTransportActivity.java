package com.structure.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.structure.R;

/**
 * Created by yuchao.
 */
public class LargeDataTransportActivity extends AppCompatActivity{

    private Item item;

    /** 传递大容量数据方法
     * Intent 理论上可以传递1M的数据，但是Object类的大小不能单纯的以String的大小来算，
     * 一般只要String超过512kb，可以采用此方法，避免意想不到的的bug.
     * 采用此方法比单例和static filed 好的地方是不用担心清理。
     * */
    private enum ItemDataHolder {
        DATA;
        private Item item;

        public static boolean hasData() {
            return DATA.item != null;
        }

        public static void setData(Item item) {
            DATA.item = item;
        }

        public static Item getData() {
            return DATA.item;
        }
    }

    public static Intent getIntent(Context context, Item item) {
        Intent intent = new Intent(context, LargeDataTransportActivity.class);
        ItemDataHolder.setData(item);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = ItemDataHolder.getData();
    }

}
