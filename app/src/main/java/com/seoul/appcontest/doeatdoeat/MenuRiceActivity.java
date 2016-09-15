package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Created by a on 2016-09-15.
 */
public class MenuRiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rice);
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            Intent i= new Intent(MenuRiceActivity.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
            finish();
        }
        return true;
    }

}
