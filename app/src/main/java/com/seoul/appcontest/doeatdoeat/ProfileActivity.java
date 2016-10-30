package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user_icon_64 on 2016-09-08.
 */
public class ProfileActivity extends FragmentActivity {

    private static final String TAG = "ProfileActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @InjectView(R.id.profile_layout_username) LinearLayout _nameLayout;
    @InjectView(R.id.profile_layout_email) LinearLayout _emailLayout;
    @InjectView(R.id.profile_layout_language) LinearLayout _languageLyaout;
    @InjectView(R.id.profile_layout_aboutus) LinearLayout _aboutusLayout;
    @InjectView(R.id.profile_layout_password) LinearLayout _passwordLayout;

    @InjectView(R.id.profile_photo) ImageButton _photoImage;
    @InjectView(R.id.profile_top_username) TextView _topnameText;
    @InjectView(R.id.profile_username) TextView _nameText;
    @InjectView(R.id.profile_email) TextView _emailText;
    @InjectView(R.id.profile_language) TextView _languageText;

    @InjectView(R.id.btn_top) Button _topButton;
    @InjectView(R.id.btn_tradi) Button _tipsButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;

    @InjectView(R.id.signout) Button _signoutButton;

    @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // User is signed in
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            final String defaultName = "두잇두잇";

            if(name !=null){
                _nameText.setText(name);
                _topnameText.setText(name);
            }else{
                _nameText.setText(defaultName);
                _topnameText.setText(defaultName);

            }
            _emailText.setText(email);

            SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
            String[] languages = getResources().getStringArray(R.array.language);
            String language = prefs.getString("language", languages[0]);
            _languageText.setText(language);

            InputStream is;
            try {
                is = this.getContentResolver().openInputStream( photoUrl );
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap preview_bitmap=BitmapFactory.decodeStream(is,null,options);

                Drawable icon = new BitmapDrawable(getResources(),preview_bitmap);

                _photoImage.setBackground(icon);
            } catch (FileNotFoundException e) {
                //set default image from the button
                //icon = getResources().getDrawable(R.drawable.shopping1);
            } catch (NullPointerException e){
                Toast.makeText(ProfileActivity.this, "photoUri is null", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "photoUri is null");

            }

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        // Languge
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String[] languages = getResources().getStringArray(R.array.language);
        final String language = prefs.getString("language", languages[0]);

        // 이름 변경
        _nameLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                    Flag
                    1 : name
                    2 : email
                    3 : language
                 */
                changeProfile(_nameText.getText().toString(),
                        _emailText.getText().toString(),
                        language,
                        1);
            }
        });

        // 이메일 변경
        _emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    Flag
                    1 : name
                    2 : email
                    3 : language
                 */
                changeProfile(_nameText.getText().toString(),
                        _emailText.getText().toString(),
                        language,
                        2);
            }
        });

        // Language 변경
        _languageLyaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile(_nameText.getText().toString(),
                        _emailText.getText().toString(),
                        language,
                        3);
            }
        });
        // AboutUs 페이지 이동
        _aboutusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,AboutUsActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                finish();
            }
        });
        //패스워드 변경
        _passwordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,PasswordChangeActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                finish();
            }
        });

        // 로그아웃 실행
        _signoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                signOut(auth);
            }
        });



        //Top5 화면으로 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        //Tips 화면으로 이동
        _tipsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TipsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        // 메인 화면으로 이동
        _listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        // 좋아요 페이지 이동
        _favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        // 로그아웃 이벤트 리스터
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // launch login activity
                    Toast.makeText(ProfileActivity.this, "로그아웃 완료, 로그인 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };


    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    public void signOut(FirebaseAuth auth){
        auth.signOut();
    }

    public void changeProfile(String name, String email, String language, int flag){
        Toast.makeText(ProfileActivity.this, "프로필 변경 페이지로 이동", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ProfileActivity.this,ProfileChangeActivity.class);
        i.putExtra("name", name);
        i.putExtra("email", email);
        i.putExtra("language",language);
        i.putExtra("flag", flag);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();
    }


}
