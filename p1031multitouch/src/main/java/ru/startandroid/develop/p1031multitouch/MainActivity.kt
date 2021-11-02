package ru.startandroid.develop.p1031multitouch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnTouchListener {
    val sb = StringBuilder()
    lateinit var textView: TextView
    var upPI = 0
    var downPI = 0
    var inTouch = false
    var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = TextView(this).apply {
            textSize = 20F
            setOnTouchListener(this@MainActivity)
            setContentView(this)
        }
    }

    /*
        Если для одного касания мы использовали метод getAction, чтобы понять какое событие
            произошло, то с мультитачем надо использовать getActionMasked. Индекс касания
            определяется методом getActionIndex. Кол-во текущих касаний – getPointerCount.
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            //событие
            val actionMask = event.actionMasked
            //индекс касания
            val pointerIndex = event.actionIndex
            //число касаний
            val pointerCount = event.pointerCount

            when(actionMask) {
                /*
                    Если событие - ACTION_DOWN, значит мы получили первое касание. Ставим метку
                        inTouch = true. Она для нас будет означать, что есть касания. Если событие
                        ACTION_POINTER_DOWN (или ACTION_DOWN), то в переменную downPI помещаем
                        индекс касания. Это будет индекс последнего прикоснувшегося пальца.
                 */
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    inTouch = true
                    downPI = pointerIndex
                }

                /*
                    Если событие - ACTION_UP, значит последнее касание прервано и экрана больше
                        ничего не касается. Ставим inTouch = false, т.е. отсутствие касаний.
                        Очищаем StringBuilder, который содержит информацию о движениях.
                        Если событие - ACTION_POINTER_UP (или ACTION_UP), то в переменную upPI
                        помещаем  индекс касания. Это будет индекс последнего прерванного касания.
                        Т.е. когда мы одно за другим прерываем касания, эта переменная будет
                        содержать один за другим индексы последнего из прерванных.
                 */
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    inTouch = false
                    sb.setLength(0)
                    upPI = pointerIndex
                }

                /*
                    Если событие ACTION_MOVE – мы перебираем все существующие индексы. С помощью
                        pointerCount определяем, какие из них сейчас задействованы и содержат
                        информацию о касаниях. Для них мы пишем номер индекса, ID (метод
                        getPointerId) и координаты (getX и getY). Для незадействованных пишем
                        только номер индекса. Пишем мы это все в StringBuilder.
                 */
                MotionEvent.ACTION_MOVE -> {
                    sb.setLength(0)

                    for (i in 0 until 10) {
                        sb.apply {
                            append("Index = $i")
                            if (i < pointerCount) {
                                append(", ID = ${event.getPointerId(i)}")
                                append(", X = ${event.x}")
                                append(", Y = ${event.y}")
                            } else {
                                append(", ID = ")
                                append(", X = ")
                                append(", Y = ")
                            }
                            append("\r\n")
                        }
                    }
                }
            }
            /*
                Далее при любом событии формируем result, пишем туда индекс последнего касания и
                    последнего завершенного касания. Если в данный момент есть касание (inTouch),
                    то добавляем в результат содержимое StringBuilder с подробной инфой о всех
                    касаниях. И выводим result в TextView.
             */
            result = "down: $downPI\nup: $upPI\n"
            if (inTouch) {
                result += "pointerCount = $pointerCount\n$sb"
            }
            textView.text = result
        }
        return true
    }
}