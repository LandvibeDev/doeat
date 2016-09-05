package com.seoul.appcontest.doeatdoeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2016-09-02.
 */
public class LoginActivity extends FragmentActivity {

    private static final String Tag = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private CallbackManager mCallbackManager;
    private AccessToken mToken = null;

    @InjectView(R.id.email) EditText _emailText;
    @InjectView(R.id.password) EditText _passwordText;
    @InjectView(R.id.btnLogin) Button _loginButton;
    @InjectView(R.id.btnLinkToRegisterScreen) TextView _signupLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        FaceBoock
         */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());;
        mCallbackManager = CallbackManager.Factory.create();
        mToken = AccessToken.getCurrentAccessToken();

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _signupLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "회원가입 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

}