package com.adityakarang.sampahku.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.adityakarang.sampahku.databinding.RowKategoriBinding
import com.adityakarang.sampahku.filter.KategoriFilter
import com.adityakarang.sampahku.model.KategoriModel
import com.google.firebase.database.FirebaseDatabase

class KategoriAdapter : RecyclerView.Adapter<KategoriAdapter.HolderKategori>, Filterable {

    private val context: Context
    public var categoryArrayList: ArrayList<KategoriModel>
    private lateinit var binding: RowKategoriBinding
    private var filterlist: ArrayList<KategoriModel>

    private var filter: KategoriFilter? = null

    constructor(context: Context, categoryArrayList: ArrayList<KategoriModel>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterlist = categoryArrayList
    }

    inner class HolderKategori(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKategori: TextView = binding.tvKategori
        var kategoridlt: ImageButton = binding.kategoridlt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderKategori {
        binding = RowKategoriBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderKategori(binding.root)
    }

    override fun onBindViewHolder(holder: HolderKategori, position: Int) {
        val model = categoryArrayList[position]
        val id = model.id
        val kategori = model.kategori
        val uid = model.uid
        val timestamp = model.timestamp

        holder.tvKategori.text = kategori

        holder.kategoridlt.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Hapus")
                .setMessage("Apa anda yakin menghapus kategori ini?")
                .setPositiveButton("Konfirmasi") { a, d ->
                    Toast.makeText(context, "Menghapus Kategori...", Toast.LENGTH_SHORT).show()
                    deleteKategori(model, holder)
                }
                .setNegativeButton("Batal") { a, d ->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun deleteKategori(model: KategoriModel, holder: HolderKategori) {
        val id = model.id

        val ref = FirebaseDatabase.getInstance().getReference("Kategori")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Berhasil Menghapus", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "unable to delete due to ${e.message}...",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = KategoriFilter(filterlist, this)
        }
        return filter as KategoriFilter
    }
}