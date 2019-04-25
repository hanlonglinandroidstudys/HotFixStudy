package hanlonglin.com.hotfixstudy.application;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import hanlonglin.com.hotfixstudy.hotfix.Fix;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.e(TAG, "attachBaseContext()");
        boolean isFixed = Fix.fix(getClassLoader(), getCacheDir().getAbsolutePath(), "sdcard/fix.dex");
        if (isFixed)
            Toast.makeText(this, "修复成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");


    }
}
