package com.oscarvj.pruebanach.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.oscarvj.pruebanach.BuildConfig
import com.oscarvj.pruebanach.constants.Constants
import java.io.*

object CameraUtil {

    const val REQUEST_IMAGE_CAPTURE = 101

    lateinit var photoFile: File

    fun dispatchTakeCamera(base: Fragment) {
        try {
            val context: Context? = base.context
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            pictureIntent.also { takePictureIntent ->
                takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                    try {
                        photoFile = createImageFile(context)!!
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                    val photoURI = FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + Constants.FILE_PROVIDER, photoFile
                    )

                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    base.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun decodeBase64Bitmap(bitmap: Bitmap): String {
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getBitmap(path: String): Bitmap? {
        val bitmap: Bitmap?
        try {
            val f = File(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = compressImage(
                BitmapFactory.decodeStream(FileInputStream(f), null, options)!!,
                Constants.PHOTO_MAX_SIZE
            )
            decodeBase64Bitmap(bitmap!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return bitmap
    }

    fun compressImage(image: Bitmap, maxSize: Int): Bitmap? {
        if (image.width != 0) {
            var width = image.width
            var height = image.height

            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }
        return null
    }

    private fun createImageFile(c: Context): File? {
        var image: File? = null
        try {
            val imageFileName = "IMG_"
            val storageDir = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            image = File.createTempFile(imageFileName, ".jpg", storageDir)
            image.createNewFile()
            photoFile = image
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return image
    }
}