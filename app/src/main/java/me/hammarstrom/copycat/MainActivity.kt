package me.hammarstrom.copycat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.hammarstrom.copycat.models.Envelope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            refreshRequest()
        }
    }

    private fun refreshRequest() {
        NewsApi.getInstance(this)
                .getTopHeadlines()
                .enqueue(object : Callback<Envelope> {
                    override fun onFailure(call: Call<Envelope>?, t: Throwable?) {
                        Log.d(TAG, "onFailure(): ${t?.message}")
                    }

                    override fun onResponse(call: Call<Envelope>?, response: Response<Envelope>?) {
                        Log.d(TAG, "RESPONSE: ${response?.code()}")

                        response?.let {
                            if (it.isSuccessful) {
                                Log.d(TAG, "SUCCESS: ${it.body()}")
                            }
                        }
                    }

                })
    }
}
