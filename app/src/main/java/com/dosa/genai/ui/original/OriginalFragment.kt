package com.dosa.genai.ui.original

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dosa.genai.databinding.FragmentOriginalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OriginalFragment : Fragment() {

    private var _binding: FragmentOriginalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(OriginalViewModel::class.java)

        _binding = FragmentOriginalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.text
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val imageView: ImageView = binding.image
        viewModel.image.observe(viewLifecycleOwner) {
            imageView.setImageBitmap(it)
        }

        viewModel.generateStory()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}