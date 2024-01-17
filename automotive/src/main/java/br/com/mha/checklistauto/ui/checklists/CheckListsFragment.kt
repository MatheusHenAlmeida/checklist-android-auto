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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.mha.checklistauto.R
import br.com.mha.checklistauto.databinding.FragmentCheckListsBinding
import br.com.mha.checklistauto.sensors.VoiceSensor
import br.com.mha.checklistauto.ui.checklists.adapters.CheckListsAdapter
import br.com.mha.checklistauto.ui.checklists.dialogs.AddNewListDialog
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.CHECK_LIST_ID
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.NAME
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListsFragment : Fragment() {

    private lateinit var binding: FragmentCheckListsBinding
    private lateinit var checkListAdapter: CheckListsAdapter
    private val viewModel: CheckListsViewModel by viewModel()
    private val voiceSensor: VoiceSensor by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckListsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupAddListButtonAction()
        setAudioSystem()
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

    private fun setAudioSystem() {
        if (audioPermissionsAreNotGranted()) {
            askPermissionToTheUser()
        } else {
            startSpeechRecognizer()
        }
    }

    private fun audioPermissionsAreNotGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.RECORD_AUDIO
    ) != PackageManager.PERMISSION_GRANTED

    private fun askPermissionToTheUser() {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) startSpeechRecognizer()
        }.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun startSpeechRecognizer() {
        voiceSensor.setCallbacks(onStart = {}, onCommandListened = {
            Log.d("COMMAND", it)
        })

        voiceSensor.startListening()
    }

    companion object {
        private const val ADD_NEW_LIST_TAG = "ADD_NEW_LIST_TAG"
    }
}