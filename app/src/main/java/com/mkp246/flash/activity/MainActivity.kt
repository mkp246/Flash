package com.mkp246.flash.activity

import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mkp246.flash.R
import com.mkp246.flash.app.FlashApp

class MainActivity : AppCompatActivity() {
    private var cameraManager: CameraManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as FlashApp
        cameraManager = app.getCameraManager()
        setContentView(R.layout.activity_main)
        Toast.makeText(
            baseContext,
            "found camera: ${app.getState().cameraCount}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun toggleBackFlash(view: View) {
        val state = (application as FlashApp).getState()
        state.back = state.back.not()
        cameraManager?.setTorchMode(state.backId, state.back)
    }

    fun toggleFrontFlash(view: View) {
        val state = (application as FlashApp).getState()
        state.front = state.front.not()
        cameraManager?.setTorchMode(state.frontId, state.front)
    }
}