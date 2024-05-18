package com.example.cargame

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GameTask {

    // Add a MediaPlayer property for the game music
    private lateinit var gameMusic: MediaPlayer

    private val HIGH_SCORE_KEY = "high_score"
    private val MAX_LEVEL_KEY ="max_level"
    private lateinit var highScoreDisplay: TextView
    private lateinit var maxlevelDisplay: TextView
    lateinit var  rootLayout:LinearLayout
    lateinit var startBtn:Button
    lateinit var resetButton:Button
    lateinit var mGameView:GameView
    lateinit var score:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreDisplay = findViewById(R.id.highscore)
        maxlevelDisplay =findViewById(R.id.maxLevel)
        mGameView = GameView(this,this)

        // Initialize MediaPlayer with the game music
        gameMusic = MediaPlayer.create(this, R.raw.sing)

         resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetScores()
        }

        startBtn.setOnClickListener {
            // Start playing the game music when startBtn is clicked
            gameMusic.start()
            mGameView.setBackgroundResource(R.drawable.bg5)
            mGameView.resetGame()
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            highScoreDisplay.visibility = View.GONE
            maxlevelDisplay.visibility = View.GONE
            resetButton.visibility = View.GONE
        }

        // Retrieve high score and display it
        val sharedPrefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScore = sharedPrefs.getInt(HIGH_SCORE_KEY, 0)
        val maxLevel = sharedPrefs.getInt(MAX_LEVEL_KEY,0)
       // score.text = "High Score: $highScore"
        highScoreDisplay.text = "High Score: $highScore"
        maxlevelDisplay.text = "Max Level:$maxLevel"

    }

    private fun resetScores() {
        val sharedPrefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            remove(HIGH_SCORE_KEY)
            remove(MAX_LEVEL_KEY)
            apply()
        }
        highScoreDisplay.text = "High Score: 0"
        maxlevelDisplay.text = "Max Level: 0"
    }

    override fun closeGame(mScore: Int,mLevel:Int) {
        score.text = "Score:$mScore"

        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        resetButton.visibility = View.VISIBLE




        // Stop the game music when the game ends
        gameMusic.stop()
        gameMusic.release()
        gameMusic = MediaPlayer.create(this, R.raw.sing)

        val sharedPrefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScore = sharedPrefs.getInt(HIGH_SCORE_KEY, 0)
        val maxLevel = sharedPrefs.getInt(MAX_LEVEL_KEY,0)

        //Update high socre if necessary
        if (mScore > highScore) {
            with(sharedPrefs.edit()) {
                putInt(HIGH_SCORE_KEY, mScore)
                apply()
            }
            //score.text = "High Score: $mScore"
            highScoreDisplay.text = "High Score: $mScore"
        }
        if(mLevel > maxLevel) {
            with(sharedPrefs.edit()) {
                putInt(MAX_LEVEL_KEY, mLevel)
                apply()

            }

            maxlevelDisplay.text ="Max Levle:$mLevel"

        }

        // Show high score after the game ends
        highScoreDisplay.visibility = View.VISIBLE
        maxlevelDisplay.visibility= View.VISIBLE
    }
}