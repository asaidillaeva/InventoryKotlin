package com.example.inventory.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.R
import com.example.inventory.data.Product
import java.io.ByteArrayInputStream

class RecyclerAdapter(private var listener: ItemClickListener) :
    ListAdapter<Product, RecyclerAdapter.ProductViewHolder>(ProductsComparator()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ProductViewHolder(view)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView.findViewById<TextView>(R.id.title)
        private val priceView = itemView.findViewById<TextView>(R.id.price)
        private val quantityView = itemView.findViewById<TextView>(R.id.quantity)
        private val supplierView = itemView.findViewById<TextView>(R.id.supplier)
        private val imageView = itemView.findViewById<ImageView>(R.id.image)

        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val currentItem = getItem(position)
            titleView.text = currentItem.title
            priceView.text = "Price: " + currentItem.price.toString()
            quantityView.text = "Quantity: " + currentItem.quantity.toString()
            supplierView.text = "Supplier: " + currentItem.supplier
            if (currentItem.image != null) {
                val imgStream = ByteArrayInputStream(currentItem.image)
                val bitmap = BitmapFactory.decodeStream(imgStream)

                imageView.setImageBitmap(bitmap)
            }

            itemView.setOnClickListener {
                listener.onClick(position)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(position: Int)
    }

    class ProductsComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }

}



