package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.data.models.WalletBalances
import com.example.ui.components.GlassCard
import com.example.ui.components.StatCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.GlassBorderWhite10
import com.example.ui.theme.GlassWhite05
import com.example.ui.theme.GlassWhite10
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import com.example.ui.theme.WarningAmber

@Composable
fun HomeScreen(
    user: UserEntity?,
    wallet: WalletBalances?,
    transactions: List<TransactionEntity>,
    aiAdvice: String,
    isAiLoading: Boolean,
    onVerifyClick: () -> Unit,
    onDepositClick: (Double, String) -> Unit,
    onWithdrawClick: (Double, String) -> Unit,
    onAskAi: (String) -> Unit
) {
    var showDepositDialog by remember { mutableStateOf(false) }
    var showWithdrawDialog by remember { mutableStateOf(false) }
    var depositAmountText by remember { mutableStateOf("250.00") }
    var withdrawAmountText by remember { mutableStateOf("100.00") }
    var aiQueryText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card with Total Balance matching Bold Typography theme
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 32.dp,
                borderColor = GlassBorderWhite10,
                contentPadding = 22.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "TOTAL BALANCE",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedDark,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val formattedTotal = String.format("%,.2f", wallet?.totalBalance ?: 12450.85)
                            val parts = formattedTotal.split(".")
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "$${parts[0]}.",
                                    style = MaterialTheme.typography.displayLarge,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimaryDark
                                )
                                Text(
                                    text = if (parts.size > 1) parts[1] else "00",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMutedDark,
                                    modifier = Modifier.padding(bottom = 3.dp)
                                )
                            }
                        }

                        // Gold PRO Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MetallicGold.copy(alpha = 0.2f))
                                .border(1.dp, MetallicGold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = user?.currentSalaryRank?.uppercase() ?: "PRO",
                                color = MetallicGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Sub balances grid with glass containers
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(GlassWhite05)
                                .border(1.dp, GlassWhite05, RoundedCornerShape(16.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "AVAILABLE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedDark,
                                    fontSize = 9.sp,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "$${String.format("%,.2f", wallet?.availableBalance ?: 8230.00)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryDark
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(GlassWhite05)
                                .border(1.dp, GlassWhite05, RoundedCornerShape(16.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "PENDING / REFERRAL",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedDark,
                                    fontSize = 9.sp,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "$${String.format("%,.2f", wallet?.referralIncome ?: 4220.85)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Stats & Analytics Cards (Daily + Team)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Daily Earnings Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(GlassWhite05)
                        .border(1.dp, GlassWhite05, RoundedCornerShape(24.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = "Trending",
                                tint = EmeraldLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DAILY",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedDark,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "+$245.50",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(GlassWhite10)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.70f)
                                    .height(4.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen)
                            )
                        }
                    }
                }

                // Team Analytics Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(GlassWhite05)
                        .border(1.dp, GlassWhite05, RoundedCornerShape(24.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = "Team",
                                tint = MetallicGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TEAM",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedDark,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "842",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "+12 today",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Salary Rank Progress Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                RoyalBluePrimary.copy(alpha = 0.6f),
                                DarkNavyCard
                            )
                        )
                    )
                    .border(1.dp, GlassWhite05, RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "CURRENT RANK",
                                style = MaterialTheme.typography.labelSmall,
                                color = MetallicGold,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${user?.currentSalaryRank ?: "Silver"} Tier",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Next: Gold",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedDark,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "15/50 Direct",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(GlassWhite10)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.30f)
                                .height(8.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(MetallicGold, BrightGold)
                                    )
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Earn $75 Monthly Salary at Gold Rank",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedDark,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // Bottom Action Grid Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionGridButton(
                    label = "WALLET",
                    icon = Icons.Default.AccountBalanceWallet,
                    modifier = Modifier.weight(1f)
                ) {
                    showDepositDialog = true
                }
                ActionGridButton(
                    label = "MARKET",
                    icon = Icons.Default.Storefront,
                    modifier = Modifier.weight(1f)
                ) {
                    showWithdrawDialog = true
                }
                ActionGridButton(
                    label = "TASKS",
                    icon = Icons.Default.TaskAlt,
                    modifier = Modifier.weight(1f)
                ) { }
                ActionGridButton(
                    label = "REFERRAL",
                    icon = Icons.Default.Diversity3,
                    modifier = Modifier.weight(1f)
                ) { }
            }
        }

        // $5 Account Verification Required CTA (if unverified)
        if (user?.isAccountVerified == false) {
            item {
                GlassCard(
                    borderColor = WarningAmber.copy(alpha = 0.5f),
                    contentPadding = 16.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Verify",
                                    tint = WarningAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ACCOUNT VERIFICATION REQUIRED ($5)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = WarningAmber,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Complete one-time $5 USD verification to unlock all referral earnings & withdrawal features.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimaryDark,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = onVerifyClick,
                            colors = ButtonDefaults.buttonColors(containerColor = MetallicGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Pay $5", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // AI Financial Strategy Assistant Card
        item {
            GlassCard(
                borderColor = MetallicGold.copy(alpha = 0.3f),
                contentPadding = 16.dp
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Assistant",
                            tint = MetallicGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "BAIZPAY AI STRATEGY ASSISTANT",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MetallicGold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = aiAdvice,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimaryDark,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = aiQueryText,
                            onValueChange = { aiQueryText = it },
                            placeholder = { Text("Ask AI how to expand referral team...", fontSize = 11.sp, color = TextMutedDark) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (aiQueryText.isNotBlank()) {
                                    onAskAi(aiQueryText)
                                    aiQueryText = ""
                                }
                            },
                            enabled = !isAiLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MetallicGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = if (isAiLoading) "Thinking..." else "Ask", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Key Wallet Highlights
        item {
            Column {
                Text(
                    text = "EARNING STREAMS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextMutedDark,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(
                        title = "Task Income",
                        amount = "$${String.format("%.2f", wallet?.taskIncome ?: 0.0)}",
                        icon = Icons.Default.CheckCircle,
                        modifier = Modifier.weight(1f),
                        accentColor = SuccessGreen
                    )
                    StatCard(
                        title = "Marketplace Income",
                        amount = "$${String.format("%.2f", wallet?.marketplaceIncome ?: 0.0)}",
                        icon = Icons.Default.AddCard,
                        modifier = Modifier.weight(1f),
                        accentColor = MetallicGold
                    )
                }
            }
        }

        // Recent Transactions Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RECENT TRANSACTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextMutedDark,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "${transactions.size} records",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedDark
                )
            }
        }

        items(transactions.take(5)) { tx ->
            TransactionRowItem(tx)
        }
    }

    // Quick Deposit Modal
    if (showDepositDialog) {
        DepositModal(
            amount = depositAmountText,
            onAmountChange = { depositAmountText = it },
            onConfirm = {
                val amt = depositAmountText.toDoubleOrNull() ?: 100.0
                onDepositClick(amt, "Visa / Mastercard")
                showDepositDialog = false
            },
            onDismiss = { showDepositDialog = false }
        )
    }

    // Quick Withdraw Modal
    if (showWithdrawDialog) {
        WithdrawModal(
            amount = withdrawAmountText,
            onAmountChange = { withdrawAmountText = it },
            onConfirm = {
                val amt = withdrawAmountText.toDoubleOrNull() ?: 50.0
                onWithdrawClick(amt, "International Bank Wire")
                showWithdrawDialog = false
            },
            onDismiss = { showWithdrawDialog = false }
        )
    }
}

