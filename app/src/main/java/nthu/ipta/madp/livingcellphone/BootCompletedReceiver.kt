package nthu.ipta.madp.livingcellphone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class BootCompletedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, ContinueService::class.java))
        } else {
            context!!.startService(Intent(context, ContinueService::class.java))
        }
    }
}