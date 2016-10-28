package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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


    @InjectView(R.id.btn_back) Button _backButton;
    @InjectView(R.id.btn_change) Button _changeButton;

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
        auth = FirebaseAuth.getInstance();

        final String user_name;
        final String user_email;
        final Uri user_photoUrl;
        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // User is signed in
            // Name, email address, and profile photo Url
            user_name = user.getDisplayName();
            user_email = user.getEmail();
            user_photoUrl = user.getPhotoUrl();

        }

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
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
        //키보드 보이기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 숨기기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_nameInput.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(_emailInput.getWindowToken(), 0);

                Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();
            }
        });

        _changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = _nameInput.getText().toString();
                if(!inputName.equals(name)) {
                    changeProfile(user, inputName);
                }
                String inputEmail = _emailInput.getText().toString();
                if(!inputEmail.equals(email)){
                    changeEmail(user, inputEmail);
                }

                Log.d(TAG,"view change");
                Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();

            }
        });
    }
    public void changeProfile(FirebaseUser user, String name /*, Uri photoUri*/){
        // Async
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
//                .setPhotoUri(photoUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            //Toast.makeText(ProfileChangeActivity.this, "profile change success", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(0,0);
                            finish();
                        }
                    }
                });

    }
    public void changeEmail(FirebaseUser user, String email){
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            //Toast.makeText(ProfileChangeActivity.this, "email change success", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(0,0);
                            finish();
                        }
                    }
                });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
