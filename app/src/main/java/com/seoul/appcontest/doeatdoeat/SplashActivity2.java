package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by user_icon_64 on 2016-09-11.
 */
public class SplashActivity2 extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 2000); // 5초 후에 hd Handler 실행
    }

    private class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
            SplashActivity2.this.finish(); // 로딩페이지 Activity Stack에서 제거
            overridePendingTransition(R.anim.fade,R.anim.cycle_7);
        }
    }
}
