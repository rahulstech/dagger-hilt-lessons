package rahulstech.android.daggerhiltdemo.viewmodel

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

private val QUOTES = listOf(
    "The only way to do great work is to love what you do.",
    "Strive not to be a success, but rather to be of value.",
    "The mind is everything. What you think you become.",
    "Eighty percent of success is showing up.",
    "Your time is limited, don't waste it living someone else's life.",
    "The best time to plant a tree was 20 years ago. The second best time is now.",
    "An unexamined life is not worth living.",
    "The journey of a thousand miles begins with a single step.",
    "I have not failed. I've just found 10,000 ways that won't work.",
    "That which does not kill us makes us stronger.",
    "If you are going through hell, keep going.",
    "The only true wisdom is in knowing you know nothing.",
    "You must be the change you wish to see in the world.",
    "Do what you can, with what you have, where you are.",
    "It is never too late to be what you might have been.",
    "Happiness is not something readymade. It comes from your own actions.",
    "Where there is a will, there is a way.",
    "Fall seven times, stand up eight.",
    "Our greatest glory is not in never falling, but in rising every time we fall.",
    "The future belongs to those who believe in the beauty of their dreams."
)

@Singleton // though QuotesRepository is an concrete class yet add @Singleton ensures that only one instance will be used app wide
class QuotesRepository @Inject constructor() {

    init {
        Log.i("QuotesRepository","init Repository")
    }

    private val random: Random = Random(20)

    fun getRandomQuote(): String = QUOTES[random.nextInt(QUOTES.size)]
}