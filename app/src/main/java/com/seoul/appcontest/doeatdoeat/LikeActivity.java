package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.seoul.appcontest.doeatdoeat.FoodListActivity.foodView;
import static com.seoul.appcontest.doeatdoeat.FoodListActivity.menuNum;
import static com.seoul.appcontest.doeatdoeat.FoodListActivity.menuStr;
import static com.seoul.appcontest.doeatdoeat.FoodListActivity.menuStr2;

/**
 * Created by user on 2016-10-30.
 */

public class LikeActivity extends FragmentActivity {
    private static final String TAG = "LikeActivity";

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth auth;

    @InjectView(R.id.list_like) ListView _listView;
    @InjectView(R.id.btn_top) Button _topButton;
    @InjectView(R.id.btn_tradi) Button _tipsButton;
    @InjectView(R.id.btn_list) Button _listButton;
    //@InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(LikeActivity.this, LoginActivity.class));
            finish();
        }

        ListViewAdapter adapter=new ListViewAdapter();
        _listView.setAdapter(adapter);
        for(int i=0;i<FoodListActivity2.likeFoodData.size();i++){
            adapter.addItem(ContextCompat.getDrawable(this,findDraw(FoodListActivity2.likeFoodData.get(i).getName())),FoodListActivity2.likeFoodData.get(i).getName(),FoodListActivity2.likeFoodData.get(i).getShortContents());
        }
        //Top5 화면으로 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeActivity.this, TipsActivity.class);
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
                Intent intent = new Intent(LikeActivity.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        // 프로필 화면으로 이동
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeActivity.this, ProfileActivity.class);
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
    public int findDraw(String s){
        if(s.equals(MainActivity.foodList.get(0).getName())){
            return R.drawable.bibimbap;
        }else if(s.equals(MainActivity.foodList.get(1).getName())){
            return R.drawable.sungnung;
        }else if(s.equals(MainActivity.foodList.get(2).getName())){
            return R.drawable.squidrice;
        }else if(s.equals(MainActivity.foodList.get(3).getName())){
            return R.drawable.nangmyun;
        }else if(s.equals(MainActivity.foodList.get(4).getName())){
            return R.drawable.zzazangmyun;
        }else if(s.equals(MainActivity.foodList.get(5).getName())){
            return R.drawable.zzambbong;
        }else if(s.equals(MainActivity.foodList.get(6).getName())){
            return R.drawable.lamyun;
        }else if(s.equals(MainActivity.foodList.get(7).getName())){
            return R.drawable.hotbbokemmyun;
        }else if(s.equals(MainActivity.foodList.get(8).getName())){
            return R.drawable.sundaegook;
        }else if(s.equals(MainActivity.foodList.get(9).getName())){
            return R.drawable.gamzatang;
        }else if(s.equals(MainActivity.foodList.get(10).getName())){
            return R.drawable.mandugook;
        }else if(s.equals(MainActivity.foodList.get(11).getName())){
            return R.drawable.budaezzigae;
        }else if(s.equals(MainActivity.foodList.get(12).getName())){
            return R.drawable.gopchang;
        }else if(s.equals(MainActivity.foodList.get(13).getName())){
            return R.drawable.dakgalbi;
        }else if(s.equals(MainActivity.foodList.get(14).getName())){
            return R.drawable.galbizzim;
        }else if(s.equals(MainActivity.foodList.get(15).getName())){
            return R.drawable.samgyaetang;
        }else if(s.equals(MainActivity.foodList.get(16).getName())){
            return R.drawable.yukhwae;
        }else if(s.equals(MainActivity.foodList.get(17).getName())){
            return R.drawable.dakbal;
        }else if(s.equals(MainActivity.foodList.get(18).getName())){
            return R.drawable.tangsuyuk;
        }else if(s.equals(MainActivity.foodList.get(19).getName())){
            return R.drawable.samgyupsal;
        }else if(s.equals(MainActivity.foodList.get(20).getName())){
            return R.drawable.nakji;
        }else if(s.equals(MainActivity.foodList.get(21).getName())){
            return R.drawable.zzuggumi;
        }else if(s.equals(MainActivity.foodList.get(22).getName())){
            return R.drawable.ganzanggezang;
        }else if(s.equals(MainActivity.foodList.get(23).getName())){
            return R.drawable.somac;
        }else if(s.equals(MainActivity.foodList.get(24).getName())){
            return R.drawable.linggelzu;
        }else if(s.equals(MainActivity.foodList.get(25).getName())){
            return R.drawable.gozingamraezu;
        }else if(s.equals(MainActivity.foodList.get(26).getName())){
            return R.drawable.meronazu;
        }else if(s.equals(MainActivity.foodList.get(27).getName())){
            return R.drawable.makgullli;
        }else if(s.equals(MainActivity.foodList.get(28).getName())){
            return R.drawable.bingsu;
        }else if(s.equals(MainActivity.foodList.get(29).getName())){
            return R.drawable.misutgaru;
        }else if(s.equals(MainActivity.foodList.get(30).getName())){
            return R.drawable.icehongsi;
        }else if(s.equals(MainActivity.foodList.get(31).getName())){
            return R.drawable.sikhae;
        }else if(s.equals(MainActivity.foodList.get(32).getName())){
            return R.drawable.ddukbokgi;
        }else if(s.equals(MainActivity.foodList.get(33).getName())){
            return R.drawable.sundae;
        }else if(s.equals(MainActivity.foodList.get(34).getName())){
            return R.drawable.fry;
        }else if(s.equals(MainActivity.foodList.get(35).getName())){
            return R.drawable.gimbap;
        }else if(s.equals(MainActivity.foodList.get(36).getName())){
            return R.drawable.cupramyun;
        }else if(s.equals(MainActivity.foodList.get(37).getName())){
            return R.drawable.cupbbokemmyun;
        }else if(s.equals(MainActivity.foodList.get(38).getName())){
            return R.drawable.ogamzacheese;
        }else if(s.equals(MainActivity.foodList.get(39).getName())){
            return R.drawable.trianglegimbap;
        }else if(s.equals(MainActivity.foodList.get(40).getName())){
            return R.drawable.markjungsik;
        } else if(s.equals(MainActivity.foodList.get(41).getName())){
            return R.drawable.cheesebuldakbab;
        }else {
            return R.drawable.apocato;
        }
    }
    public void goToNext(){
        Intent i=new Intent(LikeActivity.this,FoodListActivity2.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();
    }
}
