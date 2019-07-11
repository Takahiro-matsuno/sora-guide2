package com.jalinfotec.kankoannai.wikitude

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import com.jalinfotec.kankoannai.wikitude.util.SampleCategory
import com.wikitude.WikitudeSDK
import com.wikitude.common.devicesupport.Feature
import com.wikitude.common.permission.PermissionManager
import java.util.*
import com.jalinfotec.kankoannai.R
import com.jalinfotec.kankoannai.wikitude.camera.CameraSettingsActivity
import com.jalinfotec.kankoannai.wikitude.plugins.BarcodePluginActivity
import com.jalinfotec.kankoannai.wikitude.plugins.CustomCameraPluginActivity
import com.jalinfotec.kankoannai.wikitude.plugins.FaceDetectionPluginActivity
import com.jalinfotec.kankoannai.wikitude.plugins.SimpleInputPluginActivity
import com.jalinfotec.kankoannai.wikitude.rendering.external.ExternalRenderingActivity
import com.jalinfotec.kankoannai.wikitude.rendering.internal.InternalRenderingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.`object`.ExtendedObjectTrackingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.`object`.ObjectTrackingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.cloud.ContinuousCloudRecognitionActivity
import com.jalinfotec.kankoannai.wikitude.tracking.cloud.SingleCloudRecognitionActivity
import com.jalinfotec.kankoannai.wikitude.tracking.image.ExtendedImageTrackingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.image.MultipleTargetsImageTrackingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.image.SimpleImageTrackingActivity
import com.jalinfotec.kankoannai.wikitude.tracking.instant.*
import com.jalinfotec.kankoannai.wikitude.util.SampleData
import com.jalinfotec.kankoannai.wikitude.util.adapter.SamplesExpendableListAdapter
import kotlin.collections.ArrayList

class WikitudeMainActivity : AppCompatActivity(), ExpandableListView.OnChildClickListener {

    private var listView: ExpandableListView? = null

