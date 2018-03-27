package com.bit.cse;

import com.clevertap.android.sdk.ActivityLifecycleCallback;

/**
 * Created by sharathbhragav on 25/3/18.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);

        super.onCreate();

    }
}
