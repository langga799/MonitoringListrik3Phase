package com.example.monitoringlistrik3phase.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnToAturPengingat.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_setting_to_aturPengingatFragment)
            }
            btnToNotifikasi.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_setting_to_notifikasiFragment)
            }
            btnToBantuan.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_setting_to_bantuanFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}