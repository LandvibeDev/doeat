package com.seoul.appcontest.doeatdoeat;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by a on 2016-09-29.
 */
public class FoodListActivity2 extends Activity{
    @InjectView(R.id.foodview)
    VideoView videoView;
    @InjectView(R.id.btn_start)
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist2);
        ButterKnife.inject(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://videoprac-bd3ea.appspot.com");
        storageRef.child("bingsu.mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView.setVideoURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure");
            }
        });

        MediaController controller = new MediaController(FoodListActivity2.this);
        videoView.setMediaController(controller);

        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(FoodListActivity2.this,
                        "동영상이 준비되었습니다. \n'시작' 버튼을 누르세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void StartButton(View v) {
        playVideo();
    }

    private void playVideo() {
        //비디오를 처음부터 재생할 때 0으로 시작(파라메터 sec)
        videoView.seekTo(0);
        videoView.start();
    }
}
