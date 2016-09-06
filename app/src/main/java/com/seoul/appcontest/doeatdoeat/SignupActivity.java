package com.seoul.appcontest.doeatdoeat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2016-09-05.
 */
public class SignupActivity extends FragmentActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

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

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        _btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = _inputName.getText().toString().trim();
                String email = _inputEmail.getText().toString().trim();
                String password = _inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Login Successful");

                                    // Save User Info (Name, Uri)
                                    String name = _inputName.getText().toString().trim();
                                    firebaseUser = auth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                            .build();
                                    firebaseUser.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated.");

                                                        Toast.makeText(SignupActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }else{
                                                        Log.d(TAG, "User profile update failed");
                                                    }
                                                }
                                            });
                                } else {
                                    //error
                                    Log.d(TAG, "Login Failed");
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        _btnLinkToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "로그인 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
