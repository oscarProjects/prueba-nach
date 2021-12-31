package com.oscarvj.pruebanach.listener

import com.google.firebase.firestore.QuerySnapshot

interface ReturnLocations {
    fun getListLocations(result: QuerySnapshot)
}