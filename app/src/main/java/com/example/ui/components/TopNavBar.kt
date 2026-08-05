package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.UserEntity
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassWhite05
import com.example.ui.theme.GlassWhite10
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.WarningAmber

@Composable
fun TopNavBar(
    user: UserEntity?,
    unreadNotifCount: Int,
    isAdminActive: Boolean,
    onToggleAdmin: () -> Unit,
    onVerifyClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkNavyBackground)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Header with Gold Ring Accent
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(MetallicGold, BrightGold)
                        )
                    )
                    .padding(1.5.dp)
                    .clip(CircleShape)
                    .background(DarkNavyCard),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (!user?.fullName.isNull_or_blank()) user!!.fullName.take(2).uppercase() else "BP",
                    color = MetallicGold,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "WELCOME BACK,",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedDark,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = user?.fullName ?: "Alexander Baiz",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark,
                    fontSize = 14.sp
                )
            }
        }

        // Action Icons & Account Status
        Row(verticalAlignment = Alignment.CenterVertically) {
            val isVerified = user?.isAccountVerified == true
            val statusColor = if (isVerified) SuccessGreen else WarningAmber

            // Verification Status Pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(GlassWhite05)
                    .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .clickable { if (!isVerified) onVerifyClick() }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isVerified) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = "Status",
                    tint = statusColor,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isVerified) "VERIFIED" else "VERIFY ($5)",
                    style = MaterialTheme.typography.labelSmall,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Circular Glass Action Button - Admin Mode
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(GlassWhite05)
                    .border(1.dp, GlassWhite10, CircleShape)
                    .clickable { onToggleAdmin() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin Mode",
                    tint = if (isAdminActive) BrightGold else TextMutedDark,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Circular Glass Action Button - Notification Bell
            BadgedBox(
                badge = {
                    if (unreadNotifCount > 0) {
                        Badge(containerColor = MetallicGold, contentColor = Color.Black) {
                            Text(text = unreadNotifCount.toString(), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(GlassWhite05)
                        .border(1.dp, GlassWhite10, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MetallicGold,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

private fun String?.isNull_or_blank(): Boolean {
    return this == null || this.isBlank()
}

