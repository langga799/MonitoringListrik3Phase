package com.example.monitoringlistrik3phase

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.monitoringlistrik3phase.databinding.ActivityTesBinding
import com.example.monitoringlistrik3phase.service.FirebaseService
import com.example.monitoringlistrik3phase.service.network.NetworkConfig
import com.example.monitoringlistrik3phase.service.model.NotificationData
import com.example.monitoringlistrik3phase.service.model.PushNotification
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTesBinding

    companion object {
        const val TAG = "Atur Pengingat Fragment"
        const val TOPIC = "/topics/Listrik"
    }

    private var token = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        FirebaseMessaging.getInstance().token.addOnSuccessListener {
//            FirebaseService.token = it
//            token = it
//        }

        Firebase.messaging.subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.btnSend.setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = current.format(formatter)

            val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
            val time = current.format(formatTime)
            val dateAndTime = date + time

            val title = "Monitoring Listrik 3 Fasa APP"
            val message = "Listrik Normal dan Tidak Berbahaya"

            sendNotification(
                PushNotification(
                    NotificationData(
                        title,
                        message,
                        dateAndTime
                    ),
                    TOPIC
                )
            )
        }

    }

    private fun sendNotification(notification: PushNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NetworkConfig().getApiService().pushNotification(notification)
                if (response.isSuccessful) {
                 //   Log.d(TAG, "Response: ${Gson().toJson(response)}")

                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }
}