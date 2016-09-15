package com.seoul.appcontest.doeatdoeat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @InjectView(R.id.btn_home) Button _homeButton;
    @InjectView(R.id.btn_match) Button _matchtButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;


    @InjectView(R.id.menu_rice) ImageButton _menuRice;
    @InjectView(R.id.menu_meat) ImageButton _menuMeat;
    @InjectView(R.id.menu_noodle) ImageButton _menuNoodle;
    @InjectView(R.id.menu_soup) ImageButton _menuSoup;
    @InjectView(R.id.menu_flour_based_food) ImageButton _menuFlour;
    @InjectView(R.id.menu_drink) ImageButton _menuDrink;
    @InjectView(R.id.menu_seafood) ImageButton _menuSeaFood;
    @InjectView(R.id.menu_convenient_store_food) ImageButton _menuConvenient;
    @InjectView(R.id.menu_dessert) ImageButton _menuDessert;

    List<ImageButton> menuList=new ArrayList<ImageButton>();

    private int classNum=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Get current user_icon_64
        final FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }


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

        menuList.add(_menuRice);
        menuList.add(_menuMeat);
        menuList.add(_menuNoodle);
        menuList.add(_menuSoup);
        menuList.add(_menuFlour);
        menuList.add(_menuDrink);
        menuList.add(_menuSeaFood);
        menuList.add(_menuConvenient);
        menuList.add(_menuDessert);


        for(int i=0;i<menuList.size();i++){
            menuList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=null;
                    if(view.equals(_menuRice)) {
                        intent = new Intent(MainActivity.this, MenuRiceActivity.class);
                    }else if(view.equals(_menuMeat)){
                        intent= new Intent(MainActivity.this,MenuMeatActivity.class);
                    }else if(view.equals(_menuNoodle)){
                        intent=new Intent(MainActivity.this,MenuNoodleActivity.class);
                    }else if(view.equals(_menuSoup)){
                        intent=new Intent(MainActivity.this,MenuSoupActivity.class);
                    }else if(view.equals(_menuFlour)){
                        intent=new Intent(MainActivity.this,MenuFlourActivity.class);
                    }else if(view.equals(_menuDrink)){
                        intent=new Intent(MainActivity.this,MenuDrinkActivity.class);
                    }else if(view.equals(_menuSeaFood)){
                        intent=new Intent(MainActivity.this,MenuSeaFoodActivity.class);
                    }else if(view.equals(_menuConvenient)){
                        intent=new Intent(MainActivity.this,MenuConvenientActivity.class);
                    }else if(view.equals(_menuDessert)){
                        intent=new Intent(MainActivity.this,MenuDessertActivity.class);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
                    finish();
                }
            });
        }


    }



}

