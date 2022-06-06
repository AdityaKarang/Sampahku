package com.adityakarang.sampahku.filter

import android.widget.Filter
import com.adityakarang.sampahku.adapter.PickupAdapter
import com.adityakarang.sampahku.model.PickupModel

class PickupFilter : Filter {

    private var filterList: ArrayList<PickupModel>


    private var pickupAdapter: PickupAdapter

    constructor(filterList: ArrayList<PickupModel>, pickupAdapter: PickupAdapter) : super() {
        this.filterList = filterList
        this.pickupAdapter = pickupAdapter
    }


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val result = FilterResults()

        if (constraint != null && constraint.isEmpty()) {

            constraint = constraint.toString().uppercase()
            val filtermodel: ArrayList<PickupModel> = ArrayList()

            for (i in 0 until filterList.size) {
                if (filterList[i].nama.uppercase().contains(constraint)) {
                    filtermodel.add(filterList[i])
                }
                if (filterList[i].alamat.uppercase().contains(constraint)) {
                    filtermodel.add(filterList[i])
                }
                if (filterList[i].jenis.uppercase().contains(constraint)) {
                    filtermodel.add(filterList[i])
                }
                if (filterList[i].berat.uppercase().contains(constraint)) {
                    filtermodel.add(filterList[i])
                }
            }
            result.count = filtermodel.size
            result.values = filtermodel
        } else {
            result.count = filterList.size
            result.values = filterList

        }
        return result
    }

    override fun publishResults(constraint: CharSequence?, result: FilterResults) {
        pickupAdapter.pickupArrayList = result.values as ArrayList<PickupModel>

        pickupAdapter.notifyDataSetChanged()
    }
}