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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.ProductApplication
import com.example.inventory.R
import com.example.inventory.data.Product
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


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "New item has been added", Toast.LENGTH_SHORT).show()
        } else if (requestCode == CREATE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Item has been Updated", Toast.LENGTH_SHORT).show()
        }

    }

    fun deleteAll(item: MenuItem) {
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        dialogBuilder.setMessage("Are you sure to delete this item?")
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
        true
    }

    fun openEditor(view: View) {
        val intent = Intent(this, EditorActivity::class.java)
        startActivityForResult(intent, CREATE)
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, EditorActivity::class.java)
        intent.putExtra("item", position)
        startActivityForResult(intent, EDIT)
    }


}