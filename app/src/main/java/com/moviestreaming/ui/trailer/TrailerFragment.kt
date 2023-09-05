package com.moviestreaming.ui.trailer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.moviestreaming.databinding.FragmentTrailerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrailerFragment : Fragment() {

    private var _binding: FragmentTrailerBinding? = null
    private val binding get() = _binding!!
    private val args: TrailerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrailerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}