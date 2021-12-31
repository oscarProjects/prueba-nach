package com.oscarvj.pruebanach.data.firebase.storage

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import javax.inject.Inject

class FireBaseStorage @Inject constructor() {

    val storage = Firebase.storage

    fun uploadImage(img: String, fileUri: Uri, context: Context) {
        val storageRef = storage.reference

        // Create the file metadata
        val metadata = storageMetadata {
            contentType = "image/jpeg"
        }

        // Upload file and metadata to the path 'images/mountains.jpg'
        val uploadTask = storageRef.child("Imagenes").child(img).putFile(fileUri, metadata)

        val response: Boolean = false

        uploadTask.addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
            Log.d("TAG", "Upload is $progress% done")
        }.addOnPausedListener {
            Log.d("TAG", "Upload is paused")
        }.addOnFailureListener {
            Log.d("TAG", "Upload is failure")
        }.addOnSuccessListener {
            Toast.makeText(context, "Se ha cargado la imagen con éxito", Toast.LENGTH_SHORT).show()
        }
    }
}