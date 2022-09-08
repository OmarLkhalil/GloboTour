package com.omar.globotour.favorite

import android.os.Bundle
import android.text.BoringLayout.make
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.omar.globotour.R
import com.omar.globotour.city.City
import com.omar.globotour.city.VacationSpots
import com.omar.globotour.city.VacationSpots.favoriteCityList
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteCityList   : ArrayList<City>
    private lateinit var favoriteAdapter    : FavoriteAdapter
    private lateinit var recyclerView       : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {

        val context = requireContext()

        favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
        favoriteAdapter = FavoriteAdapter(context, favoriteCityList)

        recyclerView = view.findViewById(R.id.favourite_recycler_view)
        recyclerView.adapter = favoriteAdapter
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//            when item is dragged
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            Collections.swap(favoriteCityList, fromPosition, toPosition)

            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //called when item is swiped.
            val position = viewHolder.adapterPosition
            val deleteCity: City = favoriteCityList[position]

            deleteItem(position)
            updateCityList(deleteCity, false)

            Snackbar.make(recyclerView, "Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    undoDelete(position, deleteCity)
                    updateCityList(deleteCity, true)
                }.show()
                }
        })




    private fun deleteItem(position : Int){
        favoriteCityList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
    }

    private fun undoDelete(position: Int, deleteCity: City){
        favoriteCityList.add(position, deleteCity)
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged( position, favoriteCityList.size)
    }

    private fun updateCityList(deleteCity: City, isFavorite: Boolean){
        val cityList    = VacationSpots.cityList!!
        val position    = cityList.indexOf(deleteCity)
        cityList[position].isFavorite = isFavorite
    }

}
