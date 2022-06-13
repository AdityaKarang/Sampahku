package com.adityakarang.sampahku.filter

import android.widget.Filter
import com.adityakarang.sampahku.adapter.KategoriAdapter
import com.adityakarang.sampahku.model.KategoriModel

class KategoriFilter : Filter {

    private var filterList: ArrayList<KategoriModel>

    private var kategoriAdapter: KategoriAdapter

    constructor(filterList: ArrayList<KategoriModel>, kategoriAdapter: KategoriAdapter) : super() {
        this.filterList = filterList
        this.kategoriAdapter = kategoriAdapter
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val result = FilterResults()

        if (constraint != null && constraint.isEmpty()) {

            constraint = constraint.toString().uppercase()
            val filtermodel: ArrayList<KategoriModel> = ArrayList()

            for (i in 0 until filterList.size) {
                if (filterList[i].kategori.uppercase().contains(constraint)) {
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
        kategoriAdapter.categoryArrayList = result.values as ArrayList<KategoriModel>

        kategoriAdapter.notifyDataSetChanged()
    }
}