package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product")
data class Product(
    var title: String,
    var price: Int,
    var quantity: Int,
    var supplier: String,
    var image: ByteArray? =null

) : Serializable {
    constructor(title: String, price: Int, quantity: Int, supplier: String): this(
     title, price, quantity, supplier, image = null
    )

    constructor(reply: Product) : this(
        title = reply.title, price = reply.price, quantity = reply.quantity, supplier = reply.supplier,
    image = reply.image
    )


    @PrimaryKey(autoGenerate = true)
    lateinit var id: String

}
