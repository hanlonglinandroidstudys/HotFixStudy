package hanlonglin.com.hotfixstudy.hotfix;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Bug {
    public final static String TAG="Bug";
    public static void  start(Context context){

        Log.e(TAG,"出bug啦啦！！");
        Toast.makeText(context, "出bug啦啦！！", Toast.LENGTH_SHORT).show();

//        Log.e(TAG,"牛逼的程序员已经修复！！");
//        Toast.makeText(context, "牛逼的程序员已经修复！！", Toast.LENGTH_SHORT).show();
    }
}
