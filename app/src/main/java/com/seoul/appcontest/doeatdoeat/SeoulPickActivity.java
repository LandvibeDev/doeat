package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;



/**
 * Created by a on 2016-10-31.
 */

public class SeoulPickActivity extends Activity {
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    @InjectView(R.id.btn_back_s) Button _btnBack;
    @InjectView(R.id.btn_favorite_s) Button _favoriteButton;
    @InjectView(R.id.btn_top_s) Button _topButton;
    @InjectView(R.id.btn_tradi_s) Button _tipsButton;
    @InjectView(R.id.btn_profile_s) Button _profileButton;
    @InjectView(R.id.btn_list_s) Button _listButton;
    @InjectView(R.id.boja) Button _boja;
    @InjectView(R.id.mukja) Button _mukja;
    @InjectView(R.id.nolja) Button _nolja;
    @InjectView(R.id.gwangang) Button _gwangang;
    @InjectView(R.id.visitseoul) Button visitseoul;
    Activity act=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seoulpick);
        ButterKnife.inject(this);

        _boja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.seoul.go.kr/mw/story/alleyway/AlleyWay3.do"));
                act.startActivity(intent);
            }
        });
        _nolja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.seoul.go.kr/mw/story/alleyway/AlleyWay1.do"));
                act.startActivity(intent);
            }
        });
        _mukja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.seoul.go.kr/mw/story/alleyway/AlleyWay2.do"));
                act.startActivity(intent);
            }
        });
        visitseoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.visitseoul.net/index"));
                act.startActivity(intent);
            }
        });
        _gwangang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.visitseoul.net/index"));
                act.startActivity(intent);
            }
        });

        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeoulPickActivity.this, TipsActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                finish();
            }
        });
        // 페이지 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeoulPickActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeoulPickActivity.this, TipsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeoulPickActivity.this, ProfileActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeoulPickActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeoulPickActivity.this, MainActivity.class);
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
