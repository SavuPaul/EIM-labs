package ro.pub.cs.systems.eim.lab03.phonedialer.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ro.pub.cs.systems.eim.lab03.phonedialer.R;
import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;

    private final CallImageButtonClickListener callImageButtonClickListener = new CallImageButtonClickListener();
    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private final HangupImageButtonClickListener hangupImageButtonClickListener = new HangupImageButtonClickListener();
    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private final BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (!phoneNumber.isEmpty()) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private final ContactsImageButtonClickListener contactsImageButtonClickListener = new ContactsImageButtonClickListener();
    private class ContactsImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (!phoneNumber.isEmpty()) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
            } else {
                Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    private final GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        ImageButton callImageButton = findViewById(R.id.call_image_button);
        callImageButton.setOnClickListener(callImageButtonClickListener);
        ImageButton hangupImageButton = findViewById(R.id.hangup_image_button);
        hangupImageButton.setOnClickListener(hangupImageButtonClickListener);
        ImageButton backspaceImageButton = findViewById(R.id.backspace_image_button);
        backspaceImageButton.setOnClickListener(backspaceButtonClickListener);
        for (int index = 0; index < Constants.buttonIds.length; index++) {
            Button genericButton = findViewById(Constants.buttonIds[index]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }
        ImageButton contactsImageButton = findViewById(R.id.contacts_image_button);
        contactsImageButton.setOnClickListener(contactsImageButtonClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.CONTACTS_MANAGER_REQUEST_CODE) {
            Toast.makeText(this, "Activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
