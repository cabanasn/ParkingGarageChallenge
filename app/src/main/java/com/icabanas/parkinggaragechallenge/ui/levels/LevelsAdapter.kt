package com.icabanas.parkinggaragechallenge.ui.levels

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icabanas.parkinggaragechallenge.databinding.LevelItemBinding
import com.icabanas.parkinggaragechallenge.vo.Level

class LevelsAdapter(
        var items: List<Level>,
        val levelClickCallback: ((Level) -> Unit)
) : RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LevelItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: LevelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Level) {
            binding.level = LevelBindItem(item)
            binding.root.setOnClickListener {
                binding.level?.let {
                    levelClickCallback.invoke(it.level)
                }
            }
            binding.executePendingBindings()
        }
    }

}