package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.GlassWhite05
import com.example.ui.theme.GlassWhite10
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.viewmodel.AppTab

private data class NavItemData(
    val tab: AppTab,
    val title: String,
    val icon: ImageVector
)

@Composable
fun BottomNavBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit
) {
    val navItems = listOf(
        NavItemData(AppTab.HOME, "DASHBOARD", Icons.Default.Home),
        NavItemData(AppTab.WALLET, "WALLET", Icons.Default.AccountBalanceWallet),
        NavItemData(AppTab.REFERRALS, "REFERRAL", Icons.Default.Groups),
        NavItemData(AppTab.EARN, "TASKS", Icons.Default.CardGiftcard),
        NavItemData(AppTab.MARKETPLACE, "MARKET", Icons.Default.Storefront),
        NavItemData(AppTab.PROFILE, "ACCOUNT", Icons.Default.Person)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkNavyBackground)
            .border(width = (0.8).dp, color = GlassWhite10)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEach { item ->
            val isSelected = currentTab == item.tab

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onTabSelected(item.tab) }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 44.dp, height = 28.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) MetallicGold.copy(alpha = 0.2f) else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) MetallicGold else TextMutedDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = item.title,
                    color = if (isSelected) TextPrimaryDark else TextMutedDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

