package br.com.mha.checklistauto.ui.checklists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

    private fun setupRecyclerView() {
        val checkLists = viewModel.getAllCheckLists()

        if (checkLists.isNotEmpty()) {
            binding.tvNoListsAvailableLabel.isVisible = false
            checkListAdapter = CheckListsAdapter(
                onCheckListSelectedListener = {
                    val bundle = bundleOf(
                        CHECK_LIST_ID to it.id,
                        NAME to it.name
                    )
                    findNavController().navigate(R.id.action_checkListsScreen_to_checkListItemsFragment, bundle)
                }, checkLists)
            binding.rvCheckLists.adapter = checkListAdapter
            binding.rvCheckLists.isVisible = true
        }
    }

    private fun setupAddListButtonAction() {
        binding.btAddList.setOnClickListener {
            AddNewListDialog(onAddNewListListener = {
                viewModel.addNewList(it)
            }).show(parentFragmentManager, ADD_NEW_LIST_TAG)
        }
    }

    companion object {
        private const val ADD_NEW_LIST_TAG = "ADD_NEW_LIST_TAG"
    }
}