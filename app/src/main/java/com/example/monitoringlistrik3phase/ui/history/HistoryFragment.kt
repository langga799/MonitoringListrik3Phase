package com.example.monitoringlistrik3phase.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringlistrik3phase.databinding.FragmentHistoryBinding
import com.example.monitoringlistrik3phase.helper.inVisible
import com.example.monitoringlistrik3phase.helper.visible
import com.example.monitoringlistrik3phase.ui.setting.notifikasi.ItemMessage
import com.example.monitoringlistrik3phase.ui.setting.notifikasi.MessageAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    private lateinit var firestore: FirebaseFirestore
    private var history =  ArrayList<HistoryModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        binding?.loadingInHistory?.visible()
        firestore.collection("history")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { data ->
                binding?.loadingInHistory?.inVisible()
                for (doc in data.documents){
                    Log.d("doc-message", doc.toString())

                    val date = doc.get("date") as String
                    val fasa1 = doc.get("fasa1") as String
                    val fasa2 = doc.get("fasa2") as String
                    val fasa3 = doc.get("fasa3") as String
                    val persentase1 = doc.get("persentase1") as String
                    val persentase2 = doc.get("persentase2") as String
                    val persentase3 = doc.get("persentase3") as String

                    history.addAll(listOf(HistoryModel(date, fasa1, fasa2, fasa3, persentase1, persentase2, persentase3)))
                }

                setupRecyclerView()
            }

    }


    private fun setupRecyclerView(){
        val adapter = HistoryAdapter(history)
        binding?.apply {
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(requireActivity())
            rvHistory.setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}