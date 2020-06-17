package nthu.ipta.madp.livingcellphone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class ContinueService : Service() {

    val TAG="BetteryLevelService"

    private var mp_shake2: MediaPlayer? = null
    var receiver_charging:PowerConnectionReceiver?=null
    var intentFilter_charging: IntentFilter? = null
    var receiver_batterylevel:BatteryLevelReceiver?=null
    var intentFilter_batterylevel: IntentFilter? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification =
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build()
            startForeground(1, notification)
        }
        BatteryLevelReceiver()
        Log.i(TAG,"================= BatteryServiceReceiver===============")

        /////////BatteryConnect//////
        MainActivity.Mp_charging.mp_charging2 = MediaPlayer.create(this,R.raw.groove)
        receiver_charging =PowerConnectionReceiver()
        intentFilter_charging = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver_charging,intentFilter_charging)


        ///////////PowerLevel//////////
        MainActivity.Mp_powerlevel.mp_powerHigh_2 = MediaPlayer.create(this,R.raw.charging_high2)
        MainActivity.Mp_powerlevel.mp_powerMid_2 = MediaPlayer.create(this,R.raw.charging_mid2)
        MainActivity.Mp_powerlevel.mp_powerLow_2 = MediaPlayer.create(this,R.raw.charging_low2)
        receiver_batterylevel = BatteryLevelReceiver()
        intentFilter_batterylevel = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver_batterylevel,intentFilter_batterylevel)


        //////////Shake///////////
        mp_shake2 = MediaPlayer.create(this,R.raw.screech2)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(shakeSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }


    private val shakeSensorListener = object: SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                val xValue = Math.abs(event.values[0]) // 加速度 - X 軸方向
                val yValue = Math.abs(event.values[1]) // 加速度 - Y 軸方向
                val zValue = Math.abs(event.values[2]) // 加速度 - Z 軸方向
                if (xValue > 20 || yValue > 20 || zValue > 20) {
                    mp_shake2?.start()
                }
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.i(TAG,"=================BetteryLevelService==============")
        return START_STICKY
    }


}