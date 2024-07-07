package net.aihelp.demoapp.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object InitUtil {
    fun copyAssetAndWrite(context: Context, fileName: String?): Boolean {
        try {
            val cacheDir = context.cacheDir
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val outFile = File(cacheDir, fileName)
            if (!outFile.exists()) {
                val res = outFile.createNewFile()
                if (!res) {
                    return false
                }
            } else {
                if (outFile.length() > 10) { //表示已经写入一次
                    return true
                }
            }
            val `is` = context.assets.open(fileName!!)
            val fos = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var byteCount: Int
            while (`is`.read(buffer).also { byteCount = it } != -1) {
                fos.write(buffer, 0, byteCount)
            }
            fos.flush()
            `is`.close()
            fos.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    fun dip2px(context: Context?, dpValue: Double): Int {
        if (context == null) return dpValue.toInt()
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5).toInt()
    }
}
