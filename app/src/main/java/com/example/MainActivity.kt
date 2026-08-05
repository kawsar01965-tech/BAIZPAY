package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopNavBar
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.EarnTasksScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MarketplaceScreen
import com.example.ui.screens.ProfileSecurityScreen
import com.example.ui.screens.ReferralSalaryScreen
import com.example.ui.screens.WalletAnalyticsScreen
import com.example.ui.theme.BaizPayTheme
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.BaizPayViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: BaizPayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BaizPayTheme {
                val user by viewModel.userState.collectAsStateWithLifecycle()
                val wallet by viewModel.walletState.collectAsStateWithLifecycle()
                val transactions by viewModel.transactionsState.collectAsStateWithLifecycle()
                val referralMembers by viewModel.referralMembersState.collectAsStateWithLifecycle()
                val tasks by viewModel.tasksState.collectAsStateWithLifecycle()
                val products by viewModel.productsState.collectAsStateWithLifecycle()
                val notifications by viewModel.notificationsState.collectAsStateWithLifecycle()
                val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
                val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
                val aiAdvice by viewModel.aiAdvice.collectAsStateWithLifecycle()
                val isAiLoading by viewModel.isAiLoading.collectAsStateWithLifecycle()
                val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

                val unreadNotifs = notifications.count { !it.isRead }
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(userMessage) {
                    userMessage?.let {
                        snackbarHostState.showSnackbar(it)
                        viewModel.clearMessage()
                    }
                }

                if (!isLoggedIn) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AuthScreen(
                            onLoginEmail = { emailOrUname, pass ->
                                viewModel.loginWithEmail(emailOrUname, pass)
                            },
                            onRegisterEmail = { email, uname, fName, lName, pass ->
                                viewModel.registerWithEmail(email, uname, fName, lName, pass)
                            },
                            onGoogleAuth = { viewModel.loginWithGoogle() },
                            onFacebookAuth = { viewModel.loginWithFacebook() },
                            onSkipDemo = { viewModel.loginWithEmail("demo_user", "123456") }
                        )

                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) { data ->
                            Snackbar(
                                containerColor = BrightGold,
                                contentColor = Color.Black,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                Text(
                                    text = data.visuals.message,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = DarkNavyBackground,
                        topBar = {
                            TopNavBar(
                                user = user,
                                unreadNotifCount = unreadNotifs,
                                isAdminActive = currentTab == AppTab.ADMIN,
                                onToggleAdmin = {
                                    if (currentTab == AppTab.ADMIN) {
                                        viewModel.setTab(AppTab.HOME)
                                    } else {
                                        viewModel.setTab(AppTab.ADMIN)
                                    }
                                },
                                onVerifyClick = { viewModel.verifyAccountFiveDollars() }
                            )
                        },
                        bottomBar = {
                            BottomNavBar(
                                currentTab = currentTab,
                                onTabSelected = { viewModel.setTab(it) }
                            )
                        },
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState) { data ->
                                Snackbar(
                                    containerColor = BrightGold,
                                    contentColor = Color.Black,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(
                                        text = data.visuals.message,
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (currentTab) {
                                AppTab.HOME -> HomeScreen(
                                    user = user,
                                    wallet = wallet,
                                    transactions = transactions,
                                    aiAdvice = aiAdvice,
                                    isAiLoading = isAiLoading,
                                    onVerifyClick = { viewModel.verifyAccountFiveDollars() },
                                    onDepositClick = { amt, method -> viewModel.depositFunds(amt, method) },
                                    onWithdrawClick = { amt, method -> viewModel.withdrawFunds(amt, method) },
                                    onAskAi = { query -> viewModel.askAiAssistant(query) }
                                )

                                AppTab.WALLET -> WalletAnalyticsScreen(
                                    wallet = wallet,
                                    transactions = transactions
                                )

                                AppTab.REFERRALS -> ReferralSalaryScreen(
                                    user = user,
                                    referralMembers = referralMembers,
                                    onClaimSalary = { viewModel.claimSalary() },
                                    onAddSimulatedReferral = { name, email, lvl ->
                                        viewModel.addSimulatedReferral(name, email, lvl)
                                    }
                                )

                                AppTab.EARN -> EarnTasksScreen(
                                    tasks = tasks,
                                    onClaimTask = { task -> viewModel.claimTaskReward(task) },
                                    onSpinWin = { amt ->
                                        viewModel.depositFunds(amt, "Lucky Wheel Win")
                                    },
                                    onScratchWin = { amt ->
                                        viewModel.depositFunds(amt, "Scratch Card Win")
                                    }
                                )

                                AppTab.MARKETPLACE -> MarketplaceScreen(
                                    products = products
                                )

                                AppTab.PROFILE -> ProfileSecurityScreen(
                                    user = user,
                                    onVerifyClick = { viewModel.verifyAccountFiveDollars() },
                                    onLogoutClick = { viewModel.logout() }
                                )

                                AppTab.ADMIN -> AdminPanelScreen(
                                    user = user,
                                    pendingTransactions = transactions,
                                    onApproveTransaction = { txId -> viewModel.adminApproveTransaction(txId) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
