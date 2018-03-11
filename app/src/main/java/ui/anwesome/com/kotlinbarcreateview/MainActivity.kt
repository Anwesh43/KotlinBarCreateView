package ui.anwesome.com.kotlinbarcreateview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import ui.anwesome.com.barcreateview.BarCreateView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarCreateView.create(this)
        fullScreen()
    }
}
fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
