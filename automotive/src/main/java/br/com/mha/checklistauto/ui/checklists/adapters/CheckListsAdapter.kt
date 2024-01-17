package br.com.mha.checklistauto.ui.checklists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mha.checklistauto.databinding.CheckListViewLayoutBinding
import br.com.mha.checklistauto.domain.CheckList

class CheckListsAdapter(
    private val onCheckListSelectedListener: (CheckList) -> Unit,
    private var checkLists: List<CheckList>
): RecyclerView.Adapter<CheckListsAdapter.CheckListViewHolder>() {

    class CheckListViewHolder(view: CheckListViewLayoutBinding): RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CheckListViewLayoutBinding.inflate(inflater, parent, false)
        return CheckListViewHolder(view)
    }

    override fun getItemCount() = checkLists.size

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        val checkList = checkLists[position]

        holder.binding.tvCheckListName.text = checkList.name
        holder.binding.clCheckListContainer.setOnClickListener {
            onCheckListSelectedListener.invoke(checkList)
        }
    }

    fun update(checkLists: List<CheckList>) {
        this.checkLists = checkLists
        notifyDataSetChanged()
    }
}