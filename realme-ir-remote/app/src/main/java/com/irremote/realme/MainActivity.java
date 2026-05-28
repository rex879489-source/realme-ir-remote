package com.irremote.realme;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ConsumerIrManager irManager;
    private TextView tvStatus;
    private boolean irAvailable = false;

    // Repeat IR while button held
    private Handler repeatHandler = new Handler(Looper.getMainLooper());
    private Runnable repeatRunnable;
    private int[] currentCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);

        // Check IR hardware
        irManager = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
        if (irManager != null && irManager.hasIrEmitter()) {
            irAvailable = true;
            tvStatus.setText("IR Ready ✓");
        } else {
            tvStatus.setText("⚠ No IR blaster found");
            Toast.makeText(this, "This device has no IR blaster!", Toast.LENGTH_LONG).show();
        }

        wireButton(R.id.btn_power,      RealmeTVCodes.POWER);
        wireButton(R.id.btn_mute,       RealmeTVCodes.MUTE);
        wireButton(R.id.btn_vol_up,     RealmeTVCodes.VOL_UP);
        wireButton(R.id.btn_vol_down,   RealmeTVCodes.VOL_DOWN);
        wireButton(R.id.btn_ch_up,      RealmeTVCodes.CH_UP);
        wireButton(R.id.btn_ch_down,    RealmeTVCodes.CH_DOWN);
        wireButton(R.id.btn_up,         RealmeTVCodes.UP);
        wireButton(R.id.btn_down,       RealmeTVCodes.DOWN);
        wireButton(R.id.btn_left,       RealmeTVCodes.LEFT);
        wireButton(R.id.btn_right,      RealmeTVCodes.RIGHT);
        wireButton(R.id.btn_ok,         RealmeTVCodes.OK);
        wireButton(R.id.btn_home,       RealmeTVCodes.HOME);
        wireButton(R.id.btn_back,       RealmeTVCodes.BACK);
        wireButton(R.id.btn_menu,       RealmeTVCodes.MENU);
        wireButton(R.id.btn_source,     RealmeTVCodes.SOURCE);
        wireButton(R.id.btn_info,       RealmeTVCodes.INFO);
        wireButton(R.id.btn_0,          RealmeTVCodes.NUM_0);
        wireButton(R.id.btn_1,          RealmeTVCodes.NUM_1);
        wireButton(R.id.btn_2,          RealmeTVCodes.NUM_2);
        wireButton(R.id.btn_3,          RealmeTVCodes.NUM_3);
        wireButton(R.id.btn_4,          RealmeTVCodes.NUM_4);
        wireButton(R.id.btn_5,          RealmeTVCodes.NUM_5);
        wireButton(R.id.btn_6,          RealmeTVCodes.NUM_6);
        wireButton(R.id.btn_7,          RealmeTVCodes.NUM_7);
        wireButton(R.id.btn_8,          RealmeTVCodes.NUM_8);
        wireButton(R.id.btn_9,          RealmeTVCodes.NUM_9);
        wireButton(R.id.btn_red,        RealmeTVCodes.RED);
        wireButton(R.id.btn_green,      RealmeTVCodes.GREEN);
        wireButton(R.id.btn_yellow,     RealmeTVCodes.YELLOW);
        wireButton(R.id.btn_blue,       RealmeTVCodes.BLUE);
        wireButton(R.id.btn_play_pause, RealmeTVCodes.PLAY_PAUSE);
        wireButton(R.id.btn_rew,        RealmeTVCodes.REW);
        wireButton(R.id.btn_ff,         RealmeTVCodes.FF);
    }

    private void wireButton(int btnId, int[] irCode) {
        Button btn = findViewById(btnId);
        if (btn == null) return;

        btn.setOnClickListener(v -> sendIR(irCode));

        // Hold to repeat (useful for volume, navigation)
        btn.setOnLongClickListener(v -> {
            currentCode = irCode;
            startRepeat();
            return true;
        });

        btn.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP ||
                event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
                stopRepeat();
            }
            return false;
        });
    }

    private void sendIR(int[] pattern) {
        if (!irAvailable) {
            tvStatus.setText("⚠ No IR blaster");
            return;
        }
        try {
            irManager.transmit(RealmeTVCodes.CARRIER_FREQUENCY, pattern);
            tvStatus.setText("Sent ✓");
        } catch (Exception e) {
            tvStatus.setText("Error: " + e.getMessage());
        }
    }

    private void startRepeat() {
        repeatRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentCode != null) {
                    sendIR(currentCode);
                    repeatHandler.postDelayed(this, 300);
                }
            }
        };
        repeatHandler.post(repeatRunnable);
    }

    private void stopRepeat() {
        if (repeatRunnable != null) {
            repeatHandler.removeCallbacks(repeatRunnable);
            repeatRunnable = null;
            currentCode = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeat();
    }
}
