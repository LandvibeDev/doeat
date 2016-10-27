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

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by a on 2016-09-21.
 */
public class FoodListActivity extends Activity {

    @InjectView(R.id.btn_top) Button _homeButton;
    @InjectView(R.id.btn_tradi) Button _matchtButton;
    @InjectView(R.id.btn_list) Button _listButton;
    @InjectView(R.id.btn_favorite) Button _favoriteButton;
    @InjectView(R.id.btn_profile) Button _profileButton;

    @InjectView(R.id.btn_back) Button _btnBack;
    @InjectView(R.id.list_food) ListView _listView;
    @InjectView(R.id.list_title) TextView _title;

    public static String menuStr;
    public static int menuNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        ButterKnife.inject(this);
        ListViewAdapter adapter=new ListViewAdapter();
        _listView.setAdapter(adapter);
        _title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));

        // 메인에서 선택한 메뉴에 따라 보이는 List 설정
        if(menuNum==R.id.menu_rice){
            _title.setText("Rice");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bibimbap),"비빔밥","밥에 나물, 고기, 달걀을 올려 고추장(매운 양념)에 비벼먹는 한국의 전통음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sungnung),"숭늉","밥솥 바닥에 열이 많이 가해져 눌러 붙은 밥(‘누룽지’라고 한다.)에 물을 붓고 끓여 만든 음식");//사진
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squidrice),"오징어덮밥","살짝 데친 오징어에 매운 양념과 콩나물, 야채 등을 넣고 볶아서 밥과 함께 먹는 음식");
        }else if(menuNum==R.id.menu_noodle){
            _title.setText("Noodle");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nangmyun),"냉면","메밀가루로 만든 얇은 면에 차가운 육수를 부어 먹는 한국의 전통음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzazangmyun),"짜장면","야채와 고기를 춘장으로 볶은소스를 면에 부어 비벼 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzambbong),"짬뽕","여러가지 채소와 해산물을 볶아 육수를 부어 만든 국물에 면을 넣어 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.lamyun),"라면","기름에 미리 튀긴 국수를 스프와 함께 끓는 물에 넣어서 요리하는 레토르트 식품");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.hotbbokemmyun),"뜨거운 볶음면","기름에 미리 튀긴 국수를 끓는 물에 익힌 후 소스에 비벼먹는 레토르트 음식");
        }else if(menuNum==R.id.menu_soup){
            _title.setText("Soup");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundaegook),"순대국","소의 다리뼈(사골)를 넣고 오랜 시간 푹 고아 만든 육수에 순대를 넣어 요리한 국물로 밥과 함께 먹는음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gamzatang),"감자탕","돼지 등뼈로 육수를 낸 후 각종 야채를 넣고 매운 양념과 후추로 간을 한 한국의 대표적인 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.mandugook),"만두국","고기와 기타 재료를 넣은 만두를 쇠고기 국물에 넣고 끓인 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.budaezzigae),"부대찌개","뜨거운 육수에 라면, 햄, 떡, 야채를 넣고 매운 양념과 함께 끓인 음식");
        }else if(menuNum==R.id.menu_meat){
            _title.setText("Meat");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gopchang),"곱창볶음","곱창(돼지나 소의 창자)을 각종 야채와 매운 소스를 철판 위에서 볶아 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakgalbi),"닭갈비","토막낸 닭을 매운 양념에 재웠다가 고구마, 양배추, 양파, 떡 등의 재료와 함께 철판에 볶아 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.galbizzim),"갈비찜","소의 갈비(늑골 부위)에 간장 소스로 양념한 후 감자, 당근 등의 야채와 함께 쪄서 조리한 한국의 전통음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyaetang),"삼계탕","손질한 닭의 배 안에 찹쌀, 인삼, 대추 등을 넣고 실로 꿰매어 냄비에 푹 익힌 여름철 한국의 보양식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.yukhwae),"육회","익히지 않은 소고기를 얇게 썰어서 양념이나 얇게 썬 배를 곁들여 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dakbal),"닭발","닭발을 매운 양념으로 조리한 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.samgyupsal),"삼겹살","삼겹살입니다");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.tangsuyuk),"탕수육","튀긴 돼지고기에 탕수 소스를 곁들여 먹는 음식");
        }else if(menuNum==R.id.menu_fish){
            _title.setText("Fish");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.nakji),"산낙지","살아 있는 낙지를 먹기 좋게 자르거나 젓가락에 감아 소금과 참기름을 섞은 양념에 찍어 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.zzuggumi),"주꾸미","주꾸미(문어의 일종)를 양파, 파 등 각종 채소와 함께 매운 양념장에 볶아먹는 철판 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ganzanggezang),"간장게장","익히지 않은 게를 간장에 오랜 시간 숙성시킨 것으로 짭쪼름한 맛이 일품인 음식");
        }else if(menuNum==R.id.menu_drink){
            _title.setText("Drink");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.somac),"소맥","한국의 술인 소주와 맥주를 적절한 비율로 섞은 술 ");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.linggelzu),"링겔주","한국의 술인 소주와 매화수 병의 입구를 맞대어 서로 섞이도록 한 술");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gozingamraezu),"고진감래","한국의 술인 소주와 맥주, 콜라를 정해진 순서에 따라 제조한 술로 쓴맛과 단맛이 절묘하게 잘 어울리는 술");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.meronazu),"메로나주","소주와 사이다와 메로나 아이스크림을 섞어 달콤하면서도 톡쏘는 맛을 내는 술");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.makgullli),"막걸리","찹쌀•보리•밀가루 등을 전통적인 방식으로 발효시켜 제조한 한국의 전통 술");
        }else if(menuNum==R.id.menu_dessert){
            _title.setText("Dessert");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bingsu),"빙수","얼음 혹은 우유얼음을 잘게 갈아서 그 위에 얹은 팥이나 과일과 함께 섞어 먹는 디저트");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.misutgaru),"미숫가루","현미, 보리, 콩, 율무, 찹쌀 등 여러 가지 몸에 좋은 곡물 가루를 우유나 물에 타먹는 음료");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.icehongsi),"아이스홍시","홍시(잘 익은 감을 일컫는다)를 얼려 샤베트처럼 퍼먹는 디저트");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sikhae),"식혜","쌀밥을 엿기름에 삭혀서 만든 음료로 특유의 단맛이 일품인 한국의 전통음료 ");
        }else if(menuNum==R.id.menu_street){
            _title.setText("Street");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ddukbokgi),"떡볶이","쌀 혹은 밀가루로 만든 떡에 오뎅과 파를 넣고 고추장이나 간장 소스로 양념을 한 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.sundae),"순대","돼지 곱창에 당면을 담고 선지로 맛과 색깔을 내어 수증기에 쪄낸 음식으로 떡볶이와 잘 어울린다.");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.fry),"튀김","떡볶이, 순대와 함께 길거리에서 많이 판매하는 음식으로 각종 야채, 해산물을 밀가루를 묻혀 기름에 튀긴 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gimbap),"김밥","식초로 간을 한 밥에 시금치, 오이, 당근 등의 야채와 햄을 넣고 김으로 만 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupramyun),"컵라면","편의점에서 판매되는 일회용 용기에 담겨있는 라면");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cupbbokemmyun),"컵볶음면","편의점에서 판매되는 일회용 용기에 담겨있는 볶음라면");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ogamzacheese),"오감자 치즈후라이","한국의 감자칩에 치즈를 뿌려 만든 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.convenient_store_food),"삼각김밥","삼각형 모양의 주먹밥 속에 참지, 불고기 등 한가지 재료를 넣고 김으로 감싼 레토르트 식품");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.markjungsik),"마크정식","인스턴트 떡볶이에 스파게티, 후랑크소세지를 넣고 모짜렐라 치즈를 얹어 먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.gimbap),"치즈 불닭볶음밥","매운 떡볶이에 밥과 스트링 치즈를 함께 넣어먹는 음식");
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.apocato),"아포카토","아이스크림에 진한 에스프레소를 얹은 디저트");
        }else{
            //menuNumm == R.id.menu_all
            _title.setText("All");
        }



        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(menuNum==R.id.menu_rice){
                    if(i==0){}
                    else if(i==1){}
                    else if(i==2){}
                }else if(menuNum==R.id.menu_noodle){
                    if(i==0){}
                    else if(i==1){menuStr="vTkMEu4s674";}
                    else if(i==2){menuStr="vTkMEu4s674";}
                    else if(i==3){}
                    else if(i==4){}
                }else if(menuNum==R.id.menu_meat){
                    if(i==0){}
                    else if(i==1){menuStr="tsjooxxZxFc";}
                    else if(i==2){}
                    else if(i==3){}
                    else if(i==4){}
                    else if(i==5){}
                    else if(i==6){menuStr="e3J24CgVKYM";}
                    else if(i==7){}
                }else if(menuNum==R.id.menu_fish){
                    if(i==0){}
                    else if(i==1){menuStr="GhwPqdGDqIU";}
                    else if(i==2){}
                }else if(menuNum==R.id.menu_drink){
                    if(i==0){}
                    else if(i==1){}
                    else if(i==2){}
                    else if(i==3){}
                    else if(i==4){}
                }else if(menuNum==R.id.menu_dessert){
                    if(i==0){menuStr="2G-3r0OO4UE";}
                    else if(i==1){}
                    else if(i==2){}
                    else if(i==3){}
                }else if(menuNum==R.id.menu_street){
                    if(i==0){menuStr="xWWtNryEGgs";}
                    else if(i==1){}
                    else if(i==2){}
                    else if(i==3){}
                    else if(i==4){}
                    else if(i==5){}
                    else if(i==6){}
                    else if(i==7){}
                    else if(i==8){}
                    else if(i==9){}
                }else if(menuNum==R.id.menu_soup){
                    if(i==0){menuStr="krewH_3efoA";}
                    else if(i==1) {menuStr = "3IA172p8rI8";}
                    else if(i==2){}
                    else if(i==3){menuStr="p44BDmajNmI";}
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
