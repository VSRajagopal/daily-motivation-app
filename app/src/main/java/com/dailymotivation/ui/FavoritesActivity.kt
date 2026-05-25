package com.dailymotivation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dailymotivation.R
import com.dailymotivation.data.Quote
import com.dailymotivation.data.QuoteRepository
import com.dailymotivation.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Favorites"

        setupRecyclerView()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        adapter = QuoteAdapter(
            onShareClick = { quote -> shareQuote(quote) },
            onFavoriteClick = { quote ->
                QuoteRepository.toggleFavorite(this, quote.id)
                loadFavorites()
            }
        )
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter
    }

    private fun loadFavorites() {
        val favorites = QuoteRepository.getFavoriteQuotes(this)
        adapter.submitList(favorites)
        binding.tvEmpty.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
        binding.rvFavorites.visibility = if (favorites.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun shareQuote(quote: Quote) {
        val shareText = "\"${quote.text}\"\n\n— ${quote.author}\n\nShared from Daily Motivation App"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Share Quote"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
