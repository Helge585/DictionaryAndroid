package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuznetsov.dictionaryandroid.TestsFragmentRequestListener
import com.kuznetsov.dictionaryandroid.WordsFragmentRequestListener
import com.kuznetsov.dictionaryandroid.adapter.WordbookAdapter
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.databinding.FragmentWordbooksBinding
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import com.kuznetsov.dictionaryandroid.utils.TestMode
import com.kuznetsov.dictionaryandroid.viewmodel.WordbooksViewModel
import com.kuznetsov.dictionaryandroid.viewmodel.WordbooksViewModelFactory

private const val TAG = "WordbooksFragment"

private const val WORDBOOK_GROUP_ID_ARG = "WORDBOOK_GROUP_ID_ARG"

class WordbooksFragment : Fragment() {
    private var _binding: FragmentWordbooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate WordbooksFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordbooksBinding.inflate(layoutInflater, container, false)

        val groupId = arguments?.getInt(WORDBOOK_GROUP_ID_ARG) ?: -1

        val viewModel = ViewModelProvider(this, WordbooksViewModelFactory(groupId))
            .get(WordbooksViewModel::class.java)

        val adapter = WordbookAdapter(
            this::requestWordsFragment,
            this::requestTestsFragment
        )
        binding.wordbooksList.adapter = adapter
        binding.wordbooksList.layoutManager = LinearLayoutManager(context)

        viewModel.wordbooks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


        return binding.root
    }

    private fun requestWordsFragment(wordbook: Wordbook) {
        (activity as WordsFragmentRequestListener).onWordsFragmentRequest(wordbook)
    }

    private fun requestTestsFragment(wordbook: Wordbook) {
        val mode = binding.testModeSpinner.selectedItem.toString()
        (activity as TestsFragmentRequestListener).onTestFragmentRequest(wordbook, mode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(wordbookGroup: WordbookGroup): WordbooksFragment {
            val args = Bundle()
            args.putInt(WORDBOOK_GROUP_ID_ARG, wordbookGroup.id)
            val fragment = WordbooksFragment()
            fragment.arguments = args
            return fragment
        }
    }
}