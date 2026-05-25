package com.dailymotivation.data

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar

data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    val category: String
)

object QuoteRepository {

    private const val PREFS_NAME = "daily_motivation_prefs"
    private const val KEY_FAVORITES = "favorites"
    private const val KEY_LAST_DATE = "last_date"
    private const val KEY_DAILY_QUOTE_ID = "daily_quote_id"
    private const val KEY_NOTIFICATION_HOUR = "notification_hour"
    private const val KEY_NOTIFICATION_MINUTE = "notification_minute"
    private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    private const val KEY_THEME = "theme"

    val allQuotes = listOf(
        Quote(1, "The only way to do great work is to love what you do.", "Steve Jobs", "Success"),
        Quote(2, "Believe you can and you're halfway there.", "Theodore Roosevelt", "Belief"),
        Quote(3, "It does not matter how slowly you go as long as you do not stop.", "Confucius", "Perseverance"),
        Quote(4, "Life is what happens when you're busy making other plans.", "John Lennon", "Life"),
        Quote(5, "The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt", "Dreams"),
        Quote(6, "It is during our darkest moments that we must focus to see the light.", "Aristotle", "Courage"),
        Quote(7, "Spread love everywhere you go. Let no one ever come to you without leaving happier.", "Mother Teresa", "Love"),
        Quote(8, "When you reach the end of your rope, tie a knot in it and hang on.", "Franklin D. Roosevelt", "Perseverance"),
        Quote(9, "Always remember that you are absolutely unique. Just like everyone else.", "Margaret Mead", "Humor"),
        Quote(10, "Don't judge each day by the harvest you reap but by the seeds that you plant.", "Robert Louis Stevenson", "Life"),
        Quote(11, "The best time to plant a tree was 20 years ago. The second best time is now.", "Chinese Proverb", "Action"),
        Quote(12, "An unexamined life is not worth living.", "Socrates", "Wisdom"),
        Quote(13, "Spread love everywhere you go.", "Mother Teresa", "Love"),
        Quote(14, "When you have a dream, you've got to grab it and never let go.", "Carol Burnett", "Dreams"),
        Quote(15, "I can't change the direction of the wind, but I can adjust my sails.", "Jimmy Dean", "Adaptability"),
        Quote(16, "You must be the change you wish to see in the world.", "Mahatma Gandhi", "Change"),
        Quote(17, "In the middle of every difficulty lies opportunity.", "Albert Einstein", "Opportunity"),
        Quote(18, "Life is not measured by the number of breaths we take, but by the moments that take our breath away.", "Maya Angelou", "Life"),
        Quote(19, "Happiness is not something ready-made. It comes from your own actions.", "Dalai Lama", "Happiness"),
        Quote(20, "The only impossible journey is the one you never begin.", "Tony Robbins", "Action"),
        Quote(21, "Success is not final, failure is not fatal: It is the courage to continue that counts.", "Winston Churchill", "Success"),
        Quote(22, "You only live once, but if you do it right, once is enough.", "Mae West", "Life"),
        Quote(23, "In order to be irreplaceable one must always be different.", "Coco Chanel", "Individuality"),
        Quote(24, "Life is not about finding yourself. Life is about creating yourself.", "George Bernard Shaw", "Self"),
        Quote(25, "The purpose of our lives is to be happy.", "Dalai Lama", "Happiness"),
        Quote(26, "Get busy living or get busy dying.", "Stephen King", "Action"),
        Quote(27, "You have brains in your head. You have feet in your shoes. You can steer yourself in any direction you choose.", "Dr. Seuss", "Self"),
        Quote(28, "If life were predictable it would cease to be life, and be without flavor.", "Eleanor Roosevelt", "Life"),
        Quote(29, "If you look at what you have in life, you'll always have more.", "Oprah Winfrey", "Gratitude"),
        Quote(30, "If you want to live a happy life, tie it to a goal, not to people or things.", "Albert Einstein", "Happiness"),
        Quote(31, "Never let the fear of striking out keep you from playing the game.", "Babe Ruth", "Courage"),
        Quote(32, "Money and success don't change people; they merely amplify what is already there.", "Will Smith", "Character"),
        Quote(33, "Your time is limited, so don't waste it living someone else's life.", "Steve Jobs", "Authenticity"),
        Quote(34, "Not how long, but how well you have lived is the main thing.", "Seneca", "Life"),
        Quote(35, "If life is not a celebration, why remember it?", "Artur Rubinstein", "Joy"),
        Quote(36, "The greatest glory in living lies not in never falling, but in rising every time we fall.", "Nelson Mandela", "Resilience"),
        Quote(37, "The way to get started is to quit talking and begin doing.", "Walt Disney", "Action"),
        Quote(38, "Life is what we make it, always has been, always will be.", "Grandma Moses", "Life"),
        Quote(39, "You will face many defeats in life, but never let yourself be defeated.", "Maya Angelou", "Resilience"),
        Quote(40, "The greatest pleasure of life is love.", "Euripides", "Love"),
        Quote(41, "Life is really simple, but we insist on making it complicated.", "Confucius", "Wisdom"),
        Quote(42, "May you live all the days of your life.", "Jonathan Swift", "Life"),
        Quote(43, "Life itself is the most wonderful fairy tale.", "Hans Christian Andersen", "Wonder"),
        Quote(44, "Do not go where the path may lead, go instead where there is no path and leave a trail.", "Ralph Waldo Emerson", "Leadership"),
        Quote(45, "You don't have to be great to start, but you have to start to be great.", "Zig Ziglar", "Action"),
        Quote(46, "Keep your face always toward the sunshine, and shadows will fall behind you.", "Walt Whitman", "Positivity"),
        Quote(47, "The only way out of the labyrinth of suffering is to forgive.", "John Green", "Forgiveness"),
        Quote(48, "Hardships often prepare ordinary people for an extraordinary destiny.", "C.S. Lewis", "Perseverance"),
        Quote(49, "Believe in yourself. You are braver than you think, more talented than you know.", "Roy T. Bennett", "Belief"),
        Quote(50, "What you get by achieving your goals is not as important as what you become.", "Henry David Thoreau", "Growth"),
        Quote(51, "Act as if what you do makes a difference. It does.", "William James", "Action"),
        Quote(52, "Success usually comes to those who are too busy to be looking for it.", "Henry David Thoreau", "Success"),
        Quote(53, "Opportunities don't happen. You create them.", "Chris Grosser", "Opportunity"),
        Quote(54, "Don't be afraid to give up the good to go for the great.", "John D. Rockefeller", "Ambition"),
        Quote(55, "I find that the harder I work, the more luck I seem to have.", "Thomas Jefferson", "Work"),
        Quote(56, "The secret of getting ahead is getting started.", "Mark Twain", "Action"),
        Quote(57, "It's not whether you get knocked down, it's whether you get up.", "Vince Lombardi", "Resilience"),
        Quote(58, "People who are crazy enough to think they can change the world are the ones who do.", "Rob Siltanen", "Change"),
        Quote(59, "Failure will never overtake me if my determination to succeed is strong enough.", "Og Mandino", "Determination"),
        Quote(60, "We may encounter many defeats but we must not be defeated.", "Maya Angelou", "Resilience"),
        Quote(61, "Knowing is not enough; we must apply. Wishing is not enough; we must do.", "Johann Wolfgang Von Goethe", "Action"),
        Quote(62, "Imagine your life is perfect in every respect; what would it look like?", "Brian Tracy", "Visualization"),
        Quote(63, "We generate fears while we sit. We overcome them by action.", "Dr. Henry Link", "Action"),
        Quote(64, "Whether you think you can or think you can't, you're right.", "Henry Ford", "Mindset"),
        Quote(65, "The man who has confidence in himself gains the confidence of others.", "Hasidic Proverb", "Confidence"),
        Quote(66, "The real test is not whether you avoid failure, but whether you let it harden you.", "Barack Obama", "Resilience"),
        Quote(67, "If you genuinely want something, don't wait for it; teach yourself to be impatient.", "Gurbaksh Chahal", "Desire"),
        Quote(68, "If you are not willing to risk the usual, you will have to settle for the ordinary.", "Jim Rohn", "Risk"),
        Quote(69, "Trust because you are willing to accept the risk, not because it's safe.", "Anonymous", "Trust"),
        Quote(70, "Take up one idea. Make that one idea your life.", "Swami Vivekananda", "Focus"),
        Quote(71, "All our dreams can come true if we have the courage to pursue them.", "Walt Disney", "Dreams"),
        Quote(72, "Good things come to people who wait, but better things come to those who go out and get them.", "Anonymous", "Action"),
        Quote(73, "If you do what you always did, you will get what you always got.", "Anonymous", "Change"),
        Quote(74, "Strength does not come from physical capacity. It comes from indomitable will.", "Mahatma Gandhi", "Strength"),
        Quote(75, "You are never too old to set another goal or to dream a new dream.", "C.S. Lewis", "Dreams"),
        Quote(76, "To see what is right and not do it is a lack of courage.", "Confucius", "Courage"),
        Quote(77, "Reading is to the mind, as exercise is to the body.", "Brian Tracy", "Learning"),
        Quote(78, "A somebody was once a nobody who wanted to and did.", "John Burroughs", "Success"),
        Quote(79, "Innovation distinguishes between a leader and a follower.", "Steve Jobs", "Leadership"),
        Quote(80, "There are two types of people who will tell you that you cannot make a difference: those who are afraid to try and those who are afraid you will succeed.", "Ray Goforth", "Belief"),
        Quote(81, "Thinking should become your capital asset, no matter whatever ups and downs you come across in your life.", "A.P.J. Abdul Kalam", "Wisdom"),
        Quote(82, "I have learned over the years that when one's mind is made up, this diminishes fear.", "Rosa Parks", "Determination"),
        Quote(83, "I alone cannot change the world, but I can cast a stone across the water to create many ripples.", "Mother Teresa", "Impact"),
        Quote(84, "Nothing is impossible, the word itself says 'I'm possible'!", "Audrey Hepburn", "Possibility"),
        Quote(85, "The question isn't who is going to let me; it's who is going to stop me.", "Ayn Rand", "Determination"),
        Quote(86, "Every child is an artist. The problem is how to remain an artist once we grow up.", "Pablo Picasso", "Creativity"),
        Quote(87, "You can't use up creativity. The more you use, the more you have.", "Maya Angelou", "Creativity"),
        Quote(88, "The only person you are destined to become is the person you decide to be.", "Ralph Waldo Emerson", "Self"),
        Quote(89, "Go confidently in the direction of your dreams! Live the life you've imagined.", "Henry David Thoreau", "Dreams"),
        Quote(90, "When I stand before God at the end of my life, I would hope that I would not have a single bit of talent left, and could say, I used everything you gave me.", "Erma Bombeck", "Purpose"),
        Quote(91, "Few things can help an individual more than to place responsibility on him, and to let him know that you trust him.", "Booker T. Washington", "Responsibility"),
        Quote(92, "Certain things catch your eye, but pursue only those that capture the heart.", "Native American Proverb", "Passion"),
        Quote(93, "Believe and act as if it were impossible to fail.", "Charles Kettering", "Belief"),
        Quote(94, "Strive not to be a success, but rather to be of value.", "Albert Einstein", "Value"),
        Quote(95, "I am not a product of my circumstances. I am a product of my decisions.", "Stephen Covey", "Responsibility"),
        Quote(96, "When everything seems to be going against you, remember that the airplane takes off against the wind, not with it.", "Henry Ford", "Perseverance"),
        Quote(97, "The most common way people give up their power is by thinking they don't have any.", "Alice Walker", "Empowerment"),
        Quote(98, "The mind is everything. What you think you become.", "Buddha", "Mindset"),
        Quote(99, "The best revenge is massive success.", "Frank Sinatra", "Success"),
        Quote(100, "People often say that motivation doesn't last. Well, neither does bathing. That's why we recommend it daily.", "Zig Ziglar", "Motivation"),
        Quote(101, "Life shrinks or expands in proportion to one's courage.", "Anais Nin", "Courage"),
        Quote(102, "If you hear a voice within you say you cannot paint, then by all means paint and that voice will be silenced.", "Vincent Van Gogh", "Creativity"),
        Quote(103, "There is only one way to avoid criticism: do nothing, say nothing, and be nothing.", "Aristotle", "Courage"),
        Quote(104, "Ask and it will be given to you; search, and you will find; knock and the door will be opened for you.", "Bible", "Faith"),
        Quote(105, "The secret of happiness is not in doing what one likes, but in liking what one does.", "James M. Barrie", "Happiness")
    )

