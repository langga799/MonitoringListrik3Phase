package com.example.monitoringlistrik3phase.serviceimport android.annotation.SuppressLintimport android.app.NotificationChannelimport android.app.NotificationManagerimport android.app.NotificationManager.IMPORTANCE_HIGHimport android.app.PendingIntentimport android.content.Contextimport android.content.Intentimport android.graphics.Colorimport android.os.Buildimport android.util.Logimport android.widget.Toastimport androidx.annotation.RequiresApiimport androidx.core.app.NotificationCompatimport com.example.monitoringlistrik3phase.Rimport com.example.monitoringlistrik3phase.helper.SharedPreferenceimport com.example.monitoringlistrik3phase.service.model.NotificationDataimport com.example.monitoringlistrik3phase.service.model.PushNotificationimport com.example.monitoringlistrik3phase.service.network.NetworkConfigimport com.example.monitoringlistrik3phase.ui.MainActivityimport com.google.firebase.database.DatabaseReferenceimport com.google.firebase.firestore.FirebaseFirestoreimport com.google.firebase.firestore.ktx.firestoreimport com.google.firebase.ktx.Firebaseimport com.google.firebase.messaging.FirebaseMessagingServiceimport com.google.firebase.messaging.RemoteMessageimport kotlinx.coroutines.CoroutineScopeimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.launchimport java.time.LocalDateTimeimport java.time.format.DateTimeFormatterimport kotlin.random.Randomprivate const val TOPIC = "/topics/Listrik"class FirebaseService : FirebaseMessagingService() {    companion object {        private const val CHANNEL_ID = "my_channel"//        var sharedPref: SharedPreferences? = null//        var token: String?//            get() {//                return sharedPref?.getString("token", "")//            }//            set(value) {//                sharedPref?.edit()?.putString("token", value)?.apply()//            }        private lateinit var preferences: SharedPreference    }    private lateinit var fireStore : FirebaseFirestore    private lateinit var reference : DatabaseReference    private var time = ""    override fun onNewToken(newToken: String) {        super.onNewToken(newToken)        //   token = newToken        Log.d("TOKEN-FCM----", newToken)        preferences = SharedPreference(this)        preferences.saveTokenFirebase(newToken)    }    @SuppressLint("UnspecifiedImmutableFlag")    override fun onMessageReceived(message: RemoteMessage) {        super.onMessageReceived(message)// ===================================== Creating List Message ====================================        fireStore = Firebase.firestore        val collection = hashMapOf(            "title" to message.data["title"].toString(),            "body" to message.data["body"].toString(),            "time" to message.data["time"].toString()        )        fireStore.collection("message")            .add(collection)            .addOnSuccessListener { docReference ->                Log.d("MESSAGE", "Message add to ${docReference.id}")            }        time = message.data["time"].toString()        Log.d("Data-Message", message.data["body"].toString())        Log.d("Data-Message", message.data["title"].toString())        Log.d("Data-Message", message.data["time"].toString())// =================================================================================================// =================================================================================================        val intent = Intent(this, MainActivity::class.java)        val notificationManager =            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager        val notificationID = Random.nextInt()        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {            createNotificationChannel(notificationManager)        }        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)        val notification = NotificationCompat.Builder(this, CHANNEL_ID)            .setContentTitle(message.data["title"])            .setContentText(message.data["body"])            .setSmallIcon(R.drawable.ic_listrik)            .setAutoCancel(true)            .setContentIntent(pendingIntent)            .build()        notificationManager.notify(notificationID, notification)// =================================================================================================    }    @RequiresApi(Build.VERSION_CODES.O)    private fun createNotificationChannel(notificationManager: NotificationManager) {        val channelName = "ChannelName"        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {            description = "My channel description"            enableLights(true)            lightColor = Color.GREEN        }        notificationManager.createNotificationChannel(channel)    }}// Send Notif Automaticallyfun warningNotifFasa1(context: Context){    CoroutineScope(Dispatchers.Main).launch {        Toast.makeText(context, "Tess", Toast.LENGTH_SHORT).show()    }    val current = LocalDateTime.now()    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")    val date = current.format(formatter)    val formatTime = DateTimeFormatter.ofPattern("HH:mm")    val time = current.format(formatTime)    val dateAndTime = "$date $time"    val title = "Monitoring Listrik 3 Fasa APP"    val message = "Listrik Fasa 1 Tidak Normal, Periksa Sekarang"    sendNotification(        PushNotification(            NotificationData(                title,                message,                dateAndTime            ),            TOPIC        )    )}fun warningNotifFasa2(){    val current = LocalDateTime.now()    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")    val date = current.format(formatter)    val formatTime = DateTimeFormatter.ofPattern("HH:mm")    val time = current.format(formatTime)    val dateAndTime = "$date $time"    val title = "Monitoring Listrik 3 Fasa APP"    val message = "Listrik Fasa 2 Tidak Normal, Periksa Sekarang"    sendNotification(        PushNotification(            NotificationData(                title,                message,                dateAndTime            ),            TOPIC        )    )}fun warningNotifFasa3(){    val current = LocalDateTime.now()    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")    val date = current.format(formatter)    val formatTime = DateTimeFormatter.ofPattern("HH:mm")    val time = current.format(formatTime)    val dateAndTime = "$date $time"    val title = "Monitoring Listrik 3 Fasa APP"    val message = "Listrik Fasa 3 Tidak Normal, Periksa Sekarang"    sendNotification(        PushNotification(            NotificationData(                title,                message,                dateAndTime            ),            TOPIC        )    )}private fun sendNotification(notification: PushNotification) {    CoroutineScope(Dispatchers.IO).launch {        try {            Log.d("SEND", "PUSH_NOTIFICATION")            val response = NetworkConfig().getApiService().pushNotification(notification)            if (response.isSuccessful) {                //   Log.d(TAG, "Response: ${Gson().toJson(response)}")            } else {                Log.d("SEND_NOTIFICATION", response.errorBody().toString())            }        } catch (e: Exception) {            Log.e("SEND_NOTIFICATION", e.toString())        }    }}