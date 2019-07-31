package com.jalinfotec.kankoannai.wikitude.geo

import android.app.Activity
import android.location.Location
import android.os.Bundle
import com.jalinfotec.kankoannai.R
import android.location.LocationListener
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.jalinfotec.kankoannai.Constants
import com.wikitude.architect.ArchitectStartupConfiguration
import com.wikitude.architect.ArchitectView
import kotlinx.android.synthetic.main.ar_campus.*
import java.io.IOException
import org.json.JSONObject
import org.jetbrains.anko.startActivity
import org.json.JSONException


class GeoActivity : Activity() {

    companion object {
        const val geoKey = "GEO"
    }

    private lateinit var geoName: String

    private var aView: ArchitectView? = null
    private var locationProvider: LocationProvider? = null
    private val logTag = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_campus)
        Log.i(logTag, "onCreate")

        geoName = if (savedInstanceState == null) intent.getStringExtra(geoKey) else savedInstanceState.getString(geoKey)


        aView = findViewById<ArchitectView>(R.id.archView)
        val config = ArchitectStartupConfiguration()
        config.licenseKey = Constants.WIKITUDE_SDK_KEY
        archView.onCreate(config)

        locationProvider = LocationProvider(this, object : LocationListener {
            override fun onLocationChanged(location: Location?) {

                if (location != null) {
                    // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                    if (location.hasAltitude() && location.hasAccuracy() && location.accuracy < 7) {

                        aView?.setLocation(
                            location.latitude,
                            location.longitude,
                            location.altitude,
                            location.accuracy
                        )
                    } else {
                        aView?.setLocation(
                            location.latitude,
                            location.longitude,
                            if(location.hasAccuracy()) location.accuracy.toDouble() else 1000.0
                        )
                    }
                }
            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
                Log.i(logTag, "onStatusChanged")
            }
            override fun onProviderEnabled(s: String) {
                Log.i(logTag, "onProviderEnabled")
            }
            override fun onProviderDisabled(s: String) {
                Log.i(logTag, "onProviderDisabled")
            }
        })

        archView.addArchitectJavaScriptInterfaceListener {
            try {
                startActivity<PoiDetailActivity>(
                    Pair(PoiDetailActivity.EXTRAS_KEY_POI_ID, it.getString("id")),
                    Pair(PoiDetailActivity.EXTRAS_KEY_POI_TITILE, it.getString("title")),
                    Pair(PoiDetailActivity.EXTRAS_KEY_POI_DESCR, it.getString("description"))
                )
            } catch (ex: JSONException) {
                Log.e("onJSONObjectReceived:", "$it", ex)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(geoKey, geoName)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.i(logTag, "onPostCreate")

        aView?.onPostCreate()
        try {
            aView?.load("geo/$geoName/index.html")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        Log.i(logTag, "onResume")

        aView?.onResume()
        // start location updates
        locationProvider?.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.i(logTag, "onPause")

        aView?.onPause()
        // stop location updates
        locationProvider?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(logTag, "onDestroy")

        aView?.onDestroy()
    }


}