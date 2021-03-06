package com.seoul.appcontest.doeatdoeat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase firebaseDatabase;
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    public static List<FoodData> foodList;
    private boolean hasUser=false;

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
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            // 인증된 사용자가 없을때 LoginActivity로 이동
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }else{
            Log.d(TAG , "User uid : " + user.getUid());
            firebaseDatabase = FirebaseDatabase.getInstance();
            searchUser(firebaseDatabase,user.getUid(),user.getEmail());
        }

        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String[] languages = getResources().getStringArray(R.array.language);
        String language = prefs.getString("language", languages[0]);
        editor.putString("language", language);
        editor.apply();
        Log.d(TAG,"language : "+language);



        if(foodList==null){
            foodList = new ArrayList<>();
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            loadData(database,foodList,language);
        }

        // 페이지 이동
        _topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TopActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LikeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        _profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
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

    // 메인 메뉴 아이콘 클릭 이벤트
    @OnClick({R.id.menu_all, R.id.menu_rice, R.id.menu_noodle,
            R.id.menu_soup, R.id.menu_meat, R.id.menu_fish,
            R.id.menu_drink, R.id.menu_dessert, R.id.menu_street})
    public void onMenuClick(View view) {
        FoodListActivity.menuNum = view.getId();
        Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();
    }

    public void loadData(FirebaseDatabase database, final List<FoodData> foods, final String language){
        DatabaseReference myRef=database.getReference(language);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                        FoodData f=postSnapshot.getValue(FoodData.class);
                        foods.add(f);
                    }
                    Log.d(TAG, "언어 : "+language + " 데이터 로드 성공!");
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


    private void searchUser(FirebaseDatabase database, final String uid, final String email){
        final DatabaseReference myRef=database.getReference("user");
        Query searchUserQuery = myRef.child(uid);
        searchUserQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                if(u==null){
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("user");
                    writeNewUser(databaseReference, uid, email, "Korean", "");
                    Log.d(TAG, "유저가 존재 하지 않습니다. DB에 유저를 추가합니다");
                }else{
                    Log.d(TAG, "유저가 이미 존재합니다! User Email : "+u.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void writeNewUser(DatabaseReference databaseReference, String uid, String email, String language, String likeFoods) {
        // /user/user-id/values 변경
        //String key = databaseReference.push().getKey();
        User user = new User(email,language,likeFoods);
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(uid, userValues);
        databaseReference.updateChildren(childUpdates);
    }
}

