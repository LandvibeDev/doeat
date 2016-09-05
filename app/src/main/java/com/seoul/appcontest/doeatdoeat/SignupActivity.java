package com.seoul.appcontest.doeatdoeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2016-09-05.
 */
public class SignupActivity extends FragmentActivity {

    @InjectView(R.id.name) EditText _inputName;
    @InjectView(R.id.email) EditText _inputEmail;
    @InjectView(R.id.password) EditText _inputPassword;
    @InjectView(R.id.btnRegister) Button _btnRegister;
    @InjectView(R.id.btnLinkToLoginScreen) Button _btnLinkToLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);



        _btnLinkToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "로그인 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
