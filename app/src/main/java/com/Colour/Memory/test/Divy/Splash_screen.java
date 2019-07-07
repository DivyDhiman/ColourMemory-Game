package com.Colour.Memory.test.Divy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_screen extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        context = Splash_screen.this;

        splashLoad();
    }

    //load splash screen
    private void splashLoad() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        }, 1800);
    }
}
