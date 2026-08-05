package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marketplace_products")
data class MarketplaceProduct(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String, // Electronics, Gift Cards, Digital Goods, Subscriptions, Financial Tools
    val imageUrl: String = "",
    val sellerName: String,
    val rating: Float = 4.8f,
    val reviewsCount: Int = 124,
    val cashbackPct: Double = 5.0,
    val isApproved: Boolean = true,
    val isFeatured: Boolean = false
)
