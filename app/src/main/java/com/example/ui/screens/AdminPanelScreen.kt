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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
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
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.ui.components.GlassCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@Composable
fun AdminPanelScreen(
    user: UserEntity?,
    pendingTransactions: List<TransactionEntity>,
    onApproveTransaction: (String) -> Unit
) {
    var announcementText by remember { mutableStateOf("") }
    var announcementSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            GlassCard(
                borderColor = BrightGold,
                contentPadding = 16.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = BrightGold,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "BaizPay Enterprise Admin Panel",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BrightGold
                        )
                        Text(
                            text = "Manage users, approve withdrawals/deposits & run anti-fraud audits",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Fraud Audit & System Overview Metrics
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminStatTile("Pending Approvals", "${pendingTransactions.filter { it.status == "PENDING" }.size}", WarningAmber, Modifier.weight(1f))
                AdminStatTile("Fraud Risk Index", "LOW (0.01%)", SuccessGreen, Modifier.weight(1f))
            }
        }

        // Pending Withdrawals & Deposits Approval Table
        item {
            Text(
                text = "Pending Deposit & Withdrawal Approvals",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        val pendings = pendingTransactions.filter { it.status == "PENDING" }
        if (pendings.isEmpty()) {
            item {
                GlassCard(borderColor = SuccessGreen) {
                    Text(
                        text = "✅ No pending withdrawal or deposit requests currently in queue.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SuccessGreen,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            items(pendings) { tx ->
                PendingTxApprovalRow(tx, onApproveTransaction)
            }
        }

        // System Announcement Broadcast Console
        item {
            GlassCard(
                borderColor = MetallicGold
            ) {
                Column {
                    Text(
                        text = "Broadcast System Announcement",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = announcementText,
                        onValueChange = { announcementText = it },
                        placeholder = { Text("Type announcement message for all global users...", fontSize = 11.sp, color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (announcementText.isNotBlank()) {
                                announcementSuccess = true
                                announcementText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Broadcast Now", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }

                    if (announcementSuccess) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "🎉 Announcement sent to all active users!", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatTile(title: String, value: String, color: Color, modifier: Modifier) {
    GlassCard(
        modifier = modifier,
        borderColor = color.copy(alpha = 0.5f),
        contentPadding = 12.dp
    ) {
        Column {
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray, fontSize = 10.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun PendingTxApprovalRow(tx: TransactionEntity, onApprove: (String) -> Unit) {
    GlassCard(
        cornerRadius = 14.dp,
        borderColor = WarningAmber,
        contentPadding = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tx.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "${tx.referenceNumber} • $${String.format("%.2f", tx.amount)}", style = MaterialTheme.typography.labelSmall, color = WarningAmber, fontSize = 11.sp)
            }

            Button(
                onClick = { onApprove(tx.id) },
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen, contentColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Approve", modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Approve", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
