package com.bike.rent.kelly.data.local

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseHelper {

    lateinit var database: FirebaseDatabase
    lateinit var dbRef: DatabaseReference

    constructor(collection: String){
        this.database = FirebaseDatabase.getInstance()
        this.dbRef = database.getReference(collection)
    }

    fun setValue(message: Any){
        dbRef.child("favourites").setValue(message)
    }



    fun read(){
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(values: DataSnapshot) {
                if (values.exists()){
                    for (h in values.children){
                        Log.i("TAG", h.toString())
                    }
                }
            }
        })
    }

}
