package br.com.mha.checklistauto.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.mha.checklistauto.commands.CreateItemCommand
import br.com.mha.checklistauto.commands.DeleteItemCommand
import br.com.mha.checklistauto.commands.ErrorMessageCommand
import br.com.mha.checklistauto.commands.MarkItemCommand
import br.com.mha.checklistauto.commands.ReadItemsCommand
import br.com.mha.checklistauto.commands.ReturnToMainScreenCommand
import br.com.mha.checklistauto.databinding.FragmentCheckListItemsBinding
import br.com.mha.checklistauto.ui.BaseFragment
import br.com.mha.checklistauto.ui.items.adapter.CheckListItemAdapter
import br.com.mha.checklistauto.ui.items.dialogs.AddNewItemDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListItemsFragment : BaseFragment() {

    private lateinit var binding: FragmentCheckListItemsBinding
    private lateinit var checkListItemAdapter: CheckListItemAdapter
    private val viewModel: CheckListItemsViewModel by viewModel()
    private var checkListId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckListItemsBinding.inflate(inflater, container, false)
        setupListName()
        updateCheckListIdOnViewModel()
        setupRecyclerView()
        setupButtons()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    private fun setupListName() {
        val name = arguments?.getString(NAME) ?: ""
        binding.tvListNameTitle.text = name
    }

    private fun updateCheckListIdOnViewModel() {
        checkListId = arguments?.getString(CHECK_LIST_ID) ?: ""
    }

    private fun setupRecyclerView() {
        checkListItemAdapter = CheckListItemAdapter(
            requireContext(),
            onCheckListItemClickListener = { itemId, isDone ->
                viewModel.updateItemStatus(itemId, isDone.not())
                updateScreen()
            }, emptyList()
        )
        binding.rvCheckListItems.adapter = checkListItemAdapter

        updateScreen()
    }

    private fun setupButtons() {
        binding.btAddItem.setOnClickListener {
            AddNewItemDialog(onAddNewItemListener = { description ->
                viewModel.addNewItemToList(checkListId, description)
                updateScreen()
            }).show(parentFragmentManager, ADD_NEW_ITEM_TAG)
        }

        binding.btStartToListen.setOnClickListener {
            if (audioPermissionsAreNotGranted().not()) {
                toggleVoiceSensor()
            }
        }

        binding.btBackToLists.setOnClickListener {
            goBackToMainScreen()
        }
    }

    private fun toggleVoiceSensor() {
        if (voiceSensor.isListening) {
            voiceSensor.stopListening()
            setBtStartToListenStatus(false)
        } else {
            voiceSensor.startListening()
            setBtStartToListenStatus(true)
        }
    }

    private fun updateScreen() {
        val items = viewModel.getItemsFromList(checkListId)

        binding.tvNoItemsAvailableLabel.isVisible = items.isEmpty()
        binding.rvCheckListItems.isVisible = items.isNotEmpty()

        checkListItemAdapter.update(items)
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
        val readItemsCommand = ReadItemsCommand(
            viewModel.getItemsFromList(checkListId),
            voiceSensor,
            errorMessageCommand
        )
        val createItemCommand = CreateItemCommand({
            viewModel.addNewItemToList(checkListId, it)
            updateScreen()
        }, readItemsCommand)
        val deleteItemCommand = DeleteItemCommand({
            viewModel.deleteItemByDescription(it)
            updateScreen()
        }, createItemCommand)
        val markItemCommand = MarkItemCommand({
            viewModel.toggleItemStatusByDescription(it)
            updateScreen()
        }, deleteItemCommand)
        val returnToMainScreenCommand = ReturnToMainScreenCommand({
            goBackToMainScreen()
        }, markItemCommand)

        returnToMainScreenCommand.evaluate(command)
    }

    private fun setBtStartToListenStatus(isListening: Boolean) {
        val color = if (isListening) android.R.color.holo_red_dark else android.R.color.darker_gray
        binding.btStartToListen.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), color)
    }

    private fun goBackToMainScreen() = findNavController().popBackStack()

    companion object {
        const val CHECK_LIST_ID = "CHECK_LIST_ID"
        const val NAME = "NAME"
        private const val ADD_NEW_ITEM_TAG = "ADD_NEW_ITEM_TAG"
    }
}