    val categories = listOf("All", "Success", "Happiness", "Life", "Love", "Courage", "Dreams", "Wisdom", "Action", "Resilience")

    fun getDailyQuote(context: Context): Quote {
        val prefs = getPrefs(context)
        val today = getTodayString()
        val lastDate = prefs.getString(KEY_LAST_DATE, "")

        return if (lastDate == today) {
            val id = prefs.getInt(KEY_DAILY_QUOTE_ID, 0)
            allQuotes.find { it.id == id } ?: selectNewDailyQuote(prefs, today)
        } else {
            selectNewDailyQuote(prefs, today)
        }
    }

    private fun selectNewDailyQuote(prefs: SharedPreferences, today: String): Quote {
        val quote = allQuotes.random()
        prefs.edit()
            .putString(KEY_LAST_DATE, today)
            .putInt(KEY_DAILY_QUOTE_ID, quote.id)
            .apply()
        return quote
    }

    fun getRandomQuote(): Quote = allQuotes.random()

    fun getQuotesByCategory(category: String): List<Quote> =
        if (category == "All") allQuotes else allQuotes.filter { it.category == category }

    fun getFavorites(context: Context): Set<Int> {
        val prefs = getPrefs(context)
        return prefs.getStringSet(KEY_FAVORITES, emptySet())
            ?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
    }

