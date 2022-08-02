package com.example.monitoringlistrik3phase.serviceimport android.app.Serviceimport android.content.Intentimport android.os.IBinderimport android.widget.Toastimport com.google.firebase.database.DataSnapshotimport com.google.firebase.database.DatabaseErrorimport com.google.firebase.database.ValueEventListenerimport com.google.firebase.database.ktx.databaseimport com.google.firebase.ktx.Firebaseclass ServiceNotification : Service() {    override fun onCreate() {        super.onCreate()        Firebase.database.reference.child("persentase_ketidakseimbangan").child("fasa-1-R")            .addValueEventListener(object : ValueEventListener {                override fun onDataChange(snapshot: DataSnapshot) {                    val value = snapshot.value.toString().toInt()                    when {                        value >= 75 -> {                            Toast.makeText(baseContext, "Tes", Toast.LENGTH_SHORT).show()                        }                        value in 75 downTo 50 -> {                        }                        value < 50 -> {                            warningNotifFasa1(baseContext)                        }                    }                }                override fun onCancelled(error: DatabaseError) {                }            })    }    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {        Toast.makeText(baseContext, "Run", Toast.LENGTH_SHORT).show()        return START_STICKY    }    override fun onBind(p0: Intent?): IBinder? {        return null    }}