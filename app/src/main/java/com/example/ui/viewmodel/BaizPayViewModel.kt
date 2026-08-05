package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.GeminiFinancialAssistant
import com.example.data.db.AppDatabase
import com.example.data.models.MarketplaceProduct
import com.example.data.models.NotificationItem
import com.example.data.models.ReferralMember
import com.example.data.models.TaskItem
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.data.models.WalletBalances
import com.example.data.repository.BaizPayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    HOME,
    WALLET,
    REFERRALS,
    EARN,
    MARKETPLACE,
    PROFILE,
    ADMIN
}

class BaizPayViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = BaizPayRepository(db.baizPayDao())
    private val aiAssistant = GeminiFinancialAssistant()

    val userState: StateFlow<UserEntity?> = repository.userFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val walletState: StateFlow<WalletBalances?> = repository.walletBalancesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val transactionsState: StateFlow<List<TransactionEntity>> = repository.transactionsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val referralMembersState: StateFlow<List<ReferralMember>> = repository.referralMembersFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasksState: StateFlow<List<TaskItem>> = repository.tasksFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productsState: StateFlow<List<MarketplaceProduct>> = repository.productsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notificationsState: StateFlow<List<NotificationItem>> = repository.notificationsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _aiAdvice = MutableStateFlow("💡 AI Advisor: Reaching the next salary rank increases your guaranteed monthly payout!")
    val aiAdvice: StateFlow<String> = _aiAdvice.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    fun loginWithEmail(emailOrUsername: String, password: String): Boolean {
        if (emailOrUsername.isBlank() || password.isBlank()) {
            _userMessage.value = "⚠️ Please fill in all login fields."
            return false
        }
        _isLoggedIn.value = true
        _userMessage.value = "✅ Welcome back! Successfully signed in."
        return true
    }

    fun registerWithEmail(
        email: String,
        username: String,
        firstName: String,
        lastName: String,
        password: String
    ): Boolean {
        if (email.isBlank() || username.isBlank() || firstName.isBlank() || lastName.isBlank() || password.isBlank()) {
            _userMessage.value = "⚠️ Please fill in all registration fields."
            return false
        }
        viewModelScope.launch {
            repository.registerUserWithEmail(email, username, firstName, lastName)
            _isLoggedIn.value = true
            _userMessage.value = "🎉 Account created successfully! Welcome, $firstName!"
        }
        return true
    }

    fun loginWithGoogle() {
        viewModelScope.launch {
            val googleEmail = "user.google@gmail.com"
            val googleName = "Alexander Baiz"
            repository.loginOrRegisterOAuthUser(googleEmail, googleName, "GOOGLE")
            _isLoggedIn.value = true
            _userMessage.value = "🌐 Successfully signed in with Google Account!"
        }
    }

    fun loginWithFacebook() {
        viewModelScope.launch {
            val fbEmail = "user.fb@facebook.com"
            val fbName = "Alexander Baiz"
            repository.loginOrRegisterOAuthUser(fbEmail, fbName, "FACEBOOK")
            _isLoggedIn.value = true
            _userMessage.value = "📘 Successfully signed in with Facebook Account!"
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _userMessage.value = "🔒 Logged out successfully."
    }

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    fun verifyAccountFiveDollars() {
        viewModelScope.launch {
            val success = repository.verifyAccountFiveDollars()
            if (success) {
                _userMessage.value = "🎉 Account Verified Successfully ($5 USD Fee Paid)!"
            } else {
                _userMessage.value = "❌ Verification Failed: Insufficient Available Balance ($5.00 required)."
            }
        }
    }

    fun depositFunds(amount: Double, method: String) {
        viewModelScope.launch {
            repository.processDeposit(amount, method)
            _userMessage.value = "💰 Deposit of $${String.format("%.2f", amount)} submitted successfully!"
        }
    }

    fun withdrawFunds(amount: Double, destination: String) {
        viewModelScope.launch {
            val success = repository.processWithdrawal(amount, destination)
            if (success) {
                _userMessage.value = "📤 Withdrawal request for $${String.format("%.2f", amount)} submitted for processing."
            } else {
                _userMessage.value = "❌ Withdrawal Failed: Insufficient Available / Withdrawable Balance."
            }
        }
    }

    fun claimTaskReward(task: TaskItem) {
        viewModelScope.launch {
            val success = repository.claimTaskReward(task)
            if (success) {
                _userMessage.value = "🌟 Task Completed! Earned $${String.format("%.2f", task.rewardAmount)}."
            }
        }
    }

    fun claimSalary() {
        viewModelScope.launch {
            val success = repository.claimSalary()
            if (success) {
                _userMessage.value = "🏆 Monthly Rank Salary claimed and credited to your wallet!"
            }
        }
    }

    fun addSimulatedReferral(name: String, email: String, level: Int) {
        viewModelScope.launch {
            repository.addNewReferralMember(name, email, level)
            val commission = when (level) {
                1 -> 2.00
                2 -> 0.50
                3 -> 0.25
                4 -> 0.15
                5 -> 0.10
                else -> 0.00
            }
            _userMessage.value = "👥 New Level $level Referral Added! Earned $${String.format("%.2f", commission)} commission."
        }
    }

    fun askAiAssistant(query: String) {
        viewModelScope.launch {
            _isAiLoading.value = true
            val wallet = walletState.value
            val user = userState.value
            val totalBal = wallet?.totalBalance ?: 0.0
            val refEarnings = wallet?.referralIncome ?: 0.0
            val rank = user?.currentSalaryRank ?: "Bronze"

            val advice = aiAssistant.getFinancialAdvice(totalBal, refEarnings, rank, query)
            _aiAdvice.value = advice
            _isAiLoading.value = false
        }
    }

    fun adminApproveTransaction(txId: String) {
        viewModelScope.launch {
            repository.adminApproveTransaction(txId)
            _userMessage.value = "✅ Transaction $txId Approved by Admin."
        }
    }
}
