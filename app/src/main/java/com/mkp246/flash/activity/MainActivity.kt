package com.mkp246.flash.activity

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mkp246.flash.R

class MainActivity : AppCompatActivity() {
    private var cameraManager: CameraManager? = null
    private var backId: String? = null
    private var frontId: String? = null
    private var frontS: Boolean = false
    private var backS: Boolean = false
    private var flashTorchCallback: CameraManager.TorchCallback? = null

    override fun onDestroy() {
        super.onDestroy()
        cameraManager?.unregisterTorchCallback(flashTorchCallback!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraIdList: Array<String> = cameraManager?.cameraIdList!!
        backId = cameraIdList[0]
        frontId = cameraIdList[1]

        class FlashTorchCallback : CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                super.onTorchModeChanged(cameraId, enabled)
                when (cameraId) {
                    backId -> backS = enabled
                    frontId -> frontS = enabled
                }
            }
        }
        flashTorchCallback = FlashTorchCallback()
        cameraManager?.registerTorchCallback(flashTorchCallback!!, null)
        setContentView(R.layout.activity_main)
        Toast.makeText(baseContext, "found camera: ${cameraIdList.size}", Toast.LENGTH_SHORT).show()
    }

    fun toggleBackFlash(view: View) {
        backS = !backS
        cameraManager?.setTorchMode(backId!!, backS)
    }

    fun toggleFrontFlash(view: View) {
        frontS = !frontS
        cameraManager?.setTorchMode(frontId!!, frontS)
    }
}