package com.example.xlc.monkey.activity;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * @author:xlc
 * @date:2019/8/23
 * @descirbe:
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable @android.support.annotation.Nullable Intent intent) {

    }
}
