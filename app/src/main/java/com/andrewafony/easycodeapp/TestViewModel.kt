package com.andrewafony.easycodeapp


interface TimeTicker {

    fun start(callback: Callback, number: Int)

    fun stop()

    interface Callback {
        fun tick()
    }
}

class Timer: TimeTicker {

    private var callback: TimeTicker.Callback? = null

    override fun start(callback: TimeTicker.Callback, number: Int) {
        this.callback = callback
    }

    override fun stop() {
        callback?.tick()
    }
}