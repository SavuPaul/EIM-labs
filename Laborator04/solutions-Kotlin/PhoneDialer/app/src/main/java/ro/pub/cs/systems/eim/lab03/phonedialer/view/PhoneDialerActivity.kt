package ro.pub.cs.systems.eim.lab03.phonedialer.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ro.pub.cs.systems.eim.lab03.phonedialer.R
import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants
import androidx.core.net.toUri

class PhoneDialerActivity : AppCompatActivity() {

    private lateinit var phoneNumberEditText: EditText

    private val callImageButtonClickListener = View.OnClickListener {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                Constants.PERMISSION_REQUEST_CALL_PHONE
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = "tel:${phoneNumberEditText.text}".toUri()
            }
            startActivity(intent)
        }
    }

    private val hangupImageButtonClickListener = View.OnClickListener {
        finish()
    }

    private val backspaceButtonClickListener = View.OnClickListener {
        val phoneNumber = phoneNumberEditText.text.toString()
        if (phoneNumber.isNotEmpty()) {
            phoneNumberEditText.setText(phoneNumber.dropLast(1))
        }
    }

    private val contactsImageButtonClickListener = View.OnClickListener {
        val phoneNumber = phoneNumberEditText.text.toString()
        if (phoneNumber.isNotEmpty()) {
            val intent = Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity").apply {
                putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber)
            }
            startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE)
        } else {
            Toast.makeText(applicationContext, getString(R.string.phone_error), Toast.LENGTH_LONG).show()
        }
    }

    private val genericButtonClickListener = View.OnClickListener { view ->
        val button = view as? Button
        if (button != null) {
            phoneNumberEditText.setText(phoneNumberEditText.text.toString() + button.text.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dialer)

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text)
        findViewById<ImageButton>(R.id.call_image_button).setOnClickListener(callImageButtonClickListener)
        findViewById<ImageButton>(R.id.hangup_image_button).setOnClickListener(hangupImageButtonClickListener)
        findViewById<ImageButton>(R.id.backspace_image_button).setOnClickListener(backspaceButtonClickListener)

        for (buttonId in Constants.buttonIds) {
            findViewById<Button>(buttonId).setOnClickListener(genericButtonClickListener)
        }

        findViewById<ImageButton>(R.id.contacts_image_button).setOnClickListener(contactsImageButtonClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == Constants.CONTACTS_MANAGER_REQUEST_CODE) {
            Toast.makeText(this, "Activity returned with result $resultCode", Toast.LENGTH_LONG).show()
        }
    }
}
