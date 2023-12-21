package com.dosa.genai.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dosa.genai.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
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