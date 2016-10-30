package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-31.
 */

public class ChopsticksActivity extends FragmentActivity {
    private static final String TAG = "ChopsticksActivity";

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    @InjectView(R.id.btn_back) Button _btnBack;
    @InjectView(R.id.title) TextView _titleText;
    @InjectView(R.id.contents1) TextView _contents1Text;
    @InjectView(R.id.contents2) TextView _contents2Text;
    @InjectView(R.id.contents3) TextView _contents3Text;

    @InjectView(R.id.btn_top)
    Button _topButton;
    @InjectView(R.id.btn_tradi)
    Button _tipsButton;
    @InjectView(R.id.btn_list)
    Button _listButton;
    @InjectView(R.id.btn_favorite)
    Button _favoriteButton;
    @InjectView(R.id.btn_profile)
    Button _profileButton;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chopsticks);
        ButterKnife.inject(this);

        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String[] languages = getResources().getStringArray(R.array.language);
        String language = prefs.getString("language", languages[0]);
        Log.d(TAG,"language : "+language);

        firebaseDatabase = FirebaseDatabase.getInstance();
        loadTipsData(firebaseDatabase,language,"chopsticks");

        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });

        // 페이지 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChopsticksActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChopsticksActivity.this, TipsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChopsticksActivity.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        _favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChopsticksActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChopsticksActivity.this, ProfileActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToMain();
    }

    private void goToMain(){
        Intent i= new Intent(ChopsticksActivity.this,TipsActivity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
        finish();
    }
    private void loadTipsData(FirebaseDatabase database, final String language, String pageType){
        DatabaseReference myRef=database.getReference("tips/" + language + "/" + pageType);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    TipsData t = dataSnapshot.getValue(TipsData.class);
                    String[] contentsArray=t.getContents().split("\n");
                    setTextView(t.getTitle(), contentsArray[0],contentsArray[1],contentsArray[2]);
                    Log.d(TAG, "데이터 로드 성공!");
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }catch(DatabaseException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"Failed to read value", databaseError.toException());
            }
        });

    }
    private void setTextView(String title, String contents1, String contents2, String contents3){
        _titleText.setText(title);
        _contents1Text.setText(contents1);
        _contents2Text.setText(contents2);
        _contents3Text.setText(contents3);
    }


}
