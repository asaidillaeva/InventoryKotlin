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
    var image: ByteArray?

) : Serializable {
    constructor(title: String, price: Int, quantity: Int, supplier: String): this(
     title, price, quantity, supplier, image = null
    )

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
