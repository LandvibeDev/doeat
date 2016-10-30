package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-07.
 */

public class TipsActivity extends FragmentActivity {
    private static final String TAG = "TipsActivity";

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth auth;

    @InjectView(R.id.seouls_pick) ImageButton _seoulButton;
    @InjectView(R.id.korean_dining) ImageButton _diningButton;
    @InjectView(R.id.using_chopsticks) ImageButton _chopstickButton;
    @InjectView(R.id.ordering_foods) ImageButton _orderButton;
    @InjectView(R.id.table_manners) ImageButton _mannersButton;
    @InjectView(R.id.drinking_etiquette) ImageButton _drinkButton;

    @InjectView(R.id.btn_top) Button _topButton;
    @InjectView(R.id.btn_tradi) Button _basicButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(TipsActivity.this, LoginActivity.class));
            finish();
        }


        _chopstickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, ChopsticksActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _mannersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, TableMannerActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, DiningActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        //Top5 화면으로 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, TopActivity.class);
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
                Intent intent = new Intent(TipsActivity.this, MainActivity.class);
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
                Intent intent = new Intent(TipsActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        // 프로필 화면으로 이동
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, ProfileActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
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
}
