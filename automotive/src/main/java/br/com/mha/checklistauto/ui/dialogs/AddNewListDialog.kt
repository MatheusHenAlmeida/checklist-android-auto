package br.com.mha.checklistauto.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.mha.checklistauto.databinding.AddNewListDialogLayoutBinding

class AddNewListDialog(
    private val onAddNewListListener: (String) -> Unit
): DialogFragment() {

    private lateinit var binding: AddNewListDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddNewListDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        binding.btAddNewList.setOnClickListener {
            onAddNewListListener.invoke(binding.etListName.text.toString())
            dismiss()
        }
        binding.btCancel.setOnClickListener {
            dismiss()
        }
    }
}