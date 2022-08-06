package com.example.monitoringlistrik3phase.ui.setting.notifikasi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.databinding.FragmentNotifikasiBinding
import com.example.monitoringlistrik3phase.helper.inVisible
import com.example.monitoringlistrik3phase.helper.visible
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotifikasiFragment : Fragment() {


    private lateinit var binding: FragmentNotifikasiBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var swipe: SwipeRefreshLayout
    private val messageAdapter = MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifikasiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToSetting.setOnClickListener {
            findNavController().navigate(R.id.action_notifikasiFragment_to_navigation_setting)
        }


        firestore = FirebaseFirestore.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Fungsi ini dijalankan", "Coroutine")
            binding.loadingInMessage.visible()
            messageAdapter.clear()
            firestore.collection("message")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { data ->
                    binding.loadingInMessage.inVisible()
                    for (doc in data.documents) {
                        Log.d("doc-message", doc.toString())

                        val title = doc.get("title") as String
                        val body = doc.get("body") as String
                        val time = doc.get("time") as String

                        messageAdapter.addAll(
                            arrayListOf(
                                ItemMessage(
                                    title,
                                    body,
                                    time
                                )
                            )
                        )
                    }
                }
        }




        // =========================================================================================


        swipe = binding.swipeRefresh
        swipe.setOnRefreshListener {

            firestore.collection("message")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { data ->
                    messageAdapter.clear()
                    swipe.isRefreshing = false

                    for (doc in data.documents) {
                        Log.d("doc-message", doc.toString())

                        val title = doc.get("title") as String
                        val body = doc.get("body") as String
                        val time = doc.get("time") as String

                        messageAdapter.addAll(
                            arrayListOf(
                                ItemMessage(
                                    title,
                                    body,
                                    time
                                )
                            )
                        )

                    }


                }

        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvMessaging.adapter = messageAdapter
            rvMessaging.layoutManager = LinearLayoutManager(requireActivity())
            rvMessaging.setHasFixedSize(true)
        }
    }


}