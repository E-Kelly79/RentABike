package com.bike.rent.kelly.ui.favorites


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.DatabaseHelper

import com.bike.rent.kelly.model.favs.Favourites
import com.bike.rent.kelly.ui.base.BaseActivity

import com.bike.rent.kelly.ui.base.BaseFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.favourites_fragment.fav_recycler_view

class FavouritesFragment: BaseFragment() {
    lateinit var mView: View
    lateinit var favList: ArrayList<Favourites>
    var mFavRecyclerViewAdapter: FavouritesRecyclerViewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var favRecycler: RecyclerView? = null
    lateinit var database:FirebaseDatabase
    lateinit var favRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        favRef = database.getReference("Favourites")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.favourites_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Favourites List")
        favRecycler = mView.findViewById(R.id.fav_recycler_view)


        favList = ArrayList<Favourites>()
        for(i in 0..11){
            favRef = favRef.child("favLists").push()
            favList.add(Favourites(favRef.key!!, "gfdgdsfg","Hello",  0.0f, 0.0f))
        }
        favRef.setValue(favList)


        favRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(erroe: DatabaseError) {

            }

            override fun onDataChange(data: DataSnapshot) {
                var value = data.value
                Log.d("Log", value.toString())
            }
        })

        mFavRecyclerViewAdapter = FavouritesRecyclerViewAdapter(favList, context!!){}

        layoutManager = GridLayoutManager(context, 3)
        favRecycler!!.layoutManager = layoutManager
        favRecycler!!.adapter = mFavRecyclerViewAdapter

        return mView
    }


}