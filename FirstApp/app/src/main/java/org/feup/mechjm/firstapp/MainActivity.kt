package org.feup.mechjm.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val EXTRA_MESSAGE = "org.feup.mechjm.firstapp.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn_send)
        button.setOnClickListener{_ ->
            val message = findViewById<EditText>(R.id.edt_message).text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, message)
            startActivity(intent)
        }
    }
}