    private val sampleCategories = ArrayList<SampleCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wikitude_main)

        WikitudeSDK.deleteRootCacheDirectory(this)

        sampleCategories.add(
            SampleCategory(
                getString(R.string.image_tracking), getSampleDataListFromNames(
                    resources.getStringArray(R.array.imageTracking_samples),
                    EnumSet.of(Feature.IMAGE_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.cloud_recognition), getSampleDataListFromNames(
                    resources.getStringArray(R.array.cloudRecognition_samples),
                    EnumSet.of(Feature.IMAGE_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.instant_tracking), getSampleDataListFromNames(
                    resources.getStringArray(R.array.instantTracking_samples),
                    EnumSet.of(Feature.INSTANT_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.object_tracking), getSampleDataListFromNames(
                    resources.getStringArray(R.array.objectTracking_samples),
                    EnumSet.of(Feature.OBJECT_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.rendering), getSampleDataListFromNames(
                    resources.getStringArray(R.array.rendering_samples),
                    EnumSet.of(Feature.IMAGE_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.plugins), getSampleDataListFromNames(
                    resources.getStringArray(R.array.plugins_samples),
                    EnumSet.of(Feature.IMAGE_TRACKING)
                )
            )
        )
        sampleCategories.add(
            SampleCategory(
                getString(R.string.camera_control), getSampleDataListFromNames(
                    resources.getStringArray(R.array.cameraControl_samples),
                    EnumSet.of(Feature.IMAGE_TRACKING)
                )
            )
        )

        val adapter = SamplesExpendableListAdapter(this@WikitudeMainActivity, sampleCategories)

        listView = findViewById(R.id.listView)
        moveExpandableIndicatorToRight()
        listView!!.setOnChildClickListener(this)
        listView!!.setAdapter(adapter)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setOnLongClickListener{
            showSdkBuildInformation()
            false
        }
        setSupportActionBar(toolbar)
    }

    override fun onChildClick(
        expandableListView: ExpandableListView,
        view: View,
        groupPosition: Int,
        childPosition: Int,
        id: Long
    ): Boolean {
        val sampleData = sampleCategories[groupPosition].samples[childPosition]

        if (!sampleData.isDeviceSupporting) {
            showDeviceMissingFeatures(sampleData.isDeviceSupportingError)
        } else {
            WikitudeSDK.getPermissionManager().checkPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PermissionManager.WIKITUDE_PERMISSION_REQUEST,
                object :
                    PermissionManager.PermissionManagerCallback {
                    override fun permissionsGranted(requestCode: Int) {
                        var activity: Class<*> = SimpleImageTrackingActivity::class.java
                        val sampleCategory = sampleCategories[groupPosition]
                        val categoryName = sampleCategory.name
                        val sampleName = sampleCategory.samples[childPosition].name
                        when (categoryName) {
                            "Image Tracking" -> when (sampleName) {
                                "Simple" -> activity = SimpleImageTrackingActivity::class.java
                                "Multiple Targets" -> activity = MultipleTargetsImageTrackingActivity::class.java
                                "Extended Tracking" -> activity = ExtendedImageTrackingActivity::class.java
                            }
                            "Cloud Recognition" -> when (sampleName) {
                                "Single Recognition" -> activity = SingleCloudRecognitionActivity::class.java
                                "Continuous Recognition" -> activity = ContinuousCloudRecognitionActivity::class.java
                            }
                            "Instant Tracking" -> when (sampleName) {
                                "Simple" -> activity = InstantTrackingActivity::class.java
                                "Scene Picking" -> activity = InstantScenePickingActivity::class.java
                                "Save Instant Target" -> activity = SaveInstantTargetActivity::class.java
                                "Load Instant Target" -> activity = LoadInstantTargetActivity::class.java
                                "Plane Detection" -> activity = PlaneDetectionActivity::class.java
                            }
                            "Object Tracking" -> when (sampleName) {
                                "Simple" -> activity = ObjectTrackingActivity::class.java
                                "Extended Tracking" -> activity = ExtendedObjectTrackingActivity::class.java
                            }
                            "Rendering" -> when (sampleName) {
                                "External OpenGL ES Rendering" -> activity = ExternalRenderingActivity::class.java
                                "Internal OpenGL ES Rendering" -> activity = InternalRenderingActivity::class.java
                            }
                            "Plugins" -> when (sampleName) {
                                "QR & Barcode" -> activity = BarcodePluginActivity::class.java
                                "Face Detection" -> activity = FaceDetectionPluginActivity::class.java
                                "Simple Custom Camera " -> activity = SimpleInputPluginActivity::class.java
                                "Advanced Custom Camera" -> activity = CustomCameraPluginActivity::class.java
                            }
                            "Camera Control" -> when (sampleName) {
                                "Camera Settings" -> activity = CameraSettingsActivity::class.java
                            }
                        }

                        val intent = Intent(this@WikitudeMainActivity, activity)
                        startActivity(intent)
                    }

                    override fun permissionsDenied(deniedPermissions: Array<String>) {
                        Toast.makeText(
                            this@WikitudeMainActivity,
                            "The Wikitude SDK needs the following permissions to enable an AR experience: " + Arrays.toString(
                                deniedPermissions
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun showPermissionRationale(requestCode: Int, permissions: Array<String>) {
                        val alertBuilder = AlertDialog.Builder(this@WikitudeMainActivity)
                        alertBuilder.setCancelable(true)
                        alertBuilder.setTitle("Wikitude Permissions")
                        alertBuilder.setMessage(
                            "The Wikitude SDK needs the following permissions to enable an AR experience: " + Arrays.toString(
                                permissions
                            )
                        )
                        alertBuilder.setPositiveButton(
                            android.R.string.yes
                        ) { _, _ ->
                            WikitudeSDK.getPermissionManager().positiveRationaleResult(requestCode, permissions)
                        }

                        val alert = alertBuilder.create()
                        alert.show()
                    }
                })
        }

        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        WikitudeSDK.getPermissionManager().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun moveExpandableIndicatorToRight() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
        listView!!.setIndicatorBoundsRelative(
            width - dpToPx(EXPANDABLE_INDICATOR_START_OFFSET),
            width - dpToPx(EXPANDABLE_INDICATOR_END_OFFSET)
        )
        listView!!.setIndicatorBoundsRelative(
            width - dpToPx(EXPANDABLE_INDICATOR_START_OFFSET),
            width - dpToPx(EXPANDABLE_INDICATOR_END_OFFSET)
        )
    }

    private fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun getSampleDataListFromNames(arrayNames: Array<String>, features: EnumSet<Feature>): List<SampleData> {
        val sampleDataImageTracking = ArrayList<SampleData>()
        for (arrayName in arrayNames) {
            val isDeviceSupported: Boolean
            val isDeviceSupportedMessage: String

            val callStatus = WikitudeSDK.isDeviceSupporting(this, features)
            if (callStatus.isSuccess) {
                isDeviceSupported = true
                isDeviceSupportedMessage = ""
            } else {
                isDeviceSupported = false
                isDeviceSupportedMessage = callStatus.error.message
            }

            val item = SampleData(arrayName, isDeviceSupported, isDeviceSupportedMessage)
            sampleDataImageTracking.add(item)
        }

        return sampleDataImageTracking
    }

    private fun showSdkBuildInformation() {
        val sdkBuildInformation = WikitudeSDK.getSDKBuildInformation()
        AlertDialog.Builder(this)
            .setTitle(R.string.build_information_title)
            .setMessage(
                getString(R.string.build_information_config) + sdkBuildInformation.buildConfiguration + "\n" +
                        getString(R.string.build_information_date) + sdkBuildInformation.buildDate + "\n" +
                        getString(R.string.build_information_number) + sdkBuildInformation.buildNumber + "\n" +
                        getString(R.string.build_information_version) + WikitudeSDK.getSDKVersion()
            )
            .show()
    }

    private fun showDeviceMissingFeatures(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.device_missing_features)
            .setMessage(errorMessage)
            .show()
    }

    companion object {

        private const val EXPANDABLE_INDICATOR_START_OFFSET = 60
        private const val EXPANDABLE_INDICATOR_END_OFFSET = 30
    }
}
