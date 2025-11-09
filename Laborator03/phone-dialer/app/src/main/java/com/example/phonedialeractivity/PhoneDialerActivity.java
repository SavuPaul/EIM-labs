package com.example.phonedialeractivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhoneDialerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView accelerationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_dialer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int[] buttonsIds = {
                R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6,
                R.id.button_7, R.id.button_8, R.id.button_9,
                R.id.button_star, R.id.button_0, R.id.button_hashtag
        };

        int[] imageButtonIds = {
                R.id.delete_button, R.id.button_call, R.id.button_hangup
        };

        for (int buttonId : buttonsIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new ButtonClickListener());
        }

        for (int imageButtonId: imageButtonIds) {
            ImageButton button = findViewById(imageButtonId);
            button.setOnClickListener(new ButtonClickListener());
        }

        accelerationTextView = findViewById(R.id.acceleration_text_view);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.delete_button) {
                Log.d("ButtonClickListener", "Delete button clicked");
                // Remove the last character from the phone number
                String currentPhoneNumber = ((EditText) findViewById(R.id.phone_number_text_view)).getText().toString();
                if (!currentPhoneNumber.isEmpty()) {
                    currentPhoneNumber = currentPhoneNumber.substring(0, currentPhoneNumber.length() - 1);
                    ((EditText) findViewById(R.id.phone_number_text_view)).setText(currentPhoneNumber);
                }
            } else if (v.getId() == R.id.button_hangup) {
                finish();
            } else if (v.getId() == R.id.button_call) {
                EditText phoneNumberEditText = findViewById(R.id.phone_number_text_view);

                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                    startActivity(intent);
                }
            } else {
                Button clickedButton = (Button) v;
                String buttonText = clickedButton.getText().toString();

                // Append the button text to the phone number
                String currentPhoneNumber = ((EditText) findViewById(R.id.phone_number_text_view)).getText().toString();
                currentPhoneNumber += buttonText;
                ((EditText) findViewById(R.id.phone_number_text_view)).setText(currentPhoneNumber);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];

            accelerationTextView.setText(String.format("Acceleratie Ox: %.2f", x));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}