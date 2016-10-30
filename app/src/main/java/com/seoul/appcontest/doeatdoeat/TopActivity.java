package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-28.
 */

public class TopActivity extends FragmentActivity {
    private static final String TAG = "TopActivity";

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth auth;

    @InjectView(R.id.datetime) TextView _datatimeText;

    @InjectView(R.id.btn_top) Button _topButton;
    @InjectView(R.id.btn_tradi) Button _tipsButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;
    @InjectView(R.id.top1_picture) ImageView _top1Picture;
    @InjectView(R.id.top1_title) TextView _top1Title;
    @InjectView(R.id.top1_like) TextView _top1Like;
    @InjectView(R.id.top2_picture) ImageView _top2Picture;
    @InjectView(R.id.top2_title) TextView _top2Title;
    @InjectView(R.id.top2_like) TextView _top2Like;
    @InjectView(R.id.top3_picture) ImageView _top3Picture;
    @InjectView(R.id.top3_title) TextView _top3Title;
    @InjectView(R.id.top3_like) TextView _top3Like;
    @InjectView(R.id.top4_picture) ImageView _top4Picture;
    @InjectView(R.id.top4_title) TextView _top4Title;
    @InjectView(R.id.top4_like) TextView _top4Like;
    @InjectView(R.id.top5_picture) ImageView _top5Picture;
    @InjectView(R.id.top5_title) TextView _top5Title;
    @InjectView(R.id.top5_like) TextView _top5Like;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.inject(this);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(TopActivity.this, LoginActivity.class));
            finish();
        }
        final String[] week = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int date = today.get(Calendar.DATE);
        String day = week[today.get(Calendar.DAY_OF_WEEK)-1];
        _datatimeText.setText(Integer.toString(year)+"."+Integer.toString(month)+"."+Integer.toString(date)+" "+day);

        //Tips 화면으로 이동
        _tipsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this, TipsActivity.class);
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
                Intent intent = new Intent(TopActivity.this, MainActivity.class);
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
                Intent intent = new Intent(TopActivity.this, LikeActivity.class);
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
                Intent intent = new Intent(TopActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        List<FoodData> tmpFdList=new ArrayList<>();
        for(int i=0;i<MainActivity.foodList.size();i++){
            tmpFdList.add(new FoodData(MainActivity.foodList.get(i)));
        }
        Collections.sort(tmpFdList,new desCompare());

        try{
            _top1Like.setText(Integer.toString(tmpFdList.get(0).getLike()));
            _top1Picture.setImageResource(findDraw(tmpFdList.get(0).getName()));
            _top2Like.setText(Integer.toString(tmpFdList.get(1).getLike()));
            _top2Picture.setImageResource(findDraw(tmpFdList.get(1).getName()));
            _top3Like.setText(Integer.toString(tmpFdList.get(2).getLike()));
            _top3Picture.setImageResource(findDraw(tmpFdList.get(2).getName()));
            _top4Like.setText(Integer.toString(tmpFdList.get(3).getLike()));
            _top4Picture.setImageResource(findDraw(tmpFdList.get(3).getName()));
            _top5Like.setText(Integer.toString(tmpFdList.get(4).getLike()));
            _top5Picture.setImageResource(findDraw(tmpFdList.get(4).getName()));
            for(int i=0;i<5;i++){
                if(tmpFdList.get(i).getName().length()>=5){
                    tmpFdList.get(i).setName(tmpFdList.get(i).getName().substring(0,4)+"...");
                }
            }
            _top1Title.setText(tmpFdList.get(0).getName());
            _top2Title.setText(tmpFdList.get(1).getName());
            _top3Title.setText(tmpFdList.get(2).getName());
            _top4Title.setText(tmpFdList.get(3).getName());
            _top5Title.setText(tmpFdList.get(4).getName());
        }catch(Exception e){
           Log.e(TAG,e.toString());
        }

    }
    static  class desCompare implements Comparator<FoodData>{
        @Override
        public int compare(FoodData arg0,FoodData arg1){
            return arg0.getLike()>arg1.getLike()?-1:arg0.getLike()<arg1.getLike()?1:0;
        }
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

}