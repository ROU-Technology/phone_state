package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING;
    var number: String? = null
    var state: Any? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val newState = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
            status = when (intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                TelephonyManager.EXTRA_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
                TelephonyManager.EXTRA_STATE_OFFHOOK -> PhoneStateStatus.CALL_STARTED
                TelephonyManager.EXTRA_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
                else -> PhoneStateStatus.NOTHING
            }
            if (newState != state) {
                state = newState
                number = intent?.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}