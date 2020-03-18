package com.hydroponics_garden

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep
import java.net.Socket
import java.net.SocketException
import java.util.*
import kotlin.math.round
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var progressAnimation = ObjectAnimator.ofInt(monthProgress,"progress",0,733)
        progressAnimation.duration = 2000
        progressAnimation.startDelay = 300
        progressAnimation.interpolator = AccelerateDecelerateInterpolator();
        progressAnimation.start()
        thread(start = true){
            while(true){
                try{
                    var s = Socket("192.168.4.1",2025)
                    s.getOutputStream().write(1)
                    var phbyte = s.getInputStream().read()
                    var ph = phbyte/256.0f*14.0f;

                    s.close();
                    runOnUiThread {
                        pHLevelOutput.text = ((ph*100).toInt()/100.0f).toString()
                    }
                }catch(se: SocketException){

                }

                sleep(200)
            }

        }

        temperatureOutput.text = "70"
        waterLevelProgressBar.progress = 90
        nextWaterChangeProgressBar.progress = 75

        goToMoreInformation.setOnClickListener {
            startActivity(Intent(this, MoreInformationActivity::class.java))
        }



    }//end of onCreate

}//end of class
