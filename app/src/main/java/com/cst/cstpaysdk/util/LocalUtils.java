package com.cst.cstpaysdk.util;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class LocalUtils {

    /**
     * 判断服务是否启动
     *
     * @param context   上下文
     * @param className 服务全路径
     * @return true-已经启动  false-未启动
     */
    public static boolean hasServiceStart(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (!(serviceList.size() > 0)) {
                return false;
            }
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).service.getClassName().equals(className)) {
                    isRunning = true;
                    break;
                }
            }
        }
        return isRunning;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取国际移动装备辨识码IMEI
     */
    public static String getImei(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String imei = null;
                try {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
                    imei = tm.getDeviceId();
                    return imei;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            return imei;
        }
        return null;
    }

    public static String getMacAddress() {
        return "45:55:78:14:86:78";
    }

    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            if (en_netInterface != null) {
                while (en_netInterface.hasMoreElements()) {//是否还有元素
                    NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                    Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                    while (en_ip.hasMoreElements()) {
                        ip = en_ip.nextElement();
                        if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                            break;
                        else
                            ip = null;
                    }
                    if (ip != null) {
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/eth0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            while (str != null) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.replaceAll("\\s*", "");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 判断应用任务栈是否还有Activity
     * @param context 一个context
     * @return boolean
     */
    public static boolean hasActivityRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<RunningTaskInfo> info = activityManager.getRunningTasks(Integer.MAX_VALUE);
            for (int i = 0; i < info.size(); i++) {
                ComponentName componentName = info.get(i).baseActivity;
                if (componentName != null) {
                    String packageName = componentName.getPackageName();
                    if (context.getPackageName().equals(packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @return boolean
     */
    public static boolean isAppAlive(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
            for (int i = 0; i < processInfos.size(); i++) {
                if (processInfos.get(i).processName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 强制关闭应用程序
     * @param context 一个context
     */
    public static void killAppProcess(Context context) {
        //注意：不能先杀掉主进程，否则逻辑代码无法继续执行，需先杀掉相关进程最后杀掉主进程
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> mList = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mList) {
                if (runningAppProcessInfo.pid != android.os.Process.myPid()) {
                    android.os.Process.killProcess(runningAppProcessInfo.pid);
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
