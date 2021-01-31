package my.farhan.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.farhan.movie.R
import org.koin.android.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}