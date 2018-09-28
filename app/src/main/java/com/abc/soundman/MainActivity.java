package com.abc.soundman;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AudioManager audioManager;
    private ArrayList<SeekBarDef> seekBarDefs;
    private Button btnNormal, btnSilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        seekBarDefs = new ArrayList<>();

        seekBarDefs.add(new SeekBarDef(R.id.seekBarRing,AudioManager.STREAM_RING));
        seekBarDefs.add(new SeekBarDef(R.id.seekBarMusic,AudioManager.STREAM_MUSIC));
        seekBarDefs.add(new SeekBarDef(R.id.seekBarNotifications,AudioManager.STREAM_NOTIFICATION));
        seekBarDefs.add(new SeekBarDef(R.id.seekBarSystem,AudioManager.STREAM_SYSTEM));
        seekBarDefs.add(new SeekBarDef(R.id.seekBarAlarm,AudioManager.STREAM_ALARM));

        for (int i = 0; i < seekBarDefs.size(); i++) {
            seekBarDefs.get(i).seekBar = findViewById(seekBarDefs.get(i).id);
            initBar(seekBarDefs.get(i).seekBar, seekBarDefs.get(i).mode);
        }

        btnNormal = findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(this);

        btnSilent = findViewById(R.id.btnSilent);
        btnSilent.setOnClickListener(this);
    }

    private void initBar (SeekBar bar, final int stream) {

        bar.setMax(audioManager.getStreamMaxVolume(stream));
        bar.setProgress(audioManager.getStreamVolume(stream));

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(stream, progress, AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        int volume = 0;

        for (int i = 0; i < seekBarDefs.size(); i++) {
            SeekBarDef seekBarDef = seekBarDefs.get(i);

            switch (v.getId()) {
                case R.id.btnNormal:
                    volume = audioManager.getStreamMaxVolume(seekBarDef.mode);
                    break;
                case R.id.btnSilent:
                    volume = audioManager.getStreamMinVolume(seekBarDef.mode);
                    break;
            }

            seekBarDef.seekBar.setProgress(volume);
        }
    }
}
