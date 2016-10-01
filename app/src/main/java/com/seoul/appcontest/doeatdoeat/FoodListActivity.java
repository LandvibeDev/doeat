package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by a on 2016-09-21.
 */
public class FoodListActivity extends Activity {

    @InjectView(R.id.btn_back)
    Button _btnBack;
    @InjectView(R.id.list_food)
    ListView _listView;
    @InjectView(R.id.list_title)
    TextView _title;

    public static int menuNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        ButterKnife.inject(this);

        ListViewAdapter adapter=new ListViewAdapter();
        _listView.setAdapter(adapter);
        _title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));

        if(menuNum==R.id.menu_rice){
            _title.setText("Rice");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nureung),"누릉밥","누릉밥입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bibimbap),"비빔밥","비빔밥입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squidrice),"오징어덮밥","오징어덮밥입니다");
        }else if(menuNum==R.id.menu_meat){
            _title.setText("Meat");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyupsal),"삼겹살","삼겹살입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gopchang),"곱창","곱창입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakgalbi),"닭갈비","닭갈비입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.yukhwae),"육회","육회입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakbal),"닭발","닭발입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.galbizzim),"갈비찜","갈비찜입니다");
        }

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
    public void goToNext(){
        Intent i=new Intent(FoodListActivity.this,FoodListActivity2.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
        finish();
    }

}
