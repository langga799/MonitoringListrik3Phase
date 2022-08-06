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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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
                        binding.apply {
                            snapshot.apply {
                                tvAmpere1.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("ampere").child("ampere-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvAmpere2.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("ampere").child("ampere-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvAmpere3.text = this.value.toString().plus(" %")
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
                        binding.apply {
                            snapshot.apply {
                                tvDaya1.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("daya").child("daya-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvDaya2.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("daya").child("daya-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvDaya3.text = this.value.toString().plus(" %")
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
                        binding.apply {
                            snapshot.apply {
                                tvTegangan1.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("tegangan").child("tegangan-fasa-2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvTegangan2.text = this.value.toString().plus(" %")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("listrik").child("tegangan").child("tegangan-fasa-3")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                tvTegangan3.text = this.value.toString().plus(" %")
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

            child("persentase_ketidakseimbangan").child("fasa-1-R")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                binding.progressFasa1R.setProgress(value.toString().toInt())
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("persentase_ketidakseimbangan").child("fasa-2-S")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                binding.progressFasa2S.setProgress(value.toString().toInt())
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            child("persentase_ketidakseimbangan").child("fasa-3-T")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.apply {
                            snapshot.apply {
                                binding.progressFasa3T.setProgress(value.toString().toInt())
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