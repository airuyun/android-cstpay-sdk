package com.cst.cstpaysdk.util

import android.os.Environment
import com.alibaba.fastjson.util.IOUtils
import java.io.*


/**
 * @author TJS
 * @date 2019/11/19 11:04
 * @cst_do 功能描述：文件操作相关工具类
 * 影响范围：
 * 备注：
 * @cst_end
 */
object FileUtil {

    /**
     * 获取SD卡根目录
     * @return String
     */
    fun getPATH(): String {
        val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        return if (sdCardExist) {
            Environment.getExternalStorageDirectory().toString() + File.separator
        } else {
            Environment.getDataDirectory().absolutePath + File.separator
        }
    }

    /**
     * 获取目录下的所有文件名
     */
    public fun listFile(dirPath: String): List<File> {
        val dir = File(dirPath)
        val list: MutableList<File> = mutableListOf()
        if (dir.isDirectory) {
            val files = dir.listFiles() ?: return list
            for (f in files) {
                list.add(f)
            }
        }
        return list
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public fun copyFile(srcPath: String, destPath: String, deleteSrc: Boolean): Boolean {
        val srcFile = File(srcPath)
        val destFile = File(destPath)
        return copyFile(srcFile, destFile, deleteSrc)
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    private fun copyFile(srcFile: File, destFile: File, deleteSrc: Boolean): Boolean {
        if (!srcFile.exists() || !srcFile.isFile) {
            return false
        }
        val destParentFile: File? = destFile.parentFile
        if(destParentFile == null || !destParentFile.exists()) {
            destParentFile?.mkdirs()
        }

        var inputStream: InputStream? = null
        var out: OutputStream? = null
        try {
            inputStream = FileInputStream(srcFile)
            out = FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            var i: Int
            while (inputStream.read(buffer).apply { i = this } > 0) {
                out.write(buffer, 0, i)
                out.flush()
            }
            if (deleteSrc) {
                srcFile.delete()
            }
        } catch (e: Exception) {
            return false
        } finally {
            IOUtils.close(out)
            IOUtils.close(inputStream)
        }
        return true
    }

    fun deleteFile(filePath: String): Boolean? {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            return false
        }
        return file.delete()
    }

    fun deleteAllFile(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            return
        }
        //取得这个目录下的所有子文件对象
        val files: Array<File>? = file.listFiles()
        if (files != null) {
            //遍历该目录下的文件对象
            for (f: File in files) {
                //判断子目录是否存在子目录,如果是文件则删除
                if (f.isDirectory) {
                    deleteAllFile(f.absolutePath)
                } else {
                    f.delete()
                }
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete()
    }

    fun readFile(filePath: String): String? {
        val buffer = StringBuffer()
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            return null
        }
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader(file))
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                buffer.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        return buffer.toString()
    }

    fun writeFile(data: String, filePath: String) {
        val file = File(filePath)
        val parentFile: File? = file.parentFile
        if(parentFile != null && (!parentFile.exists() || !parentFile.isDirectory)) {
            parentFile.mkdirs()
        }
        if(file.exists()) {
            file.delete()
            file.createNewFile()
        } else {
            file.createNewFile()
        }
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(filePath, false)
            fileWriter.write(data)
        } catch (e: IOException) {
            e.printStackTrace()
        }finally {
            fileWriter?.flush()
            fileWriter?.close()
        }
    }
}
