package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.adapter.TestAdapter
import com.kuznetsov.dictionaryandroid.databinding.FragmentTestsBinding
import com.kuznetsov.dictionaryandroid.viewmodel.TestsViewModel
import com.kuznetsov.dictionaryandroid.viewmodel.TestsViewModelFactory

private const val TAG = "TestsFragment"

private const val ARG_WORDBOOK_ID = "ARG_WORDBOOK_ID"

class TestsFragment : Fragment() {
    private var _binding: FragmentTestsBinding? = null
    private val binding get() = _binding!!

    private var wordbookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordbookId = it.getInt(ARG_WORDBOOK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestsBinding.inflate(inflater, container, false)

        if (wordbookId == null) {
            return binding.root
        }
        val viewModel = ViewModelProvider(this, TestsViewModelFactory(wordbookId!!))
            .get(TestsViewModel::class.java)

        val adapter = TestAdapter()
        binding.testsList.adapter = adapter
        binding.testsList.layoutManager = LinearLayoutManager(context)

        viewModel.words.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(wordbookId: Int) =
            TestsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WORDBOOK_ID, wordbookId)
                }
            }
    }
}