package br.com.mha.checklistauto.ui.checklists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.mha.checklistauto.R
import br.com.mha.checklistauto.commands.CreateListCommand
import br.com.mha.checklistauto.commands.DeleteListCommand
import br.com.mha.checklistauto.commands.ErrorMessageCommand
import br.com.mha.checklistauto.commands.OpenListCommand
import br.com.mha.checklistauto.commands.ReadAllListsCommand
import br.com.mha.checklistauto.databinding.FragmentCheckListsBinding
import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.ui.BaseFragment
import br.com.mha.checklistauto.ui.checklists.adapters.CheckListsAdapter
import br.com.mha.checklistauto.ui.checklists.dialogs.AddNewListDialog
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.CHECK_LIST_ID
import br.com.mha.checklistauto.ui.items.CheckListItemsFragment.Companion.NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListsFragment : BaseFragment() {

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
        setupStartToListenButton()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    private fun setupRecyclerView() {
        checkListAdapter = CheckListsAdapter(
            onCheckListSelectedListener = {
                goToItemsScreen(it)
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

    override fun startSpeechRecognizer() {
        voiceSensor.setCallbacks(onStart = {}, onCommandListened = {
            processCommand(it)
        }, onComplete = {
            setBtStartToListenStatus(false)
        })
    }

    private fun processCommand(command: String) {
        val errorMessageCommand = ErrorMessageCommand(voiceSensor)
        val readAllListsCommand = ReadAllListsCommand(
            viewModel.getAllCheckLists(), voiceSensor, errorMessageCommand
        )
        val createListCommand = CreateListCommand({
            viewModel.addNewList(it)
            updateScreen()
        }, readAllListsCommand)
        val openListCommand = OpenListCommand({ listName ->
            viewModel.getListByName(listName)?.let {
                goToItemsScreen(it)
            }
        }, createListCommand)
        val deleteListCommand = DeleteListCommand({
            viewModel.deleteList(it)
            updateScreen()
        }, openListCommand)

        deleteListCommand.evaluate(command)
    }

    private fun setupStartToListenButton() {
        binding.btStartToListen.setOnClickListener {
            if (audioPermissionsAreNotGranted().not()) {
                voiceSensor.startListening()
                setBtStartToListenStatus(true)
            }
        }
    }

    private fun setBtStartToListenStatus(isListening: Boolean) {
        val color = if (isListening) android.R.color.holo_red_dark else android.R.color.darker_gray
        binding.btStartToListen.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), color)
    }

    private fun goToItemsScreen(checkList: CheckList) {
        val bundle = bundleOf(
            CHECK_LIST_ID to checkList.id,
            NAME to checkList.name
        )
        findNavController().navigate(
            R.id.action_checkListsScreen_to_checkListItemsFragment,
            bundle
        )
    }

    companion object {
        private const val ADD_NEW_LIST_TAG = "ADD_NEW_LIST_TAG"
    }
}