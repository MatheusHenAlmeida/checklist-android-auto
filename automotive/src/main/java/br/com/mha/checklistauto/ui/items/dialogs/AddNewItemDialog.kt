package br.com.mha.checklistauto.ui.items.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.mha.checklistauto.databinding.AddNewItemDialogLayoutBinding

class AddNewItemDialog(
    private val onAddNewItemListener: (String) -> Unit
): DialogFragment() {

    private lateinit var binding: AddNewItemDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddNewItemDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        binding.btAddNewItem.setOnClickListener {
            onAddNewItemListener.invoke(binding.etItemDescription.text.toString())
            dismiss()
        }
        binding.btCancel.setOnClickListener {
            dismiss()
        }
    }
}