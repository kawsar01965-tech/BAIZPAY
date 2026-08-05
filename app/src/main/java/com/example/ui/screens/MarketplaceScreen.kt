package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.MarketplaceProduct
import com.example.ui.components.GlassCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun MarketplaceScreen(
    products: List<MarketplaceProduct>
) {
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var cartCount by remember { mutableStateOf(0) }
    var showProductUploadModal by remember { mutableStateOf(false) }

    val categories = listOf("ALL", "Electronics", "Gift Cards", "Digital Goods", "Financial Tools")

    val filteredProducts = products.filter { prod ->
        if (selectedCategoryFilter == "ALL") true else prod.category == selectedCategoryFilter
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Global FinTech Marketplace",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Text(
                        text = "Buy hardware, digital goods & gift cards with instant cashback",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }

                Row {
                    Button(
                        onClick = { showProductUploadModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkNavyCard, contentColor = BrightGold),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderGold),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Upload", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Sell Item", fontSize = 11.sp)
                    }
                }
            }
        }

        // Categories Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategoryFilter == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) RoyalBluePrimary else DarkNavyCard)
                            .border(1.dp, if (isSelected) BrightGold else GlassBorderGold, RoundedCornerShape(16.dp))
                            .clickable { selectedCategoryFilter = cat }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) BrightGold else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        items(filteredProducts) { product ->
            MarketplaceProductRow(product) {
                cartCount++
            }
        }
    }

    if (showProductUploadModal) {
        GlassCard(
            modifier = Modifier.padding(24.dp),
            borderColor = BrightGold
        ) {
            Column {
                Text(
                    text = "List Product on Marketplace",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Product Title") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Price ($ USD)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { showProductUploadModal = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { showProductUploadModal = false }, colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black)) {
                        Text("Submit Listing", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun MarketplaceProductRow(product: MarketplaceProduct, onAddToCart: () -> Unit) {
    GlassCard(
        cornerRadius = 16.dp,
        borderColor = GlassBorderGold,
        contentPadding = 14.dp
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(RoyalBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = "Prod", tint = BrightGold, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = product.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "${product.sellerName} • ⭐ ${product.rating} (${product.reviewsCount})", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
                    }
                }

                Text(text = "$${product.price.toInt()} USD", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = BrightGold)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodySmall, color = Color.White, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CardGiftcard, contentDescription = "Cashback", tint = SuccessGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${product.cashbackPct.toInt()}% Instant Cashback", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Buy Now", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