    fun toggleFavorite(context: Context, quoteId: Int): Boolean {
        val prefs = getPrefs(context)
        val favorites = getFavorites(context).toMutableSet()
        val isFav = if (favorites.contains(quoteId)) {
            favorites.remove(quoteId)
            false
        } else {
            favorites.add(quoteId)
            true
        }
        prefs.edit().putStringSet(KEY_FAVORITES, favorites.map { it.toString() }.toSet()).apply()
        return isFav
    }

    fun isFavorite(context: Context, quoteId: Int): Boolean = getFavorites(context).contains(quoteId)

    fun getFavoriteQuotes(context: Context): List<Quote> {
        val ids = getFavorites(context)
        return allQuotes.filter { ids.contains(it.id) }
    }

    fun getNotificationSettings(context: Context): Triple<Boolean, Int, Int> {
        val prefs = getPrefs(context)
        val enabled = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
        val hour = prefs.getInt(KEY_NOTIFICATION_HOUR, 8)
        val minute = prefs.getInt(KEY_NOTIFICATION_MINUTE, 0)
        return Triple(enabled, hour, minute)
    }

    fun saveNotificationSettings(context: Context, enabled: Boolean, hour: Int, minute: Int) {
        getPrefs(context).edit()
            .putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled)
            .putInt(KEY_NOTIFICATION_HOUR, hour)
            .putInt(KEY_NOTIFICATION_MINUTE, minute)
            .apply()
    }

    fun getTheme(context: Context): String {
        return getPrefs(context).getString(KEY_THEME, "auto") ?: "auto"
    }

    fun saveTheme(context: Context, theme: String) {
        getPrefs(context).edit().putString(KEY_THEME, theme).apply()
    }

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun getTodayString(): String {
        val cal = Calendar.getInstance()
        return "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
    }
}
