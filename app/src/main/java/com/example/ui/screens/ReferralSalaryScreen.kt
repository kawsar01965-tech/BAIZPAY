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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.models.ReferralMember
import com.example.data.models.SalaryRank
import com.example.data.models.UserEntity
import com.example.ui.components.GlassCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun ReferralSalaryScreen(
    user: UserEntity?,
    referralMembers: List<ReferralMember>,
    onClaimSalary: () -> Unit,
    onAddSimulatedReferral: (String, String, Int) -> Unit
) {
    var showAddMemberModal by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf(1) }

    val currentRank = SalaryRank.getRankForReferrals(user?.directReferralsCount ?: 0)
    val nextRank = SalaryRank.getNextRank(currentRank)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            Column {
                Text(
                    text = "5-Level Referral & Monthly Salary Program",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Text(
                    text = "Earn up to 5 levels deep + guaranteed rank monthly salary",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        // Monthly Salary Rank Progress Card
        item {
            GlassCard(
                borderColor = BrightGold,
                contentPadding = 20.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "CURRENT MONTHLY SALARY RANK",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray,
                                letterSpacing = 0.5.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "👑 ${currentRank.rankName}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Black,
                                    color = BrightGold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "($${currentRank.monthlySalary.toInt()}/mo)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        }

                        Button(
                            onClick = onClaimSalary,
                            colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(Icons.Default.Work, contentDescription = "Claim", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Claim Salary", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    if (nextRank != null) {
                        Spacer(modifier = Modifier.height(14.dp))
                        val currentCount = user?.directReferralsCount ?: 0
                        val targetCount = nextRank.requiredDirectRefs
                        val progress = (currentCount.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f)

                        Text(
                            text = "Next Rank: ${nextRank.rankName} ($currentCount / $targetCount Direct Referrals)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = BrightGold,
                            trackColor = DarkNavyCard
                        )
                        Text(
                            text = "Unlock $${nextRank.monthlySalary.toInt()}/month upon reaching ${nextRank.requiredDirectRefs} direct referrals",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        // 5-Level Commission Breakdown Table Card
        item {
            GlassCard(
                borderColor = GlassBorderGold
            ) {
                Column {
                    Text(
                        text = "5-Level Referral Commission Structure",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    LevelCommissionRow(1, "$2.00", "${user?.level1Count ?: 0} members")
                    LevelCommissionRow(2, "$0.50", "${user?.level2Count ?: 0} members")
                    LevelCommissionRow(3, "$0.25", "${user?.level3Count ?: 0} members")
                    LevelCommissionRow(4, "$0.15", "${user?.level4Count ?: 0} members")
                    LevelCommissionRow(5, "$0.10", "${user?.level5Count ?: 0} members")
                }
            }
        }

        // Referral QR Code & Link Sharing
        item {
            GlassCard(
                borderColor = MetallicGold
            ) {
                Column {
                    Text(
                        text = "Your Referral Link & QR Code",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Referral Code: ${user?.referralCode ?: "BAIZ-VANCE-99"}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "https://baizpay.com/ref/${user?.referralCode ?: "BAIZ-VANCE-99"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }

                        Row {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(RoyalBluePrimary)
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = BrightGold, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(DarkNavyCard)
                                    .border(1.dp, BrightGold, CircleShape)
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.QrCode, contentDescription = "QR", tint = BrightGold, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }

        // Team Network & Add Referral Action
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Referral Team Members (${referralMembers.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Button(
                    onClick = { showAddMemberModal = true },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary, contentColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = "Add", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Add Referral", fontSize = 11.sp)
                }
            }
        }

        items(referralMembers) { member ->
            ReferralMemberRow(member)
        }
    }

    if (showAddMemberModal) {
        GlassCard(
            modifier = Modifier.padding(24.dp),
            borderColor = BrightGold
        ) {
            Column {
                Text(
                    text = "Add Simulated Referral Member",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Member Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newEmail,
                    onValueChange = { newEmail = it },
                    label = { Text("Email Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("Select Referral Level:", style = MaterialTheme.typography.labelSmall, color = Color.White)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    (1..5).forEach { lvl ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedLevel == lvl) BrightGold else DarkNavyCard)
                                .clickable { selectedLevel = lvl }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("L$lvl", color = if (selectedLevel == lvl) Color.Black else Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { showAddMemberModal = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newName.isNotBlank()) {
                                onAddSimulatedReferral(newName, if (newEmail.isBlank()) "user@domain.com" else newEmail, selectedLevel)
                                showAddMemberModal = false
                                newName = ""
                                newEmail = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = Color.Black)
                    ) {
                        Text("Confirm Referral", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun LevelCommissionRow(level: Int, rewardStr: String, countStr: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(RoyalBluePrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "L$level", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Level $level Direct Commission", style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }

        Row {
            Text(text = rewardStr, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = countStr, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun ReferralMemberRow(member: ReferralMember) {
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
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(RoyalBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "L${member.level}", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = member.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "${member.email} • Joined ${member.joinedDate}", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "+$${String.format("%.2f", member.totalEarnedFromMember)}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                Text(text = if (member.isVerified) "Verified ($5)" else "Unverified", style = MaterialTheme.typography.labelSmall, color = if (member.isVerified) SuccessGreen else Color.Gray, fontSize = 10.sp)
            }
        }
    }
}
