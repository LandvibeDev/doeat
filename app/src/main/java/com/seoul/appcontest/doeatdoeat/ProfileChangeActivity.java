package com.seoul.appcontest.doeatdoeat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2016-10-27.
 */

public class ProfileChangeActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProfileChangeActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private String newLanguage;

    @InjectView(R.id.btn_back) Button _backButton;
    @InjectView(R.id.btn_change) Button _changeButton;

    @InjectView(R.id.name) AppCompatEditText _nameInput;
    @InjectView(R.id.email) AppCompatEditText _emailInput;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);
        ButterKnife.inject(this);
        auth = FirebaseAuth.getInstance();

        final String user_name;
        final String user_email;
        final Uri user_photoUrl;
        firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in
            // Name, email address, and profile photo Url
            user_name = firebaseUser.getDisplayName();
            user_email = firebaseUser.getEmail();
            user_photoUrl = firebaseUser.getPhotoUrl();

        }

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String language = intent.getStringExtra("language");
        int focusFlag = intent.getIntExtra("flag", 1);

        _nameInput.setText(name);
        _emailInput.setText(email);
        if(focusFlag==1) {
            _nameInput.requestFocus();
            _nameInput.setSelection(_nameInput.getText().length());
        }else if(focusFlag==2){
            _emailInput.requestFocus();
            _emailInput.setSelection(_emailInput.getText().length());
        }

        if(focusFlag!=3){
            //키보드 보이기
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        // 언어 설정 스피너 adapter 설정
        Spinner spinner = (Spinner) findViewById(R.id.language_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        String[] languages = getResources().getStringArray(R.array.language);
        for (int i =0 ;i<languages.length;i++){
            if(language.equals(languages[i])){
                spinner.setSelection(i);
            }
        }


        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 숨기기
                hideKeyboard();
                Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();
            }
        });

        _changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = _nameInput.getText().toString();
                if(!inputName.equals(name)) {
                    changeProfile(firebaseUser, inputName);
                }
                String inputEmail = _emailInput.getText().toString();
                if(!inputEmail.equals(email)){
                    changeEmail(firebaseUser, inputEmail);
                }

                if(newLanguage!=null && !newLanguage.equals(language)){
                    changeLanguage(firebaseUser,newLanguage);
                }

                //키보드 숨기기
                hideKeyboard();
                Log.d(TAG,"view change");
                Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();

            }
        });
    }
    public void changeProfile(FirebaseUser user, String name /*, Uri photoUri*/){
        // Async
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
//                .setPhotoUri(photoUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            //Toast.makeText(ProfileChangeActivity.this, "profile change success", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(0,0);
                            finish();
                        }
                    }
                });

    }
    public void changeEmail(FirebaseUser user, String email){
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            //Toast.makeText(ProfileChangeActivity.this, "email change success", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(0,0);
                            finish();
                        }
                    }
                });
    }
    public void changeLanguage(FirebaseUser user, String language){
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String[] languages = getResources().getStringArray(R.array.language);
        editor.remove("language");
        for (String lang : languages){
            if (lang.equals(language)){
                editor.putString("language", language);
                editor.apply();
            }
        }
        Log.d(TAG,"language : "+language);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        MainActivity.foodList.clear();
        loadData(database, MainActivity.foodList, language);
        changeLanguageDB(database, user.getUid(), language);


    }
    public void hideKeyboard(){
        //키보드 숨기기
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_nameInput.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(_emailInput.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            Intent i= new Intent(ProfileChangeActivity.this,ProfileActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        newLanguage = parent.getItemAtPosition(pos).toString();
        Log.d(TAG,"Selected newLanguage : " + newLanguage );
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void loadData(FirebaseDatabase database, final List<FoodData> foods, String language){
        DatabaseReference myRef=database.getReference(language);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                        FoodData f=postSnapshot.getValue(FoodData.class);
                        foods.add(f);
                    }
                    Log.d(TAG,  " 데이터 로드 성공!");
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
    private void changeLanguageDB(FirebaseDatabase firebaseDatabase, String uid, String language){
        DatabaseReference databaseReference = firebaseDatabase.getReference("/user");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(uid + "/language", language);
        databaseReference.updateChildren(childUpdates);
    }
}
