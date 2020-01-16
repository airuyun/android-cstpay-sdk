package com.cst.cstpaysdk.mvp.init.model.impl

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.InitInfoBean
import com.cst.cstpaysdk.mvp.init.model.IInitModel
import com.cst.cstpaysdk.service.LogService
import com.cst.cstpaysdk.util.ConstantUtils
import com.cst.cstpaysdk.util.FileUtil
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable

class InitModelImpl : IInitModel {

    private val mSpFileName = "sp_cst_pay_sdk"
    private val mLaunchAppFirstTime = "launch_app_first_time"

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    override fun init(context: Context, initInfo: InitInfoBean?): Observable<InitInfoBean> {
        return Observable.create {
            //开启日志抓取服务
            context.startService(Intent(context, LogService::class.java))

            val data: String = JSON.toJSONString(initInfo)
            LogUtil.customLog(context, "初始化配置文件请求参数 = $data")
            if (initInfo == null) {
                it.onError(Throwable("数据为空"))
                return@create
            }
            if (initInfo.serverIp == null) {
                it.onError(Throwable("服务IP为空"))
                return@create
            }
            if (initInfo.serverPort == null) {
                it.onError(Throwable("服务端口为空"))
                return@create
            }
            if (initInfo.webSocketIp == null) {
                it.onError(Throwable("服务WEB SOCKET IP为空"))
                return@create
            }
            if (initInfo.webSocketPort == null) {
                it.onError(Throwable("服务WEB SOCKET 端口为空"))
                return@create
            }

            val sp: SharedPreferences = context.getSharedPreferences(mSpFileName, Context.MODE_PRIVATE)
            val b: Boolean = sp.getBoolean(mLaunchAppFirstTime, true)
            //如果是首次打开APP，则清空旧APP保存的数据
            if(b) {
                FileUtil.deleteAllFile("${FileUtil.getPATH()}/${context.packageName}/")
                sp.edit().putBoolean(mLaunchAppFirstTime, false).apply()
            }

            //判断是否需要覆盖初始化配置文件，可以用于紧急情况下手动修改IP和端口
            //配置文件参数来自于 SharedPreferences，build.gradle，获取配置文件
            //参数的优先级 SharedPreferences > build.gradle。
            val initInfoBean: InitInfoBean? = ConstantUtils.getInitInfo(context)
            val isUpdate: Boolean? = initInfoBean?.isUpdate
            if(isUpdate == null || isUpdate) {
                val filePath = "${FileUtil.getPATH()}/${context.packageName}/data/initinfo.txt"
                FileUtil.writeFile(data, filePath)
                LogUtil.customLog(context, "初始化配置文件响应参数 = ${JSON.toJSONString(data)}")
                it.onNext(initInfo)
            } else {
                LogUtil.customLog(context, "初始化配置文件响应参数 = ${JSON.toJSONString(initInfoBean)}")
                it.onNext(initInfoBean)
            }
        }
    }
}