package br.com.mha.checklistauto.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.mha.checklistauto.R
import br.com.mha.checklistauto.databinding.FragmentCheckListItemsBinding
import br.com.mha.checklistauto.ui.items.adapter.CheckListItemAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListItemsFragment : Fragment() {

    private lateinit var binding: FragmentCheckListItemsBinding
    private lateinit var checkListItemAdapter: CheckListItemAdapter
    private val viewModel: CheckListItemsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckListItemsBinding.inflate(inflater, container, false)
        setupListName()
        setupRecyclerView()
        return binding.root
    }

    private fun setupListName() {
        val name = arguments?.getString(NAME) ?: ""
        binding.tvListNameTitle.text = name
    }

    private fun setupRecyclerView() {
        val checkListId = arguments?.getInt(CHECK_LIST_ID) ?: -1
        val items = viewModel.getItemsFromList(checkListId)

        if (items.isNotEmpty()) {
            binding.tvNoItemsAvailableLabel.isVisible = false
            checkListItemAdapter = CheckListItemAdapter(requireContext(), items)
            binding.rvCheckListItems.adapter = checkListItemAdapter
            binding.rvCheckListItems.isVisible = true
        }
    }

    companion object {
        const val CHECK_LIST_ID = "CHECK_LIST_ID"
        const val NAME = "NAME"
    }
}