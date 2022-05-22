package com.complete.rapidaide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_emergency_calling.*


class EmergencyCalling : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_calling)

        police.setOnClickListener{
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:100")
        startActivity(callIntent)
        }
        medical.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:101")
            startActivity(callIntent)
        }
        fire.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:102")
            startActivity(callIntent)
        }
    }
}