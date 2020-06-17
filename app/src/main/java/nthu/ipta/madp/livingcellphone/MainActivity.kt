package nthu.ipta.madp.livingcellphone


import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.os.BatteryManager
import android.os.Build
import android.widget.Button
import nthu.ipta.madp.livingcellphone.MainActivity.Mp_charging.mp_charging2
import nthu.ipta.madp.livingcellphone.MainActivity.Mp_powerlevel.mp_powerHigh_2
import nthu.ipta.madp.livingcellphone.MainActivity.Mp_powerlevel.mp_powerLow_2
import nthu.ipta.madp.livingcellphone.MainActivity.Mp_powerlevel.mp_powerMid_2


class MainActivity : AppCompatActivity() {

    private var mp_shake2: MediaPlayer? = null
    private var mp_volumeUpKey2: MediaPlayer? = null
    private var mp_volumeDownKey2: MediaPlayer? = null

    private var context: Context? = null

//    var receiver_charging:PowerConnectionReceiver?=null
//    var intentFilter_charging: IntentFilter? = null
//
//    var receiver_batterylevel:BatteryLevelReceiver?=null
//    var intentFilter_batterylevel: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //////////sound///////////
        mp_shake2 = MediaPlayer.create(this,R.raw.screech2)
        mp_volumeUpKey2 = MediaPlayer.create(this,R.raw.up2)
        mp_volumeDownKey2 = MediaPlayer.create(this,R.raw.down2)
        mp_charging2 = MediaPlayer.create(this,R.raw.groove)
        mp_powerHigh_2 = MediaPlayer.create(this,R.raw.charging_high2)
        mp_powerMid_2 = MediaPlayer.create(this,R.raw.charging_mid2)
        mp_powerLow_2 = MediaPlayer.create(this,R.raw.charging_low2)

        context = getApplicationContext()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context,  ContinueService::class.java))
        } else {
            context!!.startService(Intent(context,  ContinueService::class.java))
        }

    }

    fun playOrPauseMusic(): String {
        if (mp_shake2!!.isPlaying) {
            mp_shake2?.pause()
            return "Play"
        } else {
            mp_shake2?.start()
            return "Pause"
        }
    }

    object Mp_powerlevel {
        var  mp_powerHigh_2 : MediaPlayer? = null
        var  mp_powerMid_2: MediaPlayer? = null
        var  mp_powerLow_2: MediaPlayer? = null
    }
    object Mp_charging {
        var mp_charging2 : MediaPlayer? = null

    }

}
