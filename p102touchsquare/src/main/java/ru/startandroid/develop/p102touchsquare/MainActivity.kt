package ru.startandroid.develop.p102touchsquare

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyView(this))
    }

    class MyView(context: Context) : View(context) {
        val p = Paint()
        //координаты для рисования квадрата
        var myX = 100F
        var myY = 100F
        val side = 200
        //переменные для перетаскивания
        var drag = false
        var dragX = 0F
        var dragY = 0F

        init {
            p.color = Color.GREEN
        }

        override fun onDraw(canvas: Canvas?) {
            //рисуем квадрат
            super.onDraw(canvas)
            canvas?.drawRect(myX, myY, myX + side, myY + side, p)
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            if (event != null) {
                //координаты touch-события
                val evX = event.x
                val evY = event.y

                when(event.action) {
                    //касание началось
                    MotionEvent.ACTION_DOWN -> {
                        //если касание в пределах квадрата
                        if (evX in myX..(myX + side) && evY in myY..(myY + side)) {
                            //включаем режим перетаскивания
                            drag = true
                            //разница между верхним левым углом квадрата и точкой касания
                            dragX = evX - myX
                            dragY = evY - myY
                        }
                    }

                    //тащим
                    MotionEvent.ACTION_MOVE -> {
                        //если режим перетаскивания включен
                        if (drag) {
                            //определяем новые координаты для рисования
                            myX = evX - dragX
                            myY = evY - dragY
                            //перерисовываем экран
                            invalidate()
                        }
                    }

                    //касание завершено
                    MotionEvent.ACTION_UP -> {
                        //выключаем режим перетаскивания
                        drag = false
                    }
                }
            }
            return true
        }
    }
}