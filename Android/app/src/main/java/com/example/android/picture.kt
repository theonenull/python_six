package com.example.android

import android.content.Context
import android.content.Intent

import android.graphics.Bitmap
import android.net.Uri

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class picture{
    //保存文件到指定路径
    companion object {fun saveImageToGallery(context: Context, bmp: Bitmap): Boolean {
        // 首先保存图片
        val storePath =
            Environment.getExternalStorageDirectory().absolutePath + File.separator.toString() + "dearxy"
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            //通过io流的方式来压缩保存图片
            val isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos)
            fos.flush()
            fos.close()

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
//            val uri: Uri = Uri.fromFile(file)
//            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
//            return isSuccess
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
    }
}