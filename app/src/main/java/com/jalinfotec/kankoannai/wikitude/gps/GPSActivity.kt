package com.jalinfotec.kankoannai.wikitude.gps

import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jalinfotec.kankoannai.R
import android.location.LocationListener
import com.jalinfotec.kankoannai.Constants
import com.wikitude.architect.ArchitectStartupConfiguration
import kotlinx.android.synthetic.main.ar_campus.*

import java.io.IOException

class GPSActivity : AppCompatActivity() {


    private var locationProvider: LocationProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ar_campus)
        val config = ArchitectStartupConfiguration()
        config.licenseKey = Constants.WIKITUDE_SDK_KEY
        architectView.onCreate(config)

        locationProvider = LocationProvider(this, object : LocationListener {
            override fun onLocationChanged(location: Location?) {

                if (location != null) {
                    // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                    if (location.hasAltitude() && location.hasAccuracy() && location.accuracy < 7) {

                        architectView.setLocation(
                            location.latitude,
                            location.longitude,
                            location.altitude,
                            location.accuracy
                        )
                    } else {
                        architectView.setLocation(
                            location.latitude,
                            location.longitude,
                            if(location.hasAccuracy()) location.accuracy.toDouble() else 1000.0
                        )
                    }
                }
            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
            override fun onProviderEnabled(s: String) {}
            override fun onProviderDisabled(s: String) {}
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        architectView.onPostCreate()
        try {
            architectView.load("gps.html")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        architectView.onResume()
        // start location updates
        locationProvider?.onResume()
    }

    override fun onPause() {
        super.onPause()
        architectView.onPause()
        // stop location updates
        locationProvider?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        architectView.onDestroy()
    }
}
