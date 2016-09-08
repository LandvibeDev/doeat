package com.seoul.appcontest.doeatdoeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @InjectView(R.id.btn_home) Button _homeButton;
    @InjectView(R.id.btn_match) Button _matchtButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;

    @InjectView(R.id.signout) Button _signoutButton;
    @InjectView(R.id.user_name) TextView _nameText;
    @InjectView(R.id.user_email) TextView _emailText;
    @InjectView(R.id.user_id) TextView _idText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Get current user
        final FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }else{
            _nameText.setText(user.getDisplayName());
            _emailText.setText(user.getEmail());
            _idText.setText(user.getUid());
        }


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Toast.makeText(MainActivity.this, "로그아웃 완료, 로그인 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

        _signoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signOut(auth);
            }
        });

        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "프로필 화면으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    public void signOut(FirebaseAuth auth){
        auth.signOut();
    }
}
