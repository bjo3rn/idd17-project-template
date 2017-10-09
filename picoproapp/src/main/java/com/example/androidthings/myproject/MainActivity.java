/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidthings.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import com.google.android.things.pio.PeripheralManagerService;

/**
 * The main Android Things activity.
 * Students should change which application class is loaded below, but otherwise leave this unchanged.
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /** CHANGE THE RIGHT-HAND SIDE OF THIS LINE TO THE NAME OF YOUR APPLICATION CLASS **/
    private SimplePicoPro myBoardApp = new HttpTestApp();

    /** DON'T CHANGE THE CODE BELOW - PUT YOUR CODE INTO YOUR APPLICATION CLASS **/
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "java.lang.ObjectonCreate");

        /** SCREEN INITIALIZATION */
        setContentView(R.layout.textlayout);
        myBoardApp.setActivity(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Log.d(TAG, "Display height in pixels: "+ dm.heightPixels);
        Log.d(TAG, "Display width in pixels: "+ dm.widthPixels);
        Log.d(TAG, "Display density in dpi: "+ dm.densityDpi);


        PeripheralManagerService service = new PeripheralManagerService();
        Log.d(TAG, "Available GPIO: " + service.getGpioList());
        Log.d(TAG, "Available I2C: " + service.getI2cBusList());
        Log.d(TAG, "Available PWM: " + service.getPwmList());
        Log.d(TAG, "Available SPI: " + service.getSpiBusList());
        Log.d(TAG, "Available UART: " + service.getUartDeviceList());

        myBoardApp.setup();
        handler.post(loopRunnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBoardApp.teardown();
        try {
            handler.removeCallbacks(loopRunnable);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "onDestroy");

    }

    Runnable loopRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                myBoardApp.loop();
                handler.post(this);
            } catch(Exception e) {
                Log.e(TAG,e.getMessage());
            }
        }

    };
}
