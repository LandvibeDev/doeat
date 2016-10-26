package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-27.
 */

public class ProfileChangeActivity extends FragmentActivity {
    private static final String TAG = "ProfileChangeActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @InjectView(R.id.name) AppCompatEditText _nameInput;
    @InjectView(R.id.email) AppCompatEditText _emailInput;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        int focusFlag = intent.getIntExtra("flag", 1);

        _nameInput.setText(name);
        _emailInput.setText(email);
        if(focusFlag==1) {
            _nameInput.requestFocus();
            _nameInput.setSelection(_nameInput.getText().length());
        }else if(focusFlag==2){
            _emailInput.requestFocus();
            _emailInput.setSelection(_emailInput.getText().length());
        }
        //키보드 보이게 하는 부분
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
//            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
//            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//            overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
//            finish();
//        }
//        return true;
//    }
}
