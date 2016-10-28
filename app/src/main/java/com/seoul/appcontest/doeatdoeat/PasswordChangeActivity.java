package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-27.
 */

public class PasswordChangeActivity extends FragmentActivity {
    private static final String TAG = "PasswordChangeActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


    @InjectView(R.id.btn_back) Button _backButton;
    @InjectView(R.id.btn_change) Button _changeButton;

    @InjectView(R.id.password1) AppCompatEditText _password1Input;
    @InjectView(R.id.password2) AppCompatEditText _password2Input;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        ButterKnife.inject(this);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.


//        AuthCredential credential = EmailAuthProvider
//                .getCredential("user@example.com", "password1234");
//
//        // Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "User re-authenticated.");
//                    }
//                });

        //키보드 보이기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        _password1Input.requestFocus();

        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 감추기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_password1Input.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(_password2Input.getWindowToken(), 0);

                Intent i= new Intent(PasswordChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();
            }
        });

        _changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = _password1Input.getText().toString();
                String password2 = _password2Input.getText().toString();

                if(password1.length() < 6 || password2.length() < 6){
                    Toast.makeText(PasswordChangeActivity.this, "비밀번호를 6자리 이상 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password1.equals(password2)){
                    Toast.makeText(PasswordChangeActivity.this, "입력한 두 비밀번호가 다릅니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                changePassword(user,password1);

                // 키보드 감추기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_password1Input.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(_password2Input.getWindowToken(), 0);

                // 화면전환
                Log.d(TAG,"view change");
                Intent i= new Intent(PasswordChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();

            }
        });
    }

    public void changePassword(FirebaseUser user, String newPassword){

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordChangeActivity.this, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            Intent i= new Intent(PasswordChangeActivity.this,ProfileActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

}
