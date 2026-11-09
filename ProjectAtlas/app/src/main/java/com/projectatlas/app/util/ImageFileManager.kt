package com.projectatlas.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ImageFileManager(private val context: Context) {
    private val imagesDir: File by lazy {
        File(context.filesDir, "atlas_images").apply { if (!exists()) mkdirs() }
    }

    suspend fun saveAndCompressImage(sourceUri: Uri, maxDim: Int = 1600): String = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(sourceUri)?.use { BitmapFactory.decodeStream(it, null, bounds) }

        var inSampleSize = 1
        while (bounds.outWidth / inSampleSize > maxDim || bounds.outHeight / inSampleSize > maxDim) {
            inSampleSize *= 2
        }

        val decodeOptions = BitmapFactory.Options().apply {
            this.inSampleSize = inSampleSize
            inPreferredConfig = Bitmap.Config.RGB_565
        }
        val sampledBmp = resolver.openInputStream(sourceUri)?.use {
            BitmapFactory.decodeStream(it, null, decodeOptions)
        } ?: throw IllegalStateException("解码失败")

        val targetFile = File(imagesDir, "img_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg")
        FileOutputStream(targetFile).use { sampledBmp.compress(Bitmap.CompressFormat.JPEG, 85, it) }
        sampledBmp.recycle()
        targetFile.absolutePath
    }

    suspend fun deleteImageFile(filePath: String): Boolean = withContext(Dispatchers.IO) {
        try { File(filePath).takeIf { it.exists() }?.delete() ?: false } catch (e: Exception) { false }
    }
}
