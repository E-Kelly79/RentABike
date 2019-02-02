package com.bike.rent.kelly.ui.favorites


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView

import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.favs.Favourites
import com.bike.rent.kelly.ui.base.BaseFragment
import com.google.firebase.database.ChildEventListener

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class FavouritesFragment: BaseFragment() {
    lateinit var database:FirebaseDatabase
    lateinit var favRef: DatabaseReference
    lateinit var mView: View
    lateinit var favList: ArrayList<Favourites>
    @BindView(R.id.fav_recycler_view) @JvmField var favRecycler: RecyclerView? = null
    var mFavRecyclerViewAdapter: FavouritesRecyclerViewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        favRef = database.getReference()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.favourites_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Favourites List")

        favRecycler = mView.findViewById(R.id.fav_recycler_view)
        favList = ArrayList<Favourites>()
        getFavouritesFromDatabase()
        return mView
    }

    fun getFavouritesFromDatabase(){
        //mDatabase.getReference().child("Favourites").orderByChild("stationName")
       database.getReference().child("Favourites").orderByChild("cityName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    favList.clear()
                    for (fav in dataSnapshot.children) {
                        var myFavs = fav.getValue(Favourites::class.java)!!
                        favList.add(myFavs)
                        mFavRecyclerViewAdapter = FavouritesRecyclerViewAdapter(favList, context!!){}
                        layoutManager = LinearLayoutManager(context)
                        favRecycler!!.layoutManager = layoutManager
                        favRecycler!!.adapter = mFavRecyclerViewAdapter
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "postMessages:onCancelled", databaseError!!.toException())
                Toast.makeText(context, "Failed to load Message.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}