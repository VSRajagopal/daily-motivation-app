package com.dailymotivation.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dailymotivation.R
import com.dailymotivation.data.Quote
import com.dailymotivation.data.QuoteRepository
import com.dailymotivation.databinding.ActivityMainBinding
import com.dailymotivation.widget.NotificationScheduler

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentQuote: Quote? = null
    private val backgroundGradients = listOf(
        intArrayOf(0xFF1a1a2e.toInt(), 0xFF16213e.toInt(), 0xFF0f3460.toInt()),
        intArrayOf(0xFF2d1b69.toInt(), 0xFF11998e.toInt(), 0xFF38ef7d.toInt()),
        intArrayOf(0xFF4e0e0e.toInt(), 0xFF862d2d.toInt(), 0xFFe06b6b.toInt()),
        intArrayOf(0xFF0d4f6c.toInt(), 0xFF1a7f9a.toInt(), 0xFF24a8c9.toInt()),
        intArrayOf(0xFF1a1a0d.toInt(), 0xFF2d4a1e.toInt(), 0xFF4a7c3f.toInt()),
        intArrayOf(0xFF3d1f5c.toInt(), 0xFF6a2d8f.toInt(), 0xFF9b59b6.toInt()),
        intArrayOf(0xFF1f3a5c.toInt(), 0xFF2e5984.toInt(), 0xFF4a90d9.toInt()),
        intArrayOf(0xFF5c2d1f.toInt(), 0xFF8f4a2d.toInt(), 0xFFd4774a.toInt())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        NotificationScheduler.scheduleIfEnabled(this)

        loadDailyQuote()
        setupClickListeners()
    }

    private fun applyTheme() {
        when (QuoteRepository.getTheme(this)) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun loadDailyQuote() {
        currentQuote = QuoteRepository.getDailyQuote(this)
        displayQuote(currentQuote!!, animate = false)
        updateFavoriteButton()
    }

    private fun displayQuote(quote: Quote, animate: Boolean = true) {
        if (animate) {
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            binding.cardQuote.startAnimation(fadeOut)
            fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(a: android.view.animation.Animation?) {}
                override fun onAnimationRepeat(a: android.view.animation.Animation?) {}
                override fun onAnimationEnd(a: android.view.animation.Animation?) {
                    binding.tvQuote.text = "\"${quote.text}\""
                    binding.tvAuthor.text = "— ${quote.author}"
                    binding.tvCategory.text = quote.category
                    val fadeIn = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
                    binding.cardQuote.startAnimation(fadeIn)
                }
            })
        } else {
            binding.tvQuote.text = "\"${quote.text}\""
            binding.tvAuthor.text = "— ${quote.author}"
            binding.tvCategory.text = quote.category
            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            binding.cardQuote.startAnimation(fadeIn)
        }
    }

    private fun setupClickListeners() {
        binding.btnFavorite.setOnClickListener {
            currentQuote?.let { quote ->
                val isFav = QuoteRepository.toggleFavorite(this, quote.id)
                updateFavoriteButton(isFav)
                val msg = if (isFav) "Added to favorites ❤️" else "Removed from favorites"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
                binding.btnFavorite.startAnimation(bounce)
            }
        }

        binding.btnShare.setOnClickListener {
            currentQuote?.let { shareQuote(it) }
        }

        binding.btnNew.setOnClickListener {
            val newQuote = QuoteRepository.getRandomQuote()
            currentQuote = newQuote
            displayQuote(newQuote)
            updateFavoriteButton()
        }

        binding.btnCopy.setOnClickListener {
            currentQuote?.let { quote ->
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText(
                    "quote",
                    "\"${quote.text}\" — ${quote.author}"
                )
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Quote copied! 📋", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareQuote(quote: Quote) {
        val shareText = "\"${quote.text}\"\n\n— ${quote.author}\n\nShared from Daily Motivation App"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Share Quote"))
    }

    private fun updateFavoriteButton(isFav: Boolean? = null) {
        val fav = isFav ?: (currentQuote?.let { QuoteRepository.isFavorite(this, it.id) } ?: false)
        binding.btnFavorite.setImageResource(
            if (fav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        updateFavoriteButton()
    }
}
