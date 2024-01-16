package br.com.mha.checklistauto.ui.items.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.mha.checklistauto.R
import br.com.mha.checklistauto.databinding.CheckListItemViewLayoutBinding
import br.com.mha.checklistauto.databinding.CheckListViewLayoutBinding
import br.com.mha.checklistauto.domain.CheckListItem

class CheckListItemAdapter(
    private val context: Context,
    private val onCheckListItemClickListener: (CheckListItem) -> Unit,
    private val items: List<CheckListItem>
) : RecyclerView.Adapter<CheckListItemAdapter.CheckListItemViewHolder>() {

    class CheckListItemViewHolder(view: CheckListItemViewLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CheckListItemViewLayoutBinding.inflate(inflater, parent, false)
        return CheckListItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CheckListItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvItemDescription.text = item.description
        val drawable = ContextCompat.getDrawable(context,
            if (item.isDone) R.drawable.baseline_check_circle_24
            else R.drawable.outline_circle_24
        )
        holder.binding.ivDone.setImageDrawable(drawable)
        holder.binding.clCheckListItemContainer.setOnClickListener {
            onCheckListItemClickListener.invoke(item)
            //notifyItemChanged(position) // TODO: Test this command
            notifyDataSetChanged()
        }
    }
}