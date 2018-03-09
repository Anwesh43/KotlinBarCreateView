package ui.anwesome.com.barcreateview

/**
 * Created by anweshmishra on 10/03/18.
 */
import android.graphics.*
import android.content.*
import android.view.*
class BarCreateView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        return true
    }
    override fun onDraw(canvas : Canvas) {

    }
}