package com.example.simpfitsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.simpfitsu.MainMenu.current;
import static com.example.simpfitsu.MainMenu.manager;
import static com.example.simpfitsu.MainMenu.score;


public class Play extends AppCompatActivity {
    String[] option1;
    String[] option2;
    String[] option3;
    String[] option4;
    String[] option5;
    private ImageView imgView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;


    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        option1 = getResources().getStringArray(R.array.optionstory1);
        option2 = getResources().getStringArray(R.array.optionstory2);
        option3 = getResources().getStringArray(R.array.optionstory3);
        option4 = getResources().getStringArray(R.array.optionstory4);
        option5 = getResources().getStringArray(R.array.optionstory5);
        setContentView(R.layout.activity_play);
        imgView = findViewById(R.id.storyImg);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        imgView.setImageResource(0);
        setButton();
        buttonONclick();

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    public void setButton(){
        if(current=="story1")
        {
            imgView.setImageResource(R.drawable.story1);
            btn1.setText(option1[0]);
            btn2.setText(option1[1]);
            btn3.setText(option1[2]);
            btn4.setText(option1[3]);
        }
        else if(current=="story2")
        {
            imgView.setImageResource(R.drawable.story2);
            btn1.setText(option2[0]);
            btn2.setText(option2[1]);
            btn3.setText(option2[2]);
            btn4.setText(option2[3]);
        }
        else if(current=="story3")
        {
            imgView.setImageResource(R.drawable.story3);
            btn1.setText(option3[0]);
            btn2.setText(option3[1]);
            btn3.setText(option3[2]);
            btn4.setText(option3[3]);
        }
        else if(current=="story4")
        {
            imgView.setImageResource(R.drawable.story4);
            btn1.setText(option4[0]);
            btn2.setText(option4[1]);
            btn3.setText(option4[2]);
            btn4.setText(option4[3]);
        }
        else if(current=="story5")
        {
            imgView.setImageResource(R.drawable.story5);
            btn1.setText(option5[0]);
            btn2.setText(option5[1]);
            btn3.setText(option5[2]);
            btn4.setText(option5[3]);
        }
    }
    public void newStory(){
        if(manager.isEmpty()==false){
            manager.draw();
            manager.discard();
            setButton();
        }
    }
    public void buttonONclick(){
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            score++;
            newStory();
            }
    });
        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                score+=2;
                newStory();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                score+=3;
                newStory();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                score+=4;
                newStory();
            }
        });

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
        music.setClass(this,MusicService.class);
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
