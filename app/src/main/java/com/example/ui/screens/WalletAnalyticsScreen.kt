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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.TransactionEntity
import com.example.data.models.WalletBalances
import com.example.ui.components.GlassCard
import com.example.ui.components.IncomeChartComponent
import com.example.ui.components.StatCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

private data class BalanceItemInfo(
    val label: String,
    val amount: Double,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun WalletAnalyticsScreen(
    wallet: WalletBalances?,
    transactions: List<TransactionEntity>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var showExportModal by remember { mutableStateOf(false) }

    val all11Balances = listOf(
        BalanceItemInfo("Total Balance", wallet?.totalBalance ?: 0.0, Icons.Default.AccountBalanceWallet, BrightGold),
        BalanceItemInfo("Available Balance", wallet?.availableBalance ?: 0.0, Icons.Default.Payments, SuccessGreen),
        BalanceItemInfo("Pending Balance", wallet?.pendingBalance ?: 0.0, Icons.Default.AccountBalance, WarningAmber),
        BalanceItemInfo("Referral Income", wallet?.referralIncome ?: 0.0, Icons.Default.Groups, BrightGold),
        BalanceItemInfo("Salary Income", wallet?.salaryIncome ?: 0.0, Icons.Default.Work, MetallicGold),
        BalanceItemInfo("Task Income", wallet?.taskIncome ?: 0.0, Icons.Default.CheckCircle, SuccessGreen),
        BalanceItemInfo("Marketplace Income", wallet?.marketplaceIncome ?: 0.0, Icons.Default.ShoppingBag, BrightGold),
        BalanceItemInfo("Deposit Balance", wallet?.depositBalance ?: 0.0, Icons.Default.ArrowDownward, RoyalBluePrimary),
        BalanceItemInfo("Withdrawable Balance", wallet?.withdrawableBalance ?: 0.0, Icons.Default.ArrowUpward, SuccessGreen),
        BalanceItemInfo("Bonus Balance", wallet?.bonusBalance ?: 0.0, Icons.Default.Star, MetallicGold),
        BalanceItemInfo("Cashback Balance", wallet?.cashbackBalance ?: 0.0, Icons.Default.CardGiftcard, BrightGold)
    )

    val filteredTransactions = transactions.filter { tx ->
        val matchesSearch = tx.title.contains(searchQuery, ignoreCase = true) || tx.referenceNumber.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedCategoryFilter) {
            "ALL" -> true
            "DEPOSIT" -> tx.category == "DEPOSIT"
            "WITHDRAWAL" -> tx.category == "WITHDRAWAL"
            "REFERRAL" -> tx.category.startsWith("REFERRAL")
            "SALARY" -> tx.category == "SALARY"
            else -> true
        }
        matchesSearch && matchesFilter
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
                        text = "Wallet & Analytics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Text(
                        text = "Full breakdown of all 11 digital wallet balances",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = { showExportModal = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkNavyCard, contentColor = BrightGold),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderGold),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Export",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Report", fontSize = 11.sp)
                }
            }
        }

        // Interactive Chart Component
        item {
            IncomeChartComponent()
        }

        // 11 Balances Cards Section
        item {
            Text(
                text = "Wallet Balances Engine (11 Accounts)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                all11Balances.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { itemInfo ->
                            StatCard(
                                title = itemInfo.label,
                                amount = "$${String.format("%,.2f", itemInfo.amount)}",
                                icon = itemInfo.icon,
                                modifier = Modifier.weight(1f),
                                accentColor = itemInfo.color
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // Transactions Search & Filters
        item {
            Column {
                Text(
                    text = "Transaction Records",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by description or reference ID...", fontSize = 12.sp, color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = BrightGold) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("ALL", "DEPOSIT", "WITHDRAWAL", "REFERRAL", "SALARY").forEach { filter ->
                        val isSelected = selectedCategoryFilter == filter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) RoyalBluePrimary else DarkNavyCard)
                                .border(1.dp, if (isSelected) BrightGold else GlassBorderGold, RoundedCornerShape(16.dp))
                                .clickable { selectedCategoryFilter = filter }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) BrightGold else Color.Gray,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        items(filteredTransactions) { tx ->
            TransactionRowItem(tx)
        }
    }

    if (showExportModal) {
        GlassCard(
            modifier = Modifier.padding(24.dp),
            borderColor = BrightGold
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "📄 Export Wallet Statement",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Statement generated for 11 balances & ${transactions.size} transactions.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showExportModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Download PDF Statement", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
