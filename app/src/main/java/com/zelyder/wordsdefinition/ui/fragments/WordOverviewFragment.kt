package com.zelyder.wordsdefinition.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zelyder.wordsdefinition.databinding.FragmentWordOverviewBinding
import com.zelyder.wordsdefinition.ui.adapters.InputFieldListener
import com.zelyder.wordsdefinition.ui.adapters.WordPropertiesAdapter
import com.zelyder.wordsdefinition.viewmodels.WordOverviewViewModel

class WordOverviewFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(WordOverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWordOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel =viewModel

        val adapter = WordPropertiesAdapter(InputFieldListener {word ->
            binding.viewModel?.getWordDefinitions(word)
        })

        binding.wordPropertiesList.adapter = adapter

        viewModel.definitions.observe(viewLifecycleOwner, {
            it?.let {
                adapter.addInputFieldAndSubmitList(it)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}