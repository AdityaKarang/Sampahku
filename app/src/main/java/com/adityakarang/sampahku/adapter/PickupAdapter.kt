package com.adityakarang.sampahku.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.adityakarang.sampahku.databinding.RiwayatItemBinding
import com.adityakarang.sampahku.filter.PickupFilter
import com.adityakarang.sampahku.model.PickupModel
import com.google.firebase.database.FirebaseDatabase

class PickupAdapter : RecyclerView.Adapter<PickupAdapter.HolderPickup>, Filterable {

    private val context: Context
    public var pickupArrayList: ArrayList<PickupModel>
    private lateinit var binding: RiwayatItemBinding
    private var filterlist: ArrayList<PickupModel>

    private var filter: PickupFilter? = null

    constructor(context:Context, pickupArrayList: ArrayList<PickupModel>) {
        this.context = context
        this.pickupArrayList = pickupArrayList
        this.filterlist = pickupArrayList
    }


    inner class HolderPickup(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvName : TextView = binding.tvNama
        var tvAlamat : TextView = binding.tvAlamat
        var tvJenis : TextView = binding.tvJenis
        var tvBerat : TextView = binding.tvBerat
        var riwayatdlt : ImageButton = binding.riwayatDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPickup {
        binding = RiwayatItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderPickup(binding.root)
    }

    override fun onBindViewHolder(holder:HolderPickup, position: Int) {
        val model = pickupArrayList[position]
        val id = model.id
        val nama = model.nama
        val alamat = model.alamat
        val jenis = model.jenis
        val berat = model.berat
        val uid = model.uid
        val timestamp = model.timestamp

        holder.tvName.text =  nama
        holder.tvAlamat.text = "Alamat : " + alamat
        holder.tvJenis.text = "Jenis Sampah : " + jenis
        holder.tvBerat.text = "Berat : " + berat + " Kg"


        holder.riwayatdlt.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Hapus")
                .setMessage("Apakah ?")
                .setPositiveButton("Konfirmasi"){a, d->
                    Toast.makeText(context, "Menghapus Kategori...", Toast.LENGTH_SHORT).show()
                    deleteKategori(model, holder)
                }
                .setNegativeButton("Batal"){a, d->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun deleteKategori(model: PickupModel, holder: HolderPickup) {
        val id = model.id

        val ref = FirebaseDatabase.getInstance().getReference("Pickup")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Berhasil Menghapus", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{e->
                Toast.makeText(context, "unable to delete due to ${e.message}...", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return pickupArrayList.size
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = PickupFilter(filterlist, this)
        }
        return filter as PickupFilter
    }

}