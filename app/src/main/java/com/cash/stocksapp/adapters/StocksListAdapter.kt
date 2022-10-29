package com.cash.stocksapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cash.stocksapp.databinding.StocksRecyclerListItemBinding
import com.cash.stocksapp.models.StockData
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class StocksListAdapter @Inject constructor(
    val clickListener: ClickListener
) : ListAdapter<StockData, StockItemViewHolder>(StocksListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val binding = StocksRecyclerListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StockItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}


class StocksListDiffUtilCallback : DiffUtil.ItemCallback<StockData>() {
    override fun areItemsTheSame(oldItem: StockData, newItem: StockData): Boolean {
        return oldItem.tickerSymbol == newItem.tickerSymbol
    }

    override fun areContentsTheSame(oldItem: StockData, newItem: StockData): Boolean {
        return oldItem == newItem
    }
}

/**
 * Holds the view for the [StockData] item.
 */
class StockItemViewHolder(private val binding: StocksRecyclerListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(stockData: StockData, itemClickListener: ClickListener) {
        binding.apply {
            this.stockData = stockData
            executePendingBindings()
            clickListener = itemClickListener
        }
    }
}

class ClickListener @Inject constructor() {

    // lambda to pass the onClick callback
    var onItemClick: ((StockData) -> Unit)? = null

    // TODO implement onClick when required
    fun onClick(data: StockData) {
        onItemClick?.invoke(data)
    }
}
