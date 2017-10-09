package com.example.androidthings.myproject;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;

import com.google.android.things.contrib.driver.adcv2x.Adcv2x;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;
import com.google.android.things.pio.SpiDevice;
import com.google.android.things.pio.UartDevice;

import java.io.IOException;

/**
 * Created by bjoern on 8/14/17.
 * Available GPIO: [GPIO_10, GPIO_128, GPIO_172, GPIO_173, GPIO_174, GPIO_175, GPIO_32, GPIO_33, GPIO_34, GPIO_35, GPIO_37, GPIO_39]
 * Available I2C: [I2C1]
 * Available PWM: [PWM1, PWM2]
 * Available SPI: [SPI3.0, SPI3.1]
 * Available UART: [UART6]
 */

public abstract class SimplePicoPro extends SimpleBoard {
    private static final String TAG = SimpleBoard.class.getSimpleName();

    static final boolean HIGH = true;
    static final boolean LOW = false;

    static PeripheralManagerService service = new PeripheralManagerService();
    static Gpio GPIO_10, GPIO_128, GPIO_172, GPIO_173, GPIO_174, GPIO_175, GPIO_32, GPIO_33, GPIO_34, GPIO_35, GPIO_37, GPIO_39;
    static Pwm PWM1, PWM2;
    static I2cDevice I2C1;
    static SpiDevice SPI3_0,SPI3_1;
    static UartDevice UART6;
    static Adcv2x ADS1015;
    static int A0 = 0;
    static int A1 = 1;
    static int A2 = 2;
    static int A3 = 3;
    private Activity activity;

    public SimplePicoPro() {
        try {
            GPIO_10 = service.openGpio("GPIO_10");
            GPIO_128 = service.openGpio("GPIO_128");
            GPIO_172 = service.openGpio("GPIO_172");
            GPIO_173 = service.openGpio("GPIO_173");
            GPIO_174 = service.openGpio("GPIO_174");
            GPIO_175 = service.openGpio("GPIO_175");
            GPIO_32 = service.openGpio("GPIO_32");
            GPIO_33 = service.openGpio("GPIO_33");
            GPIO_34 = service.openGpio("GPIO_34");
            GPIO_35 = service.openGpio("GPIO_35");
            GPIO_37 = service.openGpio("GPIO_37");
            GPIO_39 = service.openGpio("GPIO_39");

            GPIO_10.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_10.registerGpioCallback(gpioCallback);
            GPIO_128.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_128.registerGpioCallback(gpioCallback);
            GPIO_172.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_172.registerGpioCallback(gpioCallback);
            GPIO_173.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_173.registerGpioCallback(gpioCallback);
            GPIO_174.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_174.registerGpioCallback(gpioCallback);
            GPIO_175.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_175.registerGpioCallback(gpioCallback);
            GPIO_32.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_32.registerGpioCallback(gpioCallback);
            GPIO_33.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_33.registerGpioCallback(gpioCallback);
            GPIO_34.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_34.registerGpioCallback(gpioCallback);
            GPIO_35.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_35.registerGpioCallback(gpioCallback);
            GPIO_37.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_37.registerGpioCallback(gpioCallback);
            GPIO_39.setEdgeTriggerType(Gpio.EDGE_NONE);
            GPIO_39.registerGpioCallback(gpioCallback);



            PWM1 = service.openPwm("PWM1");
            PWM2 = service.openPwm("PWM2");

            //I2C1 = service.openI2cDevice("I2C1",0);

            SPI3_0 = service.openSpiDevice("SPI3.0");
            SPI3_1 = service.openSpiDevice("SPI3.1");

            UART6 = service.openUartDevice("UART6");

        } catch (IOException e) {
            Log.e(TAG, "preSetup", e);
        }
    }


    void uartInit(UartDevice uart, int baudrate) {
        try {
            uart.setBaudrate(baudrate);
        } catch (IOException e) {
            Log.e(TAG,"uartInit",e);
        }

    }
    void analogInit() {
        // This assumes the IDD HAT is on the board
        try {
            ADS1015 = new Adcv2x("I2C1",Adcv2x.I2C_ADDRESS_48);
        } catch (IOException e) {
            Log.e(TAG,"analogInit",e);
        }
    }
    float analogRead(int channel) {
        float result = 0.f;
        if(ADS1015!=null) {
            try {

                result = ADS1015.getResult(channel);

            } catch (IOException e) {
                Log.e(TAG, "analogRead", e);
            }
        } else {
            Log.e("TAG","ADS1015 not configured. Please call analogInit() first. Returning 0.");
        }
        return result;
    }

    public void setActivity(Activity a) {
        activity = a;
    }
    public Activity getActivity() {
        return activity;
    }
    void printCharacterToScreen(char c) {
        if (activity == null) {
            Log.e(TAG,"printChar: activity is null");
            return;
        }

        EditText editText;
        editText = (EditText) activity.findViewById(R.id.editText);

        if(editText != null) {
            editText.getText().append(c);
        } else {
            Log.e(TAG,"printChar: Could not find R.id.editText");
        }
    }

    void printStringToScreen(String s) {
        if (activity == null) {
            Log.e(TAG,"printString: activity is null");
            return;
        }

        EditText editText;
        editText = (EditText) activity.findViewById(R.id.editText);

        if(editText != null) {
            editText.getText().append(s);
        } else {
            Log.e(TAG,"printString: Could not find R.id.editText");
        }
    }

    void clearStringOnScreen() {
        if (activity == null) {
            Log.e(TAG,"clearString: activity is null");
            return;
        }

        EditText editText;
        editText = (EditText) activity.findViewById(R.id.editText);

        if(editText != null) {
            editText.setText("");
        } else {
            Log.e(TAG,"clearString: Could not find R.id.editText");
        }
    }

    public void teardown() {
        try {
            GPIO_10.unregisterGpioCallback(gpioCallback);
            GPIO_10.close();
            GPIO_128.unregisterGpioCallback(gpioCallback);
            GPIO_128.close();
            GPIO_172.unregisterGpioCallback(gpioCallback);
            GPIO_172.close();
            GPIO_173.unregisterGpioCallback(gpioCallback);
            GPIO_173.close();
            GPIO_174.unregisterGpioCallback(gpioCallback);
            GPIO_174.close();
            GPIO_175.unregisterGpioCallback(gpioCallback);
            GPIO_175.close();
            GPIO_32.unregisterGpioCallback(gpioCallback);
            GPIO_32.close();
            GPIO_33.unregisterGpioCallback(gpioCallback);
            GPIO_33.close();
            GPIO_34.unregisterGpioCallback(gpioCallback);
            GPIO_34.close();
            GPIO_35.unregisterGpioCallback(gpioCallback);
            GPIO_35.close();
            GPIO_37.unregisterGpioCallback(gpioCallback);
            GPIO_37.close();
            GPIO_39.unregisterGpioCallback(gpioCallback);
            GPIO_39.close();
            // ...
        } catch (IOException e) {
            Log.e(TAG,"destroy",e);
        }


    }


}

