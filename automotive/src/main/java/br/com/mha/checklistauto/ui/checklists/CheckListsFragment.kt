package br.com.mha.checklistauto.ui.checklists

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.mha.checklistauto.R
import br.com.mha.checklistauto.databinding.FragmentCheckListsBinding
import br.com.mha.checklistauto.ui.checklists.adapters.CheckListsAdapter
import br.com.mha.checklistauto.ui.checklists.dialogs.AddNewListDialog
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.CHECK_LIST_ID
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListsFragment : Fragment() {

    private lateinit var binding: FragmentCheckListsBinding
    private lateinit var checkListAdapter: CheckListsAdapter
    private val viewModel: CheckListsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckListsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupAddListButtonAction()
        setAudio()
        return binding.root
    }

    private fun setupRecyclerView() {
        checkListAdapter = CheckListsAdapter(
            onCheckListSelectedListener = {
                val bundle = bundleOf(
                    CHECK_LIST_ID to it.id,
                    NAME to it.name
                )
                findNavController().navigate(
                    R.id.action_checkListsScreen_to_checkListItemsFragment,
                    bundle
                )
            }, emptyList()
        )
        binding.rvCheckLists.adapter = checkListAdapter

        updateScreen()
    }

    private fun setupAddListButtonAction() {
        binding.btAddList.setOnClickListener {
            AddNewListDialog(onAddNewListListener = {
                viewModel.addNewList(it)
                updateScreen()
            }).show(parentFragmentManager, ADD_NEW_LIST_TAG)
        }
    }

    private fun updateScreen() {
        val checkLists = viewModel.getAllCheckLists()

        binding.tvNoListsAvailableLabel.isVisible = checkLists.isEmpty()
        binding.rvCheckLists.isVisible = checkLists.isNotEmpty()

        checkListAdapter.update(checkLists)
    }

    private fun setAudio() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {

            }.launch(Manifest.permission.RECORD_AUDIO)
        }

        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")

        val recognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {}

            override fun onBeginningOfSpeech() {
                Log.d("TEST", "Started")
            }

            override fun onRmsChanged(p0: Float) {}

            override fun onBufferReceived(p0: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(p0: Int) {}

            override fun onResults(p0: Bundle?) {
                val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.d("TEST", data?.toString() ?: "")
            }

            override fun onPartialResults(p0: Bundle?) {}

            override fun onEvent(p0: Int, p1: Bundle?) {}

        })

        recognizer.startListening(i)
    }

    companion object {
        private const val ADD_NEW_LIST_TAG = "ADD_NEW_LIST_TAG"
    }
}