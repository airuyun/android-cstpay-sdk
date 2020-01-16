package com.cst.cstpaysdk.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import com.cst.cstpaysdk.R
import com.cst.cstpaysdk.manager.CstPayManager
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener
import com.cst.cstpaysdk.util.LocalUtils
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * @author TJS
 * @date 2019/09/03 10:50
 * @cst_do 使用WebSocket与后台通讯
 * 影响范围：
 * 备注：
 * @cst_end
 */
class WebSocketService : Service(), IWebSocketView {

    private var mTimer1: Timer? = null
    private var mTimer2: Timer? = null
    private var mTimerTask1: TimerTask? = null
    private var mTimerTask2: TimerTask? = null
    private var mCstPayManager: CstPayManager? = null
    private var mTimeChangedReceiver: TimeChangedReceiver? = null
    private var mCstWebSocketListener: CstWebSocketListener? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mCstPayManager = CstPayManager(applicationContext)

        mTimeChangedReceiver = TimeChangedReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(Intent.ACTION_DATE_CHANGED)
        this.registerReceiver(mTimeChangedReceiver, filter)

        //监听应用程序是否已经关闭，如果已经关闭，则强制关闭应用程序的所有进出，否则后台服务会一直运行
        Thread(Runnable {
            while (true) {
                val isAppAlive: Boolean = LocalUtils.isAppAlive(applicationContext)
                val isActivityRunning: Boolean = LocalUtils.hasActivityRunning(applicationContext)
                if (isAppAlive && !isActivityRunning) {
                    CstPayManager(applicationContext).stopBeatService()
                    LocalUtils.killAppProcess(applicationContext)
                    break
                }
                Thread.sleep(1000)
            }
        }).start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerTask()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mTimeChangedReceiver != null) {
            this.unregisterReceiver(mTimeChangedReceiver)
        }
        mTimer1?.cancel()
        mTimer1 = null
        mTimer2?.cancel()
        mTimer2 = null
    }

    private fun timerTask() {
        mTimer1?.cancel()
        mTimer1 = null
        mTimer2?.cancel()
        mTimer2 = null

        //保持与服务器的心跳连接，心跳时间间隔为2分钟
        mTimerTask1 = object : TimerTask() {
            override fun run() {
                LogUtil.customLog(applicationContext, "心跳")
                if(mCstWebSocketListener != null) {
                    mCstWebSocketListener?.getWebSocket()?:let {
                        mCstPayManager?.webSocketConnect(this@WebSocketService)
                    }
                    mCstWebSocketListener?.getWebSocket()?.let {
                        mCstPayManager?.beatConnect(mCstWebSocketListener!!)
                    }
                } else {
                    mCstPayManager?.webSocketConnect(this@WebSocketService)
                }
            }
        }
        mTimer1 = Timer()
        mTimer1?.schedule(mTimerTask1, 0, (2 * 60 * 1000).toLong())

        //消费记录上报，每隔15分钟更新一次
        mTimerTask2 = object : TimerTask() {
            override fun run() {
                mCstPayManager?.uploadTradeRecord(null)
            }
        }
        mTimer2 = Timer()
        mTimer2?.schedule(mTimerTask2, 0, (15 * 60 * 1000).toLong())
    }

    override fun webSocketConnect(listener: CstWebSocketListener) {
        mCstWebSocketListener = listener
    }

    override fun addDispose(d: Disposable) {

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 通知渠道的id
            val id = "my_channel_01"
            // 用户可以看到的通知渠道的名字.
            val name = getString(R.string.app_name)
            //用户可以看到的通知渠道的描述
            val description = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            //配置通知渠道的属性
            mChannel.description = description
            //设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            //设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //最后在notificationmanager中创建该通知渠道 //
            notificationManager.createNotificationChannel(mChannel)
            // 为该通知设置一个id
            val notifyID = 1
            // 通知渠道的id
            val CHANNEL_ID = "my_channel_01"
            // Create a notification and set the notification channel.
            val notification = Notification.Builder(this)
                .setContentTitle("New Message").setContentText("You've received new messages.")
                .setSmallIcon(R.mipmap.ic_logo)
                .setChannelId(CHANNEL_ID)
                .build()
            startForeground(1, notification)
        }
    }

    internal inner class TimeChangedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (Intent.ACTION_TIME_CHANGED == action) {
                LogUtil.customLog(applicationContext, "系统时间被修改了!")
                timerTask()
            }
        }
    }
}