package com.dosa.genai.ui.critique

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dosa.genai.databinding.FragmentCritiqueBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CritiqueFragment : Fragment() {

    private var _binding: FragmentCritiqueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(CritiqueViewModel::class.java)

        _binding = FragmentCritiqueBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.text
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        viewModel.chat()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}