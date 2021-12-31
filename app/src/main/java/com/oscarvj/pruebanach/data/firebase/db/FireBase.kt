package com.oscarvj.pruebanach.data.firebase.db

import android.util.Log

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.listener.ReturnLocations
import java.util.*
import javax.inject.Inject

class FireBase @Inject constructor(){
    val db = Firebase.firestore

    fun registerLocation(latitude: Double, longitude: Double){
        val loc = hashMapOf(
            Constants.LATITUD to latitude,
            Constants.LONGITUD to longitude,
            Constants.DATE to Date()
        )
        db.collection(Constants.NAMEDBFB)
            .add(loc)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun getLocation( returnLocations: ReturnLocations) {
        db.collection(Constants.NAMEDBFB)
            .get()
            .addOnSuccessListener { result ->
                returnLocations.getListLocations(result)
            }
            .addOnFailureListener { exception ->
                Log.w("DATA TAG", "Error getting documents.", exception)
            }
    }
}