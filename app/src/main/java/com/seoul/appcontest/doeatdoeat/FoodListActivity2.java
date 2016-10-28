package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by a on 2016-09-29.
 */
public class FoodListActivity2 extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    public static final String API_KEY = "AIzaSyDK9ZJVkfyycnlZh1zGd2riD2hS0Zxvafk";//사용자가 얻은 API Key을 입력하면 된다.(개발자 콘솔에 얻은 것.)

    //http://youtu.be/<VIDEO_ID>
    String VIDEO_ID = FoodListActivity.menuStr;
    private static final int RQS_ErrorDialog = 1;

    @InjectView(R.id.list_title_2) TextView _title2;
    @InjectView(R.id.explain_title) TextView _explainTitle;
    @InjectView(R.id.explain_explain) TextView _explainExplain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** attaching layout xml **/
        setContentView(R.layout.activity_foodlist2);
        ButterKnife.inject(this);

        _title2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));
        _explainTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));
        _explainExplain.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf"));
        _explainTitle.setText("음식이름");
        if(FoodListActivity.menuNum==R.id.menu_all){
            _title2.setText("All");
        }else if(FoodListActivity.menuNum==R.id.menu_rice){
            _title2.setText("Rice");
        }else if(FoodListActivity.menuNum==R.id.menu_noodle){
            _title2.setText("Noodle");
        }else if(FoodListActivity.menuNum==R.id.menu_soup){
            _title2.setText("Soup");
        }else if(FoodListActivity.menuNum==R.id.menu_meat){
            _title2.setText("Meat");
        }else if(FoodListActivity.menuNum==R.id.menu_fish){
            _title2.setText("Fish");
        }else if(FoodListActivity.menuNum==R.id.menu_drink){
            _title2.setText("Drink");
        }else if(FoodListActivity.menuNum==R.id.menu_dessert){
            _title2.setText("Dessert");
        }else if(FoodListActivity.menuNum==R.id.menu_street){
            _title2.setText("Street");
        }
        /** Initializing YouTube player view **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };
    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
                Intent i= new Intent(FoodListActivity2.this,FoodListActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                finish();
        }
        return true;
    }
}