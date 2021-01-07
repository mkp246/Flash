package com.mkp246.flash.app

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraManager
import com.mkp246.flash.data.FlashState

class FlashApp : Application() {
    private val state: FlashState = FlashState()
    private var cameraManager: CameraManager? = null
    private var flashTorchCallback: CameraManager.TorchCallback? = null

    override fun onCreate() {
        super.onCreate()
        class FlashTorchCallback : CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                super.onTorchModeChanged(cameraId, enabled)
                when (cameraId) {
                    state.backId -> state.back = enabled
                    state.frontId -> state.front = enabled
                }
            }
        }
        flashTorchCallback = FlashTorchCallback()
        cameraManager?.registerTorchCallback(flashTorchCallback!!, null)
        if (cameraManager == null) {
            cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        }
        val cameraIdList: Array<String> = cameraManager?.cameraIdList!!
        state.backId = cameraIdList[0]
        state.frontId = cameraIdList[1]
    }

    override fun onTerminate() {
        super.onTerminate()
        cameraManager?.unregisterTorchCallback(flashTorchCallback!!)
    }

    fun getState(): FlashState {
        return state
    }

    fun getCameraManager(): CameraManager? {
        return cameraManager
    }
}