package br.com.mha.checklistauto.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.mha.checklistauto.databinding.FragmentCheckListItemsBinding
import br.com.mha.checklistauto.ui.items.adapter.CheckListItemAdapter
import br.com.mha.checklistauto.ui.items.dialogs.AddNewItemDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListItemsFragment : Fragment() {

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
        val items = viewModel.getItemsFromList(checkListId)

        if (items.isNotEmpty()) {
            binding.tvNoItemsAvailableLabel.isVisible = false

            binding.rvCheckListItems.isVisible = true
        }

        checkListItemAdapter = CheckListItemAdapter(requireContext(),
            onCheckListItemClickListener = {
                viewModel.updateItemStatus(it.id, it.isDone.not())
            }, items)
        binding.rvCheckListItems.adapter = checkListItemAdapter
    }

    private fun setupButtons() {
        binding.btAddItem.setOnClickListener {
            AddNewItemDialog(onAddNewItemListener = { description ->
                viewModel.addNewItemToList(checkListId, description)
                checkListItemAdapter.update(viewModel.getItemsFromList(checkListId))
            }).show(parentFragmentManager, ADD_NEW_ITEM_TAG)
        }

        binding.btBackToLists.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val CHECK_LIST_ID = "CHECK_LIST_ID"
        const val NAME = "NAME"
        private const val ADD_NEW_ITEM_TAG = "ADD_NEW_ITEM_TAG"
    }
}