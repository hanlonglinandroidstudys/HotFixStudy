package hanlonglin.com.hotfixstudy;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hanlonglin.com.hotfixstudy.hotfix.Bug;
import hanlonglin.com.hotfixstudy.hotfix.Fix;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button btn_start;
    Button btn_fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.e(TAG,"MainActivity ClassLoader:"+MainActivity.class.getClassLoader());
//        Log.e(TAG,"MainActivity parent ClassLoader:"+MainActivity.class.getClassLoader().getParent());
//        Log.e(TAG,"Activity ClassLoader:"+ Activity.class.getClassLoader());


        btn_start=(Button)findViewById(R.id.btn_test);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试
                Bug.start(MainActivity.this);
            }
        });

//        btn_fix=(Button)findViewById(R.id.btn_fix);
//        btn_fix.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isFixed=Fix.fix(getClassLoader(),getCacheDir().getAbsolutePath(),"sdcard/fix.dex");
//                String result="";
//                if(isFixed)
//                    result="修复成功";
//                else
//                    result="修复失败！";
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
