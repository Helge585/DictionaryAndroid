package com.kuznetsov.dictionaryandroid.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.adapter.TestAdapter
import com.kuznetsov.dictionaryandroid.databinding.FragmentTestsBinding
import com.kuznetsov.dictionaryandroid.utils.TestMode
import com.kuznetsov.dictionaryandroid.viewmodel.TestsViewModel
import com.kuznetsov.dictionaryandroid.viewmodel.TestsViewModelFactory

private const val TAG = "TestsFragment"

private const val ARG_WORDBOOK_ID = "ARG_WORDBOOK_ID"
private const val ARG_TEST_MODE = "ARG_TEST_MODE"

class TestsFragment : Fragment() {
    private var _binding: FragmentTestsBinding? = null
    private val binding get() = _binding!!

    private var wordbookId: Int? = null
    private var testMode: String? = null
    private var percentTextStep = 0.0
    private var progressBarStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordbookId = it.getInt(ARG_WORDBOOK_ID)
            testMode = it.getString(ARG_TEST_MODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestsBinding.inflate(inflater, container, false)

        if (wordbookId == null || testMode == null) {
            return binding.root
        }
        val viewModel = ViewModelProvider(this, TestsViewModelFactory(wordbookId!!))
            .get(TestsViewModel::class.java)

        val adapter = TestAdapter(testMode!!,
            { step ->
                //binding.testsList.scrollToPosition(it)
                binding.testsList.adapter?.let {
                    if (step >= 0 && step < it.itemCount) {
                        binding.testsList.scrollToPosition(step)
                    }
                }
            },
            { isAnswerTrue: Boolean, stepValue: Int ->
                setAnswer(isAnswerTrue, stepValue)
            }
        )
        binding.testsList.adapter = adapter
        binding.testsList.layoutManager = LinearLayoutManager(context)

        viewModel.words.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.testProgressBar.max = it.size
            Log.i(TAG, "testProgressBar.max = ${ it.size }")
            //progressBarStep = (1.0 / it.size * 100).toInt() + 1
            percentTextStep = 1.0 / it.size * 100
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAnswer(isAnswerTrue: Boolean, stepValue: Int) {
        if (isAnswerTrue) {
            Log.i(TAG, "progress = ${ binding.testProgressBar.progress }")
            binding.testProgressBar.progress += 1
            val newPercent =
                binding.testProgressPercentText.text.toString().removeSuffix("%").toDouble() + percentTextStep
            binding.testProgressPercentText.text = newPercent.toString() + "%"
        }
        Log.i(TAG, "secondaryProgress = ${ binding.testProgressBar.secondaryProgress }")
        //binding.testProgressBar.secondaryProgress += stepValue
    }

    companion object {
        @JvmStatic
        fun newInstance(wordbookId: Int, testMode: String) =
            TestsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WORDBOOK_ID, wordbookId)
                    putSerializable(ARG_TEST_MODE, testMode)
                }
            }
    }
}