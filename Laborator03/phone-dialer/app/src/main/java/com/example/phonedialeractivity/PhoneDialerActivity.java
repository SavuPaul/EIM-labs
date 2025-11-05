package com.example.phonedialeractivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhoneDialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        for (int buttonId : buttonsIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new ButtonClickListener());
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button clickedButton = (Button) v;
            String buttonText = clickedButton.getText().toString();

            // Append the button text to the phone number
            String currentPhoneNumber = ((EditText) findViewById(R.id.phone_number_text_view)).getText().toString();
            currentPhoneNumber += buttonText;
            ((EditText) findViewById(R.id.phone_number_text_view)).setText(currentPhoneNumber);

            // Handle the "delete_button" logic
            if (v.getId() == R.id.delete_button) {
                // Remove the last character from the phone number
                currentPhoneNumber = ((EditText) findViewById(R.id.phone_number_text_view)).getText().toString();
                if (!currentPhoneNumber.isEmpty()) {
                    currentPhoneNumber = currentPhoneNumber.substring(0, currentPhoneNumber.length() - 1);
                    ((EditText) findViewById(R.id.phone_number_text_view)).setText(currentPhoneNumber);
                }
            }
        }
    }
}