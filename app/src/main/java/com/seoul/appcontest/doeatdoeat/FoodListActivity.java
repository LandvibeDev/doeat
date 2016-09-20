package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;



/**
 * Created by a on 2016-09-21.
 */
public class FoodListActivity extends Activity {
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        ListView listview;
        ListViewAdapter adapter;

        adapter= new ListViewAdapter();

        listview = (ListView) findViewById(R.id.list_food);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nureung),"누릉밥","누릉밥입니다");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bibimbap),"비빔밥","비빔밥입니다");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squidrice),"오징어덮밥","오징어덮밥입니다");

        btnBack=(Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });

    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            goToMain();
        }
        return true;
    }
    public void goToMain(){
        Intent i= new Intent(FoodListActivity.this,MainActivity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
        finish();
    }

}
