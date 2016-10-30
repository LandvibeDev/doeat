package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by a on 2016-09-21.
 */
public class FoodListActivity extends Activity {
    private static final String TAG = "MainActivity";
    @InjectView(R.id.btn_back) Button _btnBack;
    @InjectView(R.id.list_food) ListView _listView;
    @InjectView(R.id.list_title) TextView _title;

    @InjectView(R.id.btn_top) Button _topButton;
    @InjectView(R.id.btn_tradi) Button _tipsButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;

    public static String menuStr;
    public static int menuNum;
    public static String menuStr2;
    public static View foodView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        ButterKnife.inject(this);

        // 페이지 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListActivity.this, TipsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListActivity.this, ProfileActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        ListViewAdapter adapter=new ListViewAdapter();
        _listView.setAdapter(adapter);
        _title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));

        // 메인에서 선택한 메뉴에 따라 보이는 List 설정

        try{
            if(menuNum==R.id.menu_rice){
                _title.setText("Rice");
                addRice(adapter);
            }else if(menuNum==R.id.menu_noodle){
                _title.setText("Noodle");
                addNoodle(adapter);
            }else if(menuNum==R.id.menu_soup){
                _title.setText("Soup");
                addSoup(adapter);
            }else if(menuNum==R.id.menu_meat){
                _title.setText("Meat");
                addMeat(adapter);
            }else if(menuNum==R.id.menu_fish){
                _title.setText("Fish");
                addFish(adapter);
            }else if(menuNum==R.id.menu_drink){
                _title.setText("Drink");
                addDrink(adapter);
            }else if(menuNum==R.id.menu_dessert){
                _title.setText("Dessert");
                addDessert(adapter);
            }else if(menuNum==R.id.menu_street){
                _title.setText("Street");
                addStreet(adapter);
            }else{
                //menuNum == R.id.menu_all
                _title.setText("All");
                addRice(adapter);
                addNoodle(adapter);
                addSoup(adapter);
                addMeat(adapter);
                addFish(adapter);
                addDrink(adapter);
                addDessert(adapter);
                addStreet(adapter);
            }
        }catch(Exception e){Log.e(TAG,e.toString());}


        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodView=((ViewGroup)view).getChildAt(0);
                if(menuNum==R.id.menu_rice){
                    menuStr2=MainActivity.foodList.get(i).getName();
                    if(i==0){menuStr="xBRlpIwJP64";}
                    else if(i==1){menuStr="pChrA7Zt_8Y";}
                    else if(i==2){menuStr="";}
                }else if(menuNum==R.id.menu_noodle){
                    menuStr2=MainActivity.foodList.get(i+3).getName();
                    if(i==0){menuStr="rTr0MvRVaWQ";}
                    else if(i==1){menuStr="vTkMEu4s674";}
                    else if(i==2){menuStr="vTkMEu4s674";}
                    else if(i==3){menuStr="uqRIH7QmAu4";}
                    else if(i==4){menuStr="AIhaFjIMeMg";}
                }else if(menuNum==R.id.menu_meat){
                    menuStr2=MainActivity.foodList.get(i+12).getName();
                    if(i==0){menuStr="tkaprRUwSwU";}
                    else if(i==1){menuStr="tsjooxxZxFc";}
                    else if(i==2){menuStr="";}
                    else if(i==3){menuStr="";}
                    else if(i==4){menuStr="lt8vAV3fiy4";}
                    else if(i==5){menuStr="Vy2sqhWexyI";}
                    else if(i==6){menuStr="vTkMEu4s674";}
                    else if(i==7){menuStr="e3J24CgVKYM";}
                }else if(menuNum==R.id.menu_fish){
                    menuStr2=MainActivity.foodList.get(i+20).getName();
                    if(i==0){menuStr="JXcGYjZcamg";}
                    else if(i==1){menuStr="GhwPqdGDqIU";}
                    else if(i==2){menuStr="DstzvJuPBN0";}
                }else if(menuNum==R.id.menu_drink){
                    menuStr2=MainActivity.foodList.get(i+23).getName();
                    if(i==0){menuStr="";}
                    else if(i==1){menuStr="";}
                    else if(i==2){menuStr="";}
                    else if(i==3){menuStr="";}
                    else if(i==4){menuStr="";}
                }else if(menuNum==R.id.menu_dessert){
                    menuStr2=MainActivity.foodList.get(i+28).getName();
                    if(i==0){menuStr="2G-3r0OO4UE";}
                    else if(i==1){menuStr="";}
                    else if(i==2){menuStr="";}
                    else if(i==3){menuStr="";}
                }else if(menuNum==R.id.menu_street){
                    menuStr2=MainActivity.foodList.get(i+32).getName();
                    if(i==0){menuStr="xWWtNryEGgs";}
                    else if(i==1){menuStr="_9jjjAe9xKk";}
                    else if(i==2){menuStr="";}
                    else if(i==3){menuStr="";}
                    else if(i==4){menuStr="Tr70mg519r8";}
                    else if(i==5){menuStr="A8ysMcsOKyg";}
                    else if(i==6){menuStr="2ke4DhjKOPU";}
                    else if(i==7){menuStr="jKbgPTWZNSw";}
                    else if(i==8){menuStr="";}
                    else if(i==9){menuStr="1i1KkDqyD74";}
                    else if(i==10){menuStr="3RWsR73M3F4";}
                }else if(menuNum==R.id.menu_soup){
                    menuStr2=MainActivity.foodList.get(i+8).getName();
                    if(i==0){menuStr="krewH_3efoA";}
                    else if(i==1) {menuStr = "3IA172p8rI8";}
                    else if(i==2){menuStr="_9jjjAe9xKk";}
                    else if(i==3){menuStr="p44BDmajNmI";}
                }else{
                    menuStr2=MainActivity.foodList.get(i).getName();
                }
                goToNext();
            }
        });
        _btnBack=(Button)findViewById(R.id.btn_back);
        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToMain();
    }

    public void goToMain(){
        Intent i= new Intent(FoodListActivity.this,MainActivity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
        finish();
    }

    public void goToNext(){
        Intent i=new Intent(FoodListActivity.this,FoodListActivity2.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();
    }

    public void addRice(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bibimbap),MainActivity.foodList.get(0).getName(),MainActivity.foodList.get(0).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sungnung),MainActivity.foodList.get(1).getName(),MainActivity.foodList.get(1).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squidrice),MainActivity.foodList.get(2).getName(),MainActivity.foodList.get(2).getShortContents());
    }
    public void addNoodle(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nangmyun),MainActivity.foodList.get(3).getName(),MainActivity.foodList.get(3).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzazangmyun),MainActivity.foodList.get(4).getName(),MainActivity.foodList.get(4).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzambbong),MainActivity.foodList.get(5).getName(),MainActivity.foodList.get(5).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.lamyun),MainActivity.foodList.get(6).getName(),MainActivity.foodList.get(6).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.hotbbokemmyun),MainActivity.foodList.get(7).getName(),MainActivity.foodList.get(7).getShortContents());
    }
    public void addSoup(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundaegook),MainActivity.foodList.get(8).getName(),MainActivity.foodList.get(8).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gamzatang),MainActivity.foodList.get(9).getName(),MainActivity.foodList.get(9).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.mandugook),MainActivity.foodList.get(10).getName(),MainActivity.foodList.get(10).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.budaezzigae),MainActivity.foodList.get(11).getName(),MainActivity.foodList.get(11).getShortContents());
    }
    public void addMeat(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gopchang),MainActivity.foodList.get(12).getName(),MainActivity.foodList.get(12).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakgalbi),MainActivity.foodList.get(13).getName(),MainActivity.foodList.get(13).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.galbizzim),MainActivity.foodList.get(14).getName(),MainActivity.foodList.get(14).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyaetang),MainActivity.foodList.get(15).getName(),MainActivity.foodList.get(15).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.yukhwae),MainActivity.foodList.get(16).getName(),MainActivity.foodList.get(16).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakbal),MainActivity.foodList.get(17).getName(),MainActivity.foodList.get(17).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.tangsuyuk),MainActivity.foodList.get(18).getName(),MainActivity.foodList.get(18).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyupsal),MainActivity.foodList.get(19).getName(),MainActivity.foodList.get(19).getShortContents());
    }
    public void addFish(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nakji),MainActivity.foodList.get(20).getName(),MainActivity.foodList.get(20).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzuggumi),MainActivity.foodList.get(21).getName(),MainActivity.foodList.get(21).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ganzanggezang),MainActivity.foodList.get(22).getName(),MainActivity.foodList.get(22).getShortContents());
    }
    public void addDrink(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.somac),MainActivity.foodList.get(23).getName(),MainActivity.foodList.get(23).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.linggelzu),MainActivity.foodList.get(24).getName(),MainActivity.foodList.get(24).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gozingamraezu),MainActivity.foodList.get(25).getName(),MainActivity.foodList.get(25).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.meronazu),MainActivity.foodList.get(26).getName(),MainActivity.foodList.get(26).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.makgullli),MainActivity.foodList.get(27).getName(),MainActivity.foodList.get(27).getShortContents());
    }
    public void addDessert(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bingsu),MainActivity.foodList.get(28).getName(),MainActivity.foodList.get(28).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.misutgaru),MainActivity.foodList.get(29).getName(),MainActivity.foodList.get(29).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.icehongsi),MainActivity.foodList.get(30).getName(),MainActivity.foodList.get(30).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sikhae),MainActivity.foodList.get(31).getName(),MainActivity.foodList.get(31).getShortContents());
    }
    public void addStreet(ListViewAdapter adapter){
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ddukbokgi),MainActivity.foodList.get(32).getName(),MainActivity.foodList.get(32).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundae),MainActivity.foodList.get(33).getName(),MainActivity.foodList.get(33).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.fry),MainActivity.foodList.get(34).getName(),MainActivity.foodList.get(34).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gimbap),MainActivity.foodList.get(35).getName(),MainActivity.foodList.get(35).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupramyun),MainActivity.foodList.get(36).getName(),MainActivity.foodList.get(36).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupbbokemmyun),MainActivity.foodList.get(37).getName(),MainActivity.foodList.get(37).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ogamzacheese),MainActivity.foodList.get(38).getName(),MainActivity.foodList.get(38).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.trianglegimbap),MainActivity.foodList.get(39).getName(),MainActivity.foodList.get(39).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.markjungsik),MainActivity.foodList.get(40).getName(),MainActivity.foodList.get(40).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cheesebuldakbab),MainActivity.foodList.get(41).getName(),MainActivity.foodList.get(41).getShortContents());
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.apocato),MainActivity.foodList.get(42).getName(),MainActivity.foodList.get(42).getShortContents());
    }

}
