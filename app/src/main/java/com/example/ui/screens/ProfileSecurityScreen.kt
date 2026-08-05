package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun ProfileSecurityScreen(
    user: UserEntity?,
    onVerifyClick: () -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    var is2FAEnabled by remember { mutableStateOf(user?.twoFactorEnabled ?: true) }
    var showKycModal by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Profile & Security Center",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Text(
                    text = "KYC identity verification, 2FA security & account management",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        // Profile Details Card
        item {
            GlassCard(
                borderColor = BrightGold,
                contentPadding = 20.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(RoyalBluePrimary)
                            .border(2.dp, BrightGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "User", tint = BrightGold, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(text = user?.fullName ?: "Alexander Vance", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = user?.email ?: "a.vance@baizpay.com", style = MaterialTheme.typography.bodySmall, color = Color.Gray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "KYC", tint = SuccessGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "KYC ${user?.kycStatus ?: "VERIFIED"}", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // $5 Account Verification Status Card
        item {
            GlassCard(
                borderColor = if (user?.isAccountVerified == true) SuccessGreen else WarningAmber
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "USD $5 Account Verification Status",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = BrightGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (user?.isAccountVerified == true)
                                "Your $5 USD verification fee is paid and active."
                            else
                                "Verification required to activate referral earnings.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }

                    if (user?.isAccountVerified == false) {
                        Button(
                            onClick = onVerifyClick,
                            colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Pay $5 Now", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(SuccessGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Verified ✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Security Settings Card
        item {
            GlassCard(
                borderColor = GlassBorderGold
            ) {
                Column {
                    Text(
                        text = "Enterprise Security Settings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Two-Factor Authentication (2FA)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "Google Authenticator / SMS OTP", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 11.sp)
                        }
                        Switch(
                            checked = is2FAEnabled,
                            onCheckedChange = { is2FAEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = BrightGold, checkedTrackColor = RoyalBluePrimary)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Device Verification & Audit Logs", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "Active session: Android Device #8821", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 11.sp)
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkNavyCard, contentColor = BrightGold),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("View Logs", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Support & Help Center Card
        item {
            GlassCard(
                borderColor = MetallicGold
            ) {
                Column {
                    Text(
                        text = "24/7 VIP Live Support & Ticket System",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Connected with BaizPay support specialists for instant resolution.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary, contentColor = Color.White),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.SupportAgent, contentDescription = "Live", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Live Chat", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Submit Ticket", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Account Logout / Switch Account Card
        item {
            GlassCard(
                borderColor = Color(0xFFEF4444).copy(alpha = 0.4f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Account Session",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Signed in as ${user?.username ?: "user"} (${user?.authProvider ?: "EMAIL"})",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444), contentColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Log Out", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
