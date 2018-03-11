package ui.anwesome.com.barcreateview

/**
 * Created by anweshmishra on 10/03/18.
 */
import android.app.Activity
import android.graphics.*
import android.content.*
import android.view.*
class BarCreateView(ctx : Context, var text : String = "hello") : View(ctx) {
    val renderer = Renderer(this)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + jDir
                j += jDir
                if (j == scales.size || j == -1) {
                    j -= jDir
                    dir = 0f
                    jDir *= -1
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
    data class BarCreate(var i : Int, var text : String) {
        val state = State()
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val x = w/2 - (w/2)*state.scales[0]
            val w1 = w/2 + (w/2)*state.scales[0]
            val y_init = h/2 - h/100 - (h/30) * state.scales[1]
            val y = y_init - (y_init)*state.scales[2]
            val h1 = y + h/50 + ((h/15) * state.scales[1]) + (h - h/50 - h/15) * state.scales[3]
            canvas.drawRect(RectF(x, y, w1, h1), paint)
            val text_scale = state.scales[4]
            canvas.drawScaleRotateText(text, x, y, w, h, text_scale, paint)
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : BarCreateView, var time : Int = 0) {
        val animator : Animator = Animator(view)
        val barCreate : BarCreate = BarCreate(0, view.text)
        fun render(canvas : Canvas, paint : Paint) {
            if (time == 0) {
                paint.color = Color.parseColor("#3F51B5")
            }
            canvas.drawColor(Color.parseColor("#212121"))
            barCreate.draw(canvas, paint)
            time++
            animator.animate {
                barCreate.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            barCreate.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity) : BarCreateView {
            val view = BarCreateView(activity)
            activity.setContentView(view)
            return view
        }
    }
}
fun Canvas.drawScaleRotateText(text : String, x : Float, y: Float, w: Float, h : Float, text_scale : Float, paint : Paint) {
    save()
    translate(x + w/2, y+h/2)
    rotate(360f * text_scale)
    scale(text_scale, text_scale)
    paint.color = Color.argb((255 * text_scale).toInt(), 255, 255, 255)
    paint.textSize = Math.min(w,h)/30
    val tw = paint.measureText(text)
    drawText(text, -tw/2, paint.textSize/2, paint)
    restore()
}