package com.cst.cstpaysdk.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.io.*

/**
 * @author TJS
 * @date 2019/07/20 14:40
 * @cst_do 功能描述：Bitmap与Base64字符串之间的互相转换
 * 影响范围：人脸图片
 * 备注：
 * @cst_end
 */
object Base64Utils {

    /**
     * 将本地存储设备仲的图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgPath 图片路径
     * @return base64字符串
     */
    fun FileToBase64(imgPath: String): String? {
        var inputStream: InputStream? = null
        var encode: String? = null
        try {
            // 读取图片字节数组
            inputStream = FileInputStream(imgPath)
            val data = ByteArray(inputStream.available())
            inputStream.read(data)

            //对字节数组Base64编码
            encode = BASE64Encoder().encode(data)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return encode
    }

    /**
     * base64字符串转化成图片文件
     *
     * @param imgData     图片编码
     * @param imgFilePath 存放到本地路径
     */
    @Throws(IOException::class)
    fun Base64ToFile(imgData: String?, imgFilePath: String): Boolean { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) {
            return false
        }

        var out: OutputStream? = null
        try {
            out = FileOutputStream(imgFilePath)
            // Base64解码
            val b: ByteArray = BASE64Decoder().decodeBuffer(imgData)
            for (i in b.indices) {
                if (b[i] < 0) {// 调整异常数据
                    //b[i] += 256
                    b[i] = b[i].plus(256).toByte()
                }
            }
            out.write(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            out?.flush()
            out?.close()
            return true
        }
    }

    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap Bitmap
     * @param bitmapQuality 图片质量值
     * @return base64字符串
     */
    fun bitmapToBase64(bitmap: Bitmap, bitmapQuality: Int): String? {
        val bStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream)
        val bytes = bStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    /**
     * 将base64转换成bitmap图片
     *
     * @param string base64图片字符串
     * @return Bitmap
     */
    fun base64ToBitmap(string: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val bitmapArray: ByteArray = Base64.decode(string, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}