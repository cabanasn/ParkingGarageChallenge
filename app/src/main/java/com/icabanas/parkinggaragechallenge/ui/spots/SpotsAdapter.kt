package com.icabanas.parkinggaragechallenge.ui.spots

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icabanas.parkinggaragechallenge.databinding.SpotItemBinding
import com.icabanas.parkinggaragechallenge.vo.Spot

class SpotsAdapter(
        var items: List<Spot>,
        val spotClickCallback: ((Spot) -> Unit)
) : RecyclerView.Adapter<SpotsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpotItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: SpotItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Spot) {
            binding.spot = item
            binding.root.setOnClickListener {
                binding.spot?.let {
                    spotClickCallback.invoke(it)
                }
            }
            binding.executePendingBindings()
        }
    }

}