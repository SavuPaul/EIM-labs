package ro.pub.cs.systems.eim.lab03.phonedialer.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import ro.pub.cs.systems.eim.lab03.phonedialer.R
import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants

class PhoneDialerActivity : AppCompatActivity() {
    private var phoneNumberEditText: EditText? = null
    private var callImageButton: ImageButton? = null
    private var hangupImageButton: ImageButton? = null
    private var backspaceImageButton: ImageButton? = null
    private var genericButton: Button? = null

    private val callImageButtonClickListener = CallImageButtonClickListener()

    private inner class CallImageButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            if (ContextCompat.checkSelfPermission(
                    this@PhoneDialerActivity,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@PhoneDialerActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    Constants.PERMISSION_REQUEST_CALL_PHONE
                )
            } else {
                val intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:" + phoneNumberEditText!!.text.toString()))
                startActivity(intent)
            }
        }
    }

    private val hangupImageButtonClickListener = HangupImageButtonClickListener()

    private inner class HangupImageButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            finish()
        }
    }

    private val backspaceButtonClickListener = BackspaceButtonClickListener()

    private inner class BackspaceButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            var phoneNumber = phoneNumberEditText!!.text.toString()
            if (phoneNumber.length > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length - 1)
                phoneNumberEditText!!.setText(phoneNumber)
            }
        }
    }

    private val genericButtonClickListener = GenericButtonClickListener()

    private inner class GenericButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            phoneNumberEditText!!.setText(phoneNumberEditText!!.text.toString() + (view as Button).text.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dialer)

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        phoneNumberEditText = findViewById<View>(R.id.phone_number_edit_text) as EditText
        callImageButton = findViewById<View>(R.id.call_image_button) as ImageButton
        callImageButton!!.setOnClickListener(callImageButtonClickListener)
        hangupImageButton = findViewById<View>(R.id.hangup_image_button) as ImageButton
        hangupImageButton!!.setOnClickListener(hangupImageButtonClickListener)
        backspaceImageButton = findViewById<View>(R.id.backspace_image_button) as ImageButton
        backspaceImageButton!!.setOnClickListener(backspaceButtonClickListener)
        for (index in 0 until Constants.buttonIds.size) {
            genericButton = findViewById<View>(Constants.buttonIds[index]) as Button
            genericButton!!.setOnClickListener(genericButtonClickListener)
        }
    }
}
