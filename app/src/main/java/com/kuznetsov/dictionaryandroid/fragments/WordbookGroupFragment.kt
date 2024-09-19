package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuznetsov.dictionaryandroid.WordbooksFragmentRequestListener
import com.kuznetsov.dictionaryandroid.adapter.WordbookGroupAdapter
import com.kuznetsov.dictionaryandroid.databinding.FragmentWordbookGroupBinding
import com.kuznetsov.dictionaryandroid.viewmodel.WordbookGroupsViewModel

private const val TAG = "WordbookGroupFragment"

class WordbookGroupFragment : Fragment() {
    private var _binding: FragmentWordbookGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordbookGroupBinding.inflate(inflater, container, false)

        val adapter = WordbookGroupAdapter {
            (activity as WordbooksFragmentRequestListener).onWordbooksFragmentRequest(it)
        }
        binding.wordbookGroupList.adapter = adapter
        binding.wordbookGroupList.layoutManager = LinearLayoutManager(context)

        val viewModel = ViewModelProvider(this).get(WordbookGroupsViewModel::class.java)

        viewModel.wordbookGroups.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}