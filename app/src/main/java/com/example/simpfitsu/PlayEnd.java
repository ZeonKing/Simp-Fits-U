package com.example.simpfitsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.simpfitsu.MusicService;
import com.example.simpfitsu.classes.storyManager;

import static com.example.simpfitsu.MainMenu.score;

public class PlayEnd extends AppCompatActivity {
    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_end);

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }

            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

        Button backButton = (Button) findViewById(R.id.back_playend);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView simpImage1 = (ImageView) findViewById(R.id.lowest_end);
        ImageView simpImage2 = (ImageView) findViewById(R.id.low_end);
        ImageView simpImage3 = (ImageView) findViewById(R.id.enough_end);
        ImageView simpImage4 = (ImageView) findViewById(R.id.top_end);


        if (score < 6) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.lowest_simp);
            mp.start();
            simpImage1.animate().alpha(1f).setDuration(3000);
        } else if (score < 11) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.low_simp);
            mp.start();
            simpImage2.animate().alpha(1f).setDuration(3000);
        } else if (score < 16) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.enough_simp);
            mp.start();
            simpImage3.animate().alpha(1f).setDuration(3000);
        } else {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.top_simp);
            mp.start();
            simpImage4.animate().alpha(1f).setDuration(3000);
        }



    }

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);

    }

    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }
}