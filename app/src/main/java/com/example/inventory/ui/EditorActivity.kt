package com.example.inventory.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.inventory.ProductApplication
import com.example.inventory.R
import com.example.inventory.data.Product
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.item.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class EditorActivity : AppCompatActivity() {

    companion object {
        var CODE = 1
        val CAMERA_REQUEST_CODE = 22

    }

    lateinit var product: Product
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_editor)
        if (intent.hasExtra("productId")) {
            product = intent.extras?.get("productId") as Product
            titleEdit.setText(product.title)
            priceEdit.setText(product.price)
            quantity.setText(product.quantity)
            supplierEdit.setText(product.supplier)
            if (product.image != null) {
                imageEdit.setImageBitmap(byteToBitmap(product.image!!))
            }
            CODE = 2
        } else {
            CODE = 1
        }

    }



    private fun bitmapToByte(image: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 25, stream)
        return stream.toByteArray()
    }

    private fun byteToBitmap(img: ByteArray): Bitmap {
        val imgStream = ByteArrayInputStream(img)
        return BitmapFactory.decodeStream(imgStream)
    }

    fun delete(item: MenuItem) {
        val dialogBuilder = AlertDialog.Builder(this@EditorActivity)
        dialogBuilder.setMessage("Are you sure to delete this item?")
            .setCancelable(false)
            .setPositiveButton("DELETE") { _, _ ->
                productViewModel.delete(product)
            }
        dialogBuilder.setNegativeButton("CANCEL") { _, _ ->
            Toast.makeText(this@EditorActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        val alert = dialogBuilder.create()
        alert.setTitle("CAUTION:")
        alert.show()
        true
    }

    fun addImage(view: View) {
        val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePicIntent, CAMERA_REQUEST_CODE)
        }
    }

    fun save(item: MenuItem) {

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val extras: Bundle = data!!.extras!!
            val image = extras.get("data")
            imageEdit.setImageBitmap(image as Bitmap?)
        }
    }
}
