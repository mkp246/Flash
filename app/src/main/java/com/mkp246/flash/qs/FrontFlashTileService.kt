package com.mkp246.flash.qs

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.mkp246.flash.app.FlashApp

class FrontFlashTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val app = application as FlashApp
        val state = app.getState()
        state.front = state.front.not()
        app.getCameraManager()?.setTorchMode(state.frontId, state.front)
        if (state.front) {
            qsTile.state = Tile.STATE_ACTIVE
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        val tile: Tile = qsTile
        val state = (application as FlashApp).getState()
        var shouldUpdate = false
        when (tile.state) {
            Tile.STATE_ACTIVE -> {
                if (state.front.not()) {
                    tile.state = Tile.STATE_INACTIVE
                    shouldUpdate = true
                }
            }
            Tile.STATE_INACTIVE -> {
                if (state.front) {
                    tile.state = Tile.STATE_ACTIVE
                    shouldUpdate = true
                }
            }
        }
        if (shouldUpdate) tile.updateTile()
    }
}