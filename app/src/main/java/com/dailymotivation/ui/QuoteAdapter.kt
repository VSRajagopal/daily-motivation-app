package com.dailymotivation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dailymotivation.R
import com.dailymotivation.data.Quote
import com.dailymotivation.data.QuoteRepository
import com.dailymotivation.databinding.ItemQuoteBinding

class QuoteAdapter(
    private val onShareClick: (Quote) -> Unit,
    private val onFavoriteClick: (Quote) -> Unit
) : ListAdapter<Quote, QuoteAdapter.QuoteViewHolder>(QuoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quote: Quote) {
            binding.tvQuoteText.text = "\"${quote.text}\""
            binding.tvQuoteAuthor.text = "— ${quote.author}"
            binding.tvCategory.text = quote.category

            val isFav = QuoteRepository.isFavorite(binding.root.context, quote.id)
            binding.btnFavorite.setImageResource(
                if (isFav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
            )

            binding.btnShare.setOnClickListener { onShareClick(quote) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(quote) }
        }
    }
}

class QuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Quote, newItem: Quote) = oldItem == newItem
}
