package com.example.monitoringlistrik3phase.ui.setting.bantuan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.databinding.FragmentBantuanBinding
import java.net.URLEncoder


class BantuanFragment : Fragment() {

    private var _binding: FragmentBantuanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBantuanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToSetting.setOnClickListener {
            findNavController().navigate(R.id.action_bantuanFragment_to_navigation_setting)
        }

        binding.btnHubungiKami.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val noTelp = "6287853032923"
            val encodedText = URLEncoder.encode("Tuliskan pesan Anda", "UTF-8")

            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$noTelp&text=$encodedText")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}