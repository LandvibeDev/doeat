package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by a on 2016-09-21.
 */
public class FoodListActivity extends Activity {
    @InjectView(R.id.btn_back) Button _btnBack;
    @InjectView(R.id.list_food) ListView _listView;
    @InjectView(R.id.list_title) TextView _title;

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



    public static String menuStr;
    public static int menuNum;
    public static String menuStr2;
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

        ListViewAdapter adapter=new ListViewAdapter();
        _listView.setAdapter(adapter);
        _title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));


        // 메인에서 선택한 메뉴에 따라 보이는 List 설정
        if(menuNum==R.id.menu_rice){
            _title.setText("Rice");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bibimbap),"비빔밥",getString(R.string.bibimbap));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sungnung),"숭늉",getString(R.string.sungnung));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squidrice),"오징어덮밥",getString(R.string.squidrice));
        }else if(menuNum==R.id.menu_noodle){
            _title.setText("Noodle");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nangmyun),"냉면",getString(R.string.nangmyun));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzazangmyun),"짜장면",getString(R.string.zzazangmyun));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzambbong),"짬뽕",getString(R.string.zzambbong));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.lamyun),"라면",getString(R.string.lamyun));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.hotbbokemmyun),"뜨거운 볶음면",getString(R.string.hotbbokemmyun));
        }else if(menuNum==R.id.menu_soup){
            _title.setText("Soup");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundaegook),"순대국",getString(R.string.sundaegook));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gamzatang),"감자탕",getString(R.string.gamzatang));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.mandugook),"만두국",getString(R.string.mandugook));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.budaezzigae),"부대찌개",getString(R.string.budaezzigae));
        }else if(menuNum==R.id.menu_meat){
            _title.setText("Meat");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gopchang),"곱창볶음",getString(R.string.gopchang));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakgalbi),"닭갈비",getString(R.string.dakgalbi));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.galbizzim),"갈비찜",getString(R.string.galbizzim));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyaetang),"삼계탕",getString(R.string.samgyaetang));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.yukhwae),"육회",getString(R.string.yukhwae));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakbal),"닭발",getString(R.string.dakbal));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyupsal),"삼겹살",getString(R.string.samgyupsal));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.tangsuyuk),"탕수육",getString(R.string.tangsuyuk));
        }else if(menuNum==R.id.menu_fish){
            _title.setText("Fish");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nakji),"산낙지",getString(R.string.sannakji));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzuggumi),"주꾸미",getString(R.string.zzuggumi));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ganzanggezang),"간장게장",getString(R.string.ganzanggezang));
        }else if(menuNum==R.id.menu_drink){
            _title.setText("Drink");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.somac),"소맥",getString(R.string.somac));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.linggelzu),"링겔주",getString(R.string.linggelzu));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gozingamraezu),"고진감래",getString(R.string.gozingamrae));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.meronazu),"메로나주",getString(R.string.meronazu));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.makgullli),"막걸리",getString(R.string.makgulli));
        }else if(menuNum==R.id.menu_dessert){
            _title.setText("Dessert");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bingsu),"빙수",getString(R.string.bingsu));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.misutgaru),"미숫가루",getString(R.string.misutgaru));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.icehongsi),"아이스홍시",getString(R.string.icehongsi));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sikhae),"식혜",getString(R.string.sikhae));
        }else if(menuNum==R.id.menu_street){
            _title.setText("Street");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ddukbokgi),"떡볶이",getString(R.string.ddukbbokgi));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundae),"순대",getString(R.string.sundae));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.fry),"튀김",getString(R.string.fry));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gimbap),"김밥",getString(R.string.gimbap));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupramyun),"컵라면",getString(R.string.cupramyun));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupbbokemmyun),"컵볶음면",getString(R.string.cupbbokemmyun));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ogamzacheese),"오감자 치즈후라이",getString(R.string.ogamza));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.convenient_store_food),"삼각김밥",getString(R.string.samgakgimbap));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.markjungsik),"마크정식",getString(R.string.markjungsik));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cheesebuldakbab),"치즈 불닭볶음밥",getString(R.string.cheesebuldakbap));
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.apocato),"아포카토",getString(R.string.apocato));
        }else{
            //menuNumm == R.id.menu_all
            _title.setText("All");
        }

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(menuNum==R.id.menu_rice){
                    if(i==0){menuStr2="비빔밥";}
                    else if(i==1){menuStr2="숭늉";}
                    else if(i==2){menuStr2="오징어덮밥";}
                }else if(menuNum==R.id.menu_noodle){
                    if(i==0){menuStr2="냉면";}
                    else if(i==1){menuStr="vTkMEu4s674";menuStr2="짜장면";}
                    else if(i==2){menuStr="vTkMEu4s674";menuStr2="짬뽕";}
                    else if(i==3){menuStr2="라면";}
                    else if(i==4){menuStr2="뜨거운 볶음면";}
                }else if(menuNum==R.id.menu_meat){
                    if(i==0){menuStr2="곱창볶음";}
                    else if(i==1){menuStr="tsjooxxZxFc";menuStr2="닭갈비";}
                    else if(i==2){menuStr2="갈비찜";}
                    else if(i==3){menuStr2="삼계탕";}
                    else if(i==4){menuStr2="육회";}
                    else if(i==5){menuStr2="닭발";}
                    else if(i==6){menuStr="e3J24CgVKYM";menuStr2="삼겹살";}
                    else if(i==7){menuStr2="탕수육";}
                }else if(menuNum==R.id.menu_fish){
                    if(i==0){menuStr2="산낙지";}
                    else if(i==1){menuStr="GhwPqdGDqIU";menuStr2="주꾸미";}
                    else if(i==2){menuStr2="간장게장";}
                }else if(menuNum==R.id.menu_drink){
                    if(i==0){menuStr2="소맥";}
                    else if(i==1){menuStr2="링겔주";}
                    else if(i==2){menuStr2="고진감래";}
                    else if(i==3){menuStr2="메로나주";}
                    else if(i==4){menuStr2="막걸리";}
                }else if(menuNum==R.id.menu_dessert){
                    if(i==0){menuStr="2G-3r0OO4UE";menuStr2="빙수";}
                    else if(i==1){menuStr2="미숫가루";}
                    else if(i==2){menuStr2="아이스홍시";}
                    else if(i==3){menuStr2="식혜";}
                }else if(menuNum==R.id.menu_street){
                    if(i==0){menuStr="xWWtNryEGgs";}
                    else if(i==1){menuStr2="떡볶이";}
                    else if(i==2){menuStr2="순대";}
                    else if(i==3){menuStr2="튀김";}
                    else if(i==4){menuStr2="김밥";}
                    else if(i==5){menuStr2="컵라면";}
                    else if(i==6){menuStr2="컵볶음면";}
                    else if(i==7){menuStr2="오감자 치즈후라이";}
                    else if(i==8){menuStr2="삼각김밥";}
                    else if(i==9){menuStr2="마크정식";}
                }else if(menuNum==R.id.menu_soup){
                    if(i==0){menuStr="krewH_3efoA";menuStr2="순대국";}
                    else if(i==1) {menuStr = "3IA172p8rI8";menuStr2="감자탕";}
                    else if(i==2){menuStr2="만두국";}
                    else if(i==3){menuStr="p44BDmajNmI";menuStr2="부대찌개";}
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

}