@Composable
fun ActionGridButton(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(GlassWhite05)
            .border(1.dp, GlassWhite05, RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MetallicGold,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimaryDark,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}


@Composable
fun QuickActionButton(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(DarkNavyCard)
                .border(1.dp, color.copy(alpha = 0.8f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontSize = 11.sp
        )
    }
}

@Composable
fun TransactionRowItem(tx: TransactionEntity) {
    GlassCard(
        cornerRadius = 14.dp,
        borderColor = GlassBorderGold,
        contentPadding = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val isPositive = tx.category.startsWith("REFERRAL") || tx.category == "SALARY" || tx.category == "DEPOSIT" || tx.category == "TASK"
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isPositive) SuccessGreen.copy(alpha = 0.2f) else WarningAmber.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                        contentDescription = "tx",
                        tint = if (isPositive) SuccessGreen else WarningAmber,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = tx.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Text(
                        text = tx.referenceNumber,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                val sign = if (tx.category == "WITHDRAWAL") "-" else "+"
                Text(
                    text = "$sign$${String.format("%.2f", tx.amount)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (tx.category == "WITHDRAWAL") WarningAmber else SuccessGreen,
                    fontSize = 14.sp
                )
                Text(
                    text = tx.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (tx.status == "COMPLETED") SuccessGreen else WarningAmber,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun DepositModal(
    amount: String,
    onAmountChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    GlassCard(
        modifier = Modifier.padding(24.dp),
        borderColor = BrightGold
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Deposit Funds to Wallet",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BrightGold
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text("Deposit Amount ($ USD)") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black)
                ) {
                    Text("Deposit Now", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun WithdrawModal(
    amount: String,
    onAmountChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    GlassCard(
        modifier = Modifier.padding(24.dp),
        borderColor = MetallicGold
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Request Withdrawal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BrightGold
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text("Withdrawal Amount ($ USD)") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black)
                ) {
                    Text("Submit Request", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
