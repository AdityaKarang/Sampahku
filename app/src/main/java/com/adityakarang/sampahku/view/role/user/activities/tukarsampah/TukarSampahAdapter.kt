package com.adityakarang.sampahku.view.role.user.activities.tukarsampah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adityakarang.sampahku.databinding.RiwayatTukarBinding
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.utils.HelpFunction.rpFormat

class TukarSampahAdapter(
    var mContext: Context,
    tukarSampahModelInputList: MutableList<TukarSampahModel>,
    adapterCallback: TukarSampahActivity
) : RecyclerView.Adapter<TukarSampahAdapter.ViewHolder>() {


    var tukarSampahModel: MutableList<TukarSampahModel>
    var mAdapterCallback: TukarSampahAdapterCallback

    fun setDataAdapter(items: List<TukarSampahModel>) {
        tukarSampahModel.clear()
        tukarSampahModel.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RiwayatTukarBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: TukarSampahModel = tukarSampahModel[position]

        holder.tvNama.setText(data.namaPengguna)
        holder.tvKategori.text = "Sampah " + data.jenisSampah
        holder.tvBerat.text = "Berat : " + data.berat.toString() + " Kg"
        holder.tvSaldo.text = "Pendapatan : " + rpFormat(data.harga)

    }

    override fun getItemCount(): Int {
        return tukarSampahModel.size
    }

    inner class ViewHolder(itemView: RiwayatTukarBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvNama: TextView = itemView.tvNama
        var tvKategori: TextView = itemView.tvKategori
        var tvBerat: TextView = itemView.tvBerat
        var tvSaldo: TextView = itemView.tvSaldo
        var imageDelete: TextView = itemView.imageTukar

        init {
            imageDelete.setOnClickListener { view: View? ->
                val tukarSampahModelLaundry: TukarSampahModel = tukarSampahModel[adapterPosition]
                mAdapterCallback.onDelete(tukarSampahModelLaundry)
            }
        }
    }

    interface TukarSampahAdapterCallback {
        fun onDelete(tukarSampahModel: TukarSampahModel?)
    }

    init {
        tukarSampahModel = tukarSampahModelInputList
        mAdapterCallback = adapterCallback
    }

}