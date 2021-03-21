package com.example.inventory.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.ProductApplication
import com.example.inventory.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerAdapter.ItemClickListener {

    companion object {
        const val CREATE = 1
        const val EDIT = 2
    }


     private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RecyclerAdapter(this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        productViewModel.allProducts.observe(this) { products ->
            products.let { adapter.submitList(it) }
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recycler: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder.setMessage("Are you sure to delete this item?")
                    .setCancelable(false)
                    .setPositiveButton("DELETE") { _, _ ->
                        productViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                    }
                dialogBuilder.setNegativeButton("CANCEL") { _, _ ->
                    Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                }
                val alert = dialogBuilder.create()
                alert.setTitle("CAUTION:")
                alert.show()
            }

        }).attachToRecyclerView(recycler)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "New item has been added", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Item has been Updated", Toast.LENGTH_SHORT).show()
        }

    }

    fun deleteAll(item: MenuItem) {
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        dialogBuilder.setMessage("Are you sure to delete all items?")
            .setCancelable(false)
            .setPositiveButton("DELETE") { _, _ ->
                productViewModel.deleteAll()
            }
        dialogBuilder.setNegativeButton("CANCEL") { _, _ ->
            Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        val alert = dialogBuilder.create()
        alert.setTitle("CAUTION:")
        alert.show()
    }


    fun openEditor(view: View) {
        val intent = Intent(this, EditorActivity::class.java)
        startActivityForResult(intent, CREATE)
    }

    override fun onClick(position: Int) {
        var intent = Intent(baseContext, EditorActivity::class.java)
        productViewModel.allProducts.observe(this, Observer {
            intent.apply {
                putExtra("product", it[position])
            }
        })
        startActivityForResult(intent, EDIT)
    }


}

