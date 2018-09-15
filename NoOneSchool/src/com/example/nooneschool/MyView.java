package com.example.nooneschool;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

public abstract class MyView<T> {

    protected Activity activity;
    protected LayoutInflater inflate;
    protected T t;

    public MyView(Activity activity) {
        this.activity = activity;
        inflate = LayoutInflater.from(activity);
    }

    public boolean fillView(T t, ListView listView) {
        if (t == null) {
            return false;
        }
        if ((t instanceof List) && ((List) t).size() == 0) {
            return false;
        }
        this.t = t;
        getView(t, listView);
        return true;
    }

    protected abstract void getView(T t, ListView listView);

}
