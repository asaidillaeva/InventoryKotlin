package com.example.inventory.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
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
        if (intent.hasExtra("product")) {
            product = intent.extras?.get("product") as Product
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


    fun addImage(view: View) {
        val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePicIntent, CAMERA_REQUEST_CODE)
        }
    }

    fun save(item: MenuItem) {
        when {
            titleEdit.text.trim().isBlank() -> {
                titleEdit.error = "Fill in the field!"
                return
            }
            priceEdit.text.trim().isBlank() -> {
                priceEdit.error = "Fill in the field!"
                return
            }
            quantityEdit.text.trim().isBlank() -> {
                quantityEdit.error = "Fill in the field!"
                return
            }
            supplierEdit.text.trim().isBlank() -> {
                supplierEdit.setText("NoName")
            }
            imageEdit.drawable == null -> {
                imageEdit.setImageResource(R.drawable.ic_bag)
            }
        }

        val productTitle = titleEdit.text.trim().toString()
        val productPrice = Integer.valueOf(priceEdit.text.trim().toString())
        val productQuantity = Integer.valueOf(quantityEdit.text.trim().toString())
        val productSupplier = supplierEdit.text.trim().toString()

        val bitmap: Bitmap = imageEdit.drawable.toBitmap(100, 100, null)
        val image = bitmapToByte(bitmap)

        val intent = Intent()

        val product = Product(productTitle, productPrice, productQuantity, productSupplier, image)
        productViewModel.insert(product)
        if (CODE == 1) {
            productViewModel.insert(product)

        } else (productViewModel.insert(product)
                )
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val extras: Bundle = data!!.extras!!
            val image = extras.get("data")
            imageEdit.setImageBitmap(image as Bitmap?)
        }
    }

    fun back(view: View) {
        this.finish()
    }
}
