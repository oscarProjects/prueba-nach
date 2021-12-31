package com.oscarvj.pruebanach.ui.view.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.oscarvj.pruebanach.R
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.ui.view.main.MainActivity
import com.oscarvj.pruebanach.ui.viewmodel.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var splashTimer: CountDownTimer? = null
    private val minute = 4L

    private val splashViewModel: SplashViewModel by viewModels()

    private var hasInternet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel.checkMovies()
    }

    override fun onResume() {
        super.onResume()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        splashTimer = object : CountDownTimer(minute * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(this::class.java.name, "millisUntilFinished $millisUntilFinished")
            }
            override fun onFinish() {
                if(validateNetwork()){
                    hasInternet = true
                    navigate()
                }else{
                    if(splashViewModel.countMovies == 0){
                        showMessage()
                    }else{
                        hasInternet = false
                        navigate()
                    }
                }
            }
        }.start()
    }

    private fun navigate(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.BUNDLE_INTERNET, hasInternet)
        intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    fun validateNetwork(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun showMessage(){
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(R.string.message))
            .setPositiveButton(getString(R.string.ok)) { view, _ ->
                view.dismiss()
                finish()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}