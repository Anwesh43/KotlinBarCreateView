package ui.anwesome.com.kotlinbarcreateview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.barcreateview.BarCreateView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarCreateView.create(this)
    }
}
