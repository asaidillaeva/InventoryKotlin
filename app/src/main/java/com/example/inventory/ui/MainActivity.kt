package com.example.inventory.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun deleteAll(item: MenuItem) {
        productViewModel.deleteAll()
    }

    fun openEditor(view: View) {
        val intent = Intent(this, EditorActivity::class.java)
        startActivityForResult(intent, CREATE)
    }

    fun sale(view: View) {

    }
    override fun onClick(position: Int) {
        val intent = Intent(this, EditorActivity::class.java)
        startActivityForResult(intent, EDIT)
    }


}