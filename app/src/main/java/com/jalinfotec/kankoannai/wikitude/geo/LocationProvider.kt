package com.jalinfotec.kankoannai.wikitude.geo

import android.content.Context
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.support.v4.content.ContextCompat
import android.util.Log
import com.jalinfotec.kankoannai.Constants

class LocationProvider(
    private val context: Context,
    private val locationListener: LocationListener?
    ) {

    private val logTag = this::class.java.simpleName

    /** location listener called on each location update  */

    /** system's locationManager allowing access to GPS / Network position  */
    private val locationManager: LocationManager?
    /** location updates should fire approximately every second  */
    private val LOCATION_UPDATE_MIN_TIME_GPS = 1000
    /** location updates should fire, even if last signal is same than current one (0m distance to last location is OK)  */
    private val LOCATION_UPDATE_DISTANCE_GPS = 0
    /** location updates should fire approximately every second  */
    private val LOCATION_UPDATE_MIN_TIME_NW = 1000
    /** location updates should fire, even if last signal is same than current one (0m distance to last location is OK)  */
    private val LOCATION_UPDATE_DISTANCE_NW = 0
    /** to faster access location, even use 10 minute old locations on start-up  */
    private val LOCATION_OUTDATED_WHEN_OLDER_MS = 1000 * 60 * 10
    /** is gpsProvider and networkProvider enabled in system settings  */
    private var gpsProviderEnabled: Boolean = false
    private var networkProviderEnabled:Boolean = false

    init {
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        this.gpsProviderEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        this.networkProviderEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun onPause() {
        Log.i(logTag, "onPause")
        if (checkPermission()) {
            if (this.locationListener != null && this.locationManager != null && (this.gpsProviderEnabled || this.networkProviderEnabled)) {
                this.locationManager.removeUpdates(this.locationListener)
            }
        }
    }

    fun onResume() {
        Log.i(logTag, "onResume")
        if (checkPermission()) {
            if (this.locationManager != null && this.locationListener != null) {

                // check which providers are available are available
                this.gpsProviderEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                this.networkProviderEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                /** is GPS provider enabled?  */
                if (this.gpsProviderEnabled) {
                    val lastKnownGPSLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastKnownGPSLocation != null && lastKnownGPSLocation.time > System.currentTimeMillis() - LOCATION_OUTDATED_WHEN_OLDER_MS) {
                        locationListener.onLocationChanged(lastKnownGPSLocation)
                    }
                    if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
                        this.locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME_GPS.toLong(),
                            LOCATION_UPDATE_DISTANCE_GPS.toFloat(),
                            this.locationListener
                        )
                    }
                }

                /** is Network / WiFi positioning provider available?  */
                if (this.networkProviderEnabled) {
                    val lastKnownNWLocation = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (lastKnownNWLocation != null && lastKnownNWLocation.time > System.currentTimeMillis() - LOCATION_OUTDATED_WHEN_OLDER_MS) {
                        locationListener.onLocationChanged(lastKnownNWLocation)
                    }
                    if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
                        this.locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME_NW.toLong(),
                            LOCATION_UPDATE_DISTANCE_NW.toFloat(),
                            this.locationListener
                        )
                    }
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        for (p in Constants.permList) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                Log.i(logTag, "checkPermission: false")
                return false
            }
        }
        Log.i(logTag, "checkPermission: true")
        return true
    }
}