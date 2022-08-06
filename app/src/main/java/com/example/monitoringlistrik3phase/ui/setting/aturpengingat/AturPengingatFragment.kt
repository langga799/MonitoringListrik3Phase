package com.example.monitoringlistrik3phase.ui.setting.aturpengingat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.databinding.FragmentAturPengingatBinding
import com.example.monitoringlistrik3phase.helper.SharedPreference
import com.example.monitoringlistrik3phase.service.FirebaseService
import com.example.monitoringlistrik3phase.service.model.NotificationData
import com.example.monitoringlistrik3phase.service.model.PushNotification
import com.example.monitoringlistrik3phase.service.network.NetworkConfig
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AturPengingatFragment : Fragment() {

    private var _binding: FragmentAturPengingatBinding? = null
    private val binding get() = _binding


    companion object {
        private const val TAG = "Atur Pengingat Fragment"
        private const val TOPIC = "/topics/Listrik"
    }


    private lateinit var preference: SharedPreference
    private var token = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAturPengingatBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBackToSetting?.setOnClickListener {
            findNavController().navigate(R.id.action_aturPengingatFragment_to_navigation_setting)
        }

        preference = SharedPreference(requireActivity())

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            preference.saveTokenFirebase(it)
            Log.d("TOKEN-FCM", it)
        }


        when (preference.getStateSubscribe()){
            true -> {
                binding?.switchAlarm?.isChecked = true
            }
            false -> {
                binding?.switchAlarm?.isChecked = false
            }
        }

        binding?.switchAlarm?.setOnCheckedChangeListener { _, condition ->
            when (condition) {
                true -> {
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                    preference.saveStateSubscribe(true)
                }
                false -> {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC)
                    preference.saveStateSubscribe(false)
                }
            }
        }


        // Send Notification
//        binding?.btnSend?.setOnClickListener {
//            val current = LocalDateTime.now()
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            val date = current.format(formatter)
//
//            val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
//            val time = current.format(formatTime)
//            val dateAndTime = "$date $time"
//
//            val title = "Monitoring Listrik 3 Fasa APP"
//            val message = "Listrik Normal dan Tidak Berbahaya"
//            sendNotification(
//                PushNotification(
//                    NotificationData(
//                        title,
//                        message,
//                        dateAndTime
//                    ),
//                    TOPIC
//                )
//            )
//        }


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