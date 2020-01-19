package com.cst.cstpaysdk.util

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author TJS
 * @date 2019/11/19 11:04
 * @cst_do 将日志保存到手机，方便问题分析与跟踪
 * 影响范围：日志
 * 备注：
 * @cst_end
 */
object LogUtil {

    /**
     * 自定义日志打印
     *
     * @param context 上下文
     * @param msg 打印输出的信息
     */
    fun customLog(context: Context, msg: String) {
        //ManifestUtils.isDebug(context)?.let { isDebug: Boolean ->
            //if (isDebug) {
                Log.i("TJS", "======$msg")
                saveLogToFile(context, msg)
            //}
        //}
    }

    private fun saveLogToFile(context: Context, msg: String) {
        val filePath = "${FileUtil.getPATH()}/${context.packageName}/log/log_" + DateUtils.getCurrentFormatTime("yyyyMMddHH") + ".txt"
        val saveFile = File(filePath)
        val parentFile: File = saveFile.parentFile ?: return
        deleteAllFileIfFull(parentFile)
        var outStream: FileOutputStream? = null
        try {
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            if (!saveFile.exists()) {
                saveFile.createNewFile()
            }
            outStream = FileOutputStream(saveFile, true)
            outStream.write(getMsgData(msg).toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (outStream != null) {
                try {
                    outStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun deleteAllFileIfFull(logDir: File?) {
        if (logDir == null) {
            return
        }
        if (logDir.exists() && logDir.isDirectory) {
            var totalSize = 0L
            val logFiles: Array<File> = logDir.listFiles() ?: return
            for (logFile in logFiles) {
                totalSize += logFile.length()
            }
            if (totalSize > 1024 * 1024 * 1024) {
                for (logFile in logFiles) {
                    logFile.delete()
                }
            }
        }
    }

    private fun getMsgData(msg: String): String {
        return ("[" + DateUtils.getCurrentFormatTime("yyyy-MM-dd HH:mm:ss:SSS") + "]====="
                + "Tag :" + "TJS" + "\t" + "Message:" + msg + "\r\n")
    }
}
