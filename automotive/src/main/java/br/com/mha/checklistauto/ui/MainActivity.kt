package br.com.mha.checklistauto.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.mha.checklistauto.databinding.ActivityMainBinding
import br.com.mha.checklistauto.ui.adapters.CheckListsAdapter
import br.com.mha.checklistauto.ui.dialogs.AddNewListDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()
    private lateinit var checkListAdapter: CheckListsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupRecyclerView()
        setupAddListButtonAction()
        setContentView(binding.root)
    }

    private fun setupRecyclerView() {
        val checkLists = viewModel.getAllCheckLists()

        if (checkLists.isNotEmpty()) {
            binding.tvNoListsAvailableLabel.isVisible = false
            checkListAdapter = CheckListsAdapter(checkLists)
            binding.rvCheckLists.adapter = checkListAdapter
            binding.rvCheckLists.isVisible = true
        }
    }

    private fun setupAddListButtonAction() {
        binding.btAddList.setOnClickListener {
            AddNewListDialog(onAddNewListListener = {
                viewModel.addNewList(it)
            }).show(supportFragmentManager, ADD_NEW_LIST_TAG)
        }
    }

    companion object {
        private const val ADD_NEW_LIST_TAG = "ADD_NEW_LIST_TAG"

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}