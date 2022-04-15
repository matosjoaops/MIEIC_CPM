package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    val helloView: TextView by lazy { findViewById(R.id.hello)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitInstance
        val resp = retrofit.server.getHello()

        resp.enqueue(object : Callback<Msg?> {
            override fun onResponse(call: Call<Msg?>, response: Response<Msg?>) {
                val respBody = response.body()

                val helloMsg = respBody?.msg
                helloView.text = helloMsg
            }

            override fun onFailure(call: Call<Msg?>, t: Throwable) {
                helloView.text = t.message
            }
        })
    }
}