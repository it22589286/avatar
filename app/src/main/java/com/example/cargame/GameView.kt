package com.example.cargame

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View

class GameView(var c:Context,var gameTask: GameTask):View(c)
{
    private var myPaint:Paint?= null
    private  var speed =1
    private  var time =0
    private var score =0
    private var level =0
    private  var myninja =0
    private val fireballs = ArrayList<HashMap<String,Any>>()


    var viewWidth =0
    var viewHeight =0

    init{
        myPaint =Paint()
    }



    public fun resetGame() {
        time = 0
        score = 0
        level =1
        speed = 1
        myninja = 0
        fireballs.clear()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String,Any>()
            //dought
            map["lane"] = (0..2).random()
            map["startTime"] = time
            fireballs.add(map)
        }
        time = time + 10 + (speed)
        val ninjaWidth = viewWidth / 5
        val ninjaHeight = ninjaWidth + 10
        myPaint!!.style = Paint.Style.FILL

        val d = resources.getDrawable(R.drawable.ninja,null)

        d.setBounds(
            myninja * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight- 2 - ninjaHeight,
            myninja * viewWidth / 3 + viewWidth / 15+ninjaWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0
        var maxlevel = 0

        for (i in fireballs.indices) {
            try {
                val fireballX = fireballs[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var fireballY = time - fireballs[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.fireball,null)

                d2.setBounds(fireballX + 25, fireballY - ninjaHeight, fireballX+ ninjaWidth - 25, fireballY)

                d2.draw(canvas)
                if (fireballs[i]["lane"] as Int == myninja) {
                    if (fireballY > viewHeight - 2 - ninjaHeight && fireballY < viewHeight - 2) {
                        gameTask.closeGame(score,level)
                    }
                }
                if (fireballY > viewHeight + ninjaHeight) {
                    fireballs.removeAt(i)
                    score++
                    level = 1 + Math.abs(score/12)
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore) {
                        highScore = score
                    }
                    if(level > maxlevel){
                        maxlevel =level
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score:$score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed:$speed", 380f, 80f, myPaint!!)
        canvas.drawText("Level:$level", 680f, 80f, myPaint!!)
        invalidate()
    }
//
    override  fun onTouchEvent(event: MotionEvent?):Boolean{

        when(event!!.action){

            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 <viewWidth/2){
                    if(myninja >0){
                        myninja--
                    }
                }
                if(x1 > viewWidth/2){
                    if(myninja<2){
                        myninja++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{}


        }
        return true
    }
}
