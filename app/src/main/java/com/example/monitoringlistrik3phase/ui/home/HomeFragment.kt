package com.example.monitoringlistrik3phase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.monitoringlistrik3phase.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = Firebase.database.reference


        getDataPersentase()
        getPersentaseKetidakSeimbangan()
    }


    private fun getDataPersentase() {
        databaseReference.apply {

// =========================================== Ampere ==============================================
            child("listrik").child("ampere").child("ampere-fasa-1")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvAmpere1.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("ampere").child("ampere-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvAmpere2.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("ampere").child("ampere-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvAmpere3.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
// =================================================================================================


// =========================================== Daya ================================================
            child("listrik").child("daya").child("daya-fasa-1")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvDaya1.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("daya").child("daya-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvDaya2.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("daya").child("daya-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvDaya3.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
// =================================================================================================


// ========================================= Tegangan ==============================================
            child("listrik").child("tegangan").child("tegangan-fasa-1")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvTegangan1.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("tegangan").child("tegangan-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvTegangan2.text = decimalFormat
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("tegangan").child("tegangan-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                val decimalFormat = "%.2f".format(snapshot.value.toString().toDouble())
                                tvTegangan3.text = decimalFormat
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

// =================================================================================================

        }
    }


    // ================================= Persentase Ketidakseimbangan ==================================
    private fun getPersentaseKetidakSeimbangan() {
        databaseReference.apply {

            child("persentase_ketidakseimbangan").child("fasa-RST")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding?.apply {
                            snapshot.apply {
                                binding?.progressFasaRST?.setProgress(value.toString().toInt())
                                binding?.numberPersentase?.text = value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })


// =================================================================================================
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}