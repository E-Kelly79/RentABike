package com.bike.rent.kelly.ui.favorites

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.favs.Favourites
import com.bike.rent.kelly.ui.base.BaseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.favourites_fragment.*

import com.marcoscg.dialogsheet.DialogSheet
import kotlinx.android.synthetic.main.favourites_fragment.mFavRecyclerView
import timber.log.Timber

class FavouritesFragment: BaseFragment() {
    lateinit var database:FirebaseDatabase
    lateinit var favRef: DatabaseReference
    lateinit var mView: View
    lateinit var favList: ArrayList<Favourites>
    var mFavRecyclerViewAdapter: FavouritesRecyclerViewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        favRef = database.reference
        favList = ArrayList()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.favourites_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Favourites List")
        favList = ArrayList()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getFavouritesFromDatabase()
    }

    fun getFavouritesFromDatabase(){
        database.reference.child("Favourites").orderByChild("cityName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    favList.clear()
                    for (fav in dataSnapshot.children) {
                        val myFavs = fav.getValue(Favourites::class.java)!!
                        favList.add(myFavs)
                        if (favList.size > 0){
                            favoritesImg.visibility = View.GONE
                            noFavsText.visibility = View.GONE
                            favsSubText.visibility = View.GONE
                            mFavRecyclerView.visibility = View.VISIBLE
                        }


                        mFavRecyclerViewAdapter = FavouritesRecyclerViewAdapter(favList, context!!){
                            deleteFavourite(favList[it].favId!!)
                        }
                        layoutManager = LinearLayoutManager(context)
                        mFavRecyclerView!!.layoutManager = layoutManager
                        mFavRecyclerView!!.adapter = mFavRecyclerViewAdapter
                        mFavRecyclerViewAdapter!!.notifyDataSetChanged()

                        Log.i("FAVS", favList.toString())
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Timber.e(databaseError.toException())
                Toast.makeText(context, "Failed to retrive information from database.", Toast.LENGTH_SHORT).show()
            }
        })
    }



    @SuppressLint("ResourceType")

    fun deleteFavourite(favId: String) {
        val drFavourite = FirebaseDatabase.getInstance().getReference("Favourites").child(favId)
            val dialogSheet: DialogSheet = DialogSheet(context)
            dialogSheet.setTitle(R.string.dialog_warning)
                .setMessage(R.string.dialog_delete)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_delete_btn){
                    drFavourite.removeValue()
                }
                .setNegativeButton("Cancel"){

                }
                .setRoundedCorners(false)
                .setBackgroundColor(Color.WHITE)
                .setButtonsColorRes(R.color.color_primary)
                .show()
    }

    override fun onResume() {
        super.onResume()
        if (favList.size <= 0){
            favoritesImg.visibility = View.VISIBLE
            noFavsText.visibility = View.VISIBLE
            favsSubText.visibility = View.VISIBLE
            mFavRecyclerView.visibility = View.GONE
        }
    }
}