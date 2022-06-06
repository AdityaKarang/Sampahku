package com.adityakarang.sampahku.view.role.user.activities.tukarsampah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ListItemRiwayatBinding
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.utils.FunctionHelper.rupiahFormat

class TukarSampahAdapter(
    var mContext: Context,
    tukarSampahModelInputList: MutableList<TukarSampahModel>,
    adapterCallback: TukarSampahActivity
) : RecyclerView.Adapter<TukarSampahAdapter.ViewHolder>() {


    var tukarSampahModel: MutableList<TukarSampahModel>
    var mAdapterCallback:  TukarSampahAdapterCallback

    fun setDataAdapter(items: List<TukarSampahModel>) {
        tukarSampahModel.clear()
        tukarSampahModel.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: TukarSampahModel = tukarSampahModel[position]

        holder.tvNama.setText(data.namaPengguna)
        holder.tvKategori.text = "Sampah " + data.jenisSampah
        holder.tvBerat.text = "Berat : " + data.berat.toString() + " Kg"
        holder.tvSaldo.text = "Pendapatan : " + rupiahFormat(data.harga)

        if (data.berat < 5) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvStatus.text = "Masih dalam proses"
        } else {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            holder.tvStatus.text = "Sudah di konfirmasi"
        }
    }

    override fun getItemCount(): Int {
        return tukarSampahModel.size
    }

    inner class ViewHolder(itemView: ListItemRiwayatBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvNama: TextView = itemView.tvNama
        var tvKategori: TextView = itemView.tvKategori
        var tvBerat: TextView = itemView.tvBerat
        var tvSaldo: TextView = itemView.tvSaldo
        var tvStatus: TextView = itemView.tvStatus
        var imageDelete: ImageView = itemView.imageDelete

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