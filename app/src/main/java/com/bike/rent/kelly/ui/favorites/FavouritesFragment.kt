package com.bike.rent.kelly.ui.favorites


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bike.rent.kelly.R

import com.bike.rent.kelly.model.favs.Favourites


import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.favourites_fragment.fav_recycler_view

class FavouritesFragment: BaseFragment() {
    lateinit var mView: View
    lateinit var favList: ArrayList<Favourites>
    var mFavRecyclerViewAdapter: FavouritesRecyclerViewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var favRecycler: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.favourites_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Favourites List")
        favRecycler = mView.findViewById(R.id.fav_recycler_view)

        favList = ArrayList<Favourites>()
        for(i in 1..12){
            favList.add(Favourites(1, "Hello", "Hello", 0.0f, 0.0f, "Hello"))
        }


        mFavRecyclerViewAdapter = FavouritesRecyclerViewAdapter(favList, context!!){}

        layoutManager = GridLayoutManager(context, 3)
        favRecycler!!.layoutManager = layoutManager
        favRecycler!!.adapter = mFavRecyclerViewAdapter

        return mView
    }


}