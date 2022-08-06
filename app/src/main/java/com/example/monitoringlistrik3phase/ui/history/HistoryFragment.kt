package com.example.monitoringlistrik3phase.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringlistrik3phase.databinding.FragmentHistoryBinding
import com.example.monitoringlistrik3phase.helper.inVisible
import com.example.monitoringlistrik3phase.helper.visible
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    private lateinit var firestore: FirebaseFirestore
    private var history = ArrayList<HistoryModel>()

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
                for (doc in data.documents) {
                    Log.d("doc-message", doc.toString())

                    val date = doc.get("date") as String
                    val ampere = doc.get("ampere_status") as String
                    val tegangan = doc.get("tegangan_status") as String
                    val daya = doc.get("daya_status") as String
                    val listrik = doc.get("listrik") as String
                    val persentase = doc.get("persentase") as String


                    history.addAll(
                        listOf(
                            HistoryModel(
                                date,
                                ampere,
                                tegangan,
                                daya,
                                listrik,
                                persentase
                            )
                        )
                    )
                }

                setupRecyclerView()
            }

    }


    private fun setupRecyclerView() {
        val adapter = HistoryAdapter(history)
        binding?.apply {
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(requireActivity())
            rvHistory.setHasFixedSize(true)
        }

        if (adapter.itemCount == 0){
            binding?.status?.visible()
        } else {
            binding?.status?.inVisible()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}