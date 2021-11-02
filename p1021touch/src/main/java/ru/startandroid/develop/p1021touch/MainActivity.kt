package ru.startandroid.develop.p1021touch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

//MainActivity реализует интерфейс OnTouchListener для того, чтобы выступить обработчиком касаний.
class MainActivity : AppCompatActivity(), View.OnTouchListener {
    lateinit var textView: TextView
    var x: Float? = 0F
    var y: Float? = 0F
    lateinit var sDown: String
    lateinit var sMove: String
    lateinit var sUp: String

    /*
        В onCreate мы создаем новый TextView, сообщаем ему, что обработчиком касаний будет
            Activity, и помещаем на экран.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = TextView(this)
        textView.setOnTouchListener(this)
        setContentView(textView)
    }

    /*
        Интерфейс OnTouchListener предполагает, что Activity реализует его метод onTouch. На вход
            методу идет View для которого было событие касания и объект MotionEvent с информацией
            о событии. Методы getX и getY дают нам X и Y координаты касания. Метод getAction дает
            тип события касания.
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        x = event?.x
        y = event?.y

        when(event?.action) {
            //В случае ACTION_DOWN(нажатие) мы пишем в sDown координаты нажатия.
            MotionEvent.ACTION_DOWN -> {
                sDown = "Down: $x,$y"
                sMove = ""
                sUp = ""
            }
            /*
                В случае ACTION_MOVE(движение) пишем в sMove координаты точки текущего положения
                    пальца. Если мы будем перемещать палец по экрану – этот текст будет постоянно
                    меняться.
             */
            MotionEvent.ACTION_MOVE -> sMove = "Move: $x,$y"
            /*
                В случае ACTION_UP(опускание) или ACTION_CANCEL пишем в sUp координаты точки, в которой
                    отпустили палец.
             */
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                sMove = ""
                sUp = "Up: $x,$y"
            }
        }
        textView.text = """ $sDown
            | $sMove
            | $sUp
        """.trimMargin()
        return true
    }
}