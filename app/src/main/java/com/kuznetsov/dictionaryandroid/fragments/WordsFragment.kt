package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.adapter.WordAdapter
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.databinding.FragmentWordsBinding
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.viewmodel.WordsViewModel
import com.kuznetsov.dictionaryandroid.viewmodel.WordsViewModelFactory

private const val TAG = "WordsFragment"

private const val ARG_WORDBOOK_ID = "ARG_WORDBOOK_ID"


class WordsFragment : Fragment() {
    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    private var wordbookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate WordsFragment")
        arguments?.let {
            wordbookId = it.getInt(ARG_WORDBOOK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordsBinding.inflate(layoutInflater, container, false)

        if (wordbookId == null) {
            return binding.root
        }
        val viewModel = ViewModelProvider(this, WordsViewModelFactory(wordbookId!!))
            .get(WordsViewModel::class.java)

        val adapter = WordAdapter()
        binding.wordsList.adapter = adapter
        binding.wordsList.layoutManager = LinearLayoutManager(context)

        viewModel.words.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "viewModel.words.observe = $it")
            adapter.submitList(it)
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(wordbbookId: Int) =
            WordsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WORDBOOK_ID, wordbbookId)
                }
            }
    }
}