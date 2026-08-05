package com.example.data.repository

import com.example.data.db.BaizPayDao
import com.example.data.models.MarketplaceProduct
import com.example.data.models.NotificationItem
import com.example.data.models.ReferralMember
import com.example.data.models.SalaryRank
import com.example.data.models.TaskItem
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.data.models.WalletBalances
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.UUID

class BaizPayRepository(private val dao: BaizPayDao) {

    val userFlow: Flow<UserEntity?> = dao.getUserFlow()
    val walletBalancesFlow: Flow<WalletBalances?> = dao.getWalletBalancesFlow()
    val transactionsFlow: Flow<List<TransactionEntity>> = dao.getAllTransactionsFlow()
    val referralMembersFlow: Flow<List<ReferralMember>> = dao.getReferralMembersFlow()
    val tasksFlow: Flow<List<TaskItem>> = dao.getAllTasksFlow()
    val productsFlow: Flow<List<MarketplaceProduct>> = dao.getAllProductsFlow()
    val notificationsFlow: Flow<List<NotificationItem>> = dao.getAllNotificationsFlow()

    suspend fun seedInitialDataIfEmpty() {
        if (dao.getUserOnce() == null) {
            val defaultUser = UserEntity(
                userId = "USR_883921",
                fullName = "Alexander Vance",
                email = "a.vance@baizpay.com",
                phoneNumber = "+1 (555) 234-8900",
                isAccountVerified = true,
                kycStatus = "VERIFIED",
                twoFactorEnabled = true,
                referralCode = "BAIZ-VANCE-99",
                referredBy = "BAIZ-GLOBAL-01",
                currentSalaryRank = "Crown",
                directReferralsCount = 1240,
                totalTeamCount = 8940,
                level1Count = 1240,
                level2Count = 2850,
                level3Count = 2410,
                level4Count = 1520,
                level5Count = 920,
                isAdmin = true
            )
            dao.insertUser(defaultUser)
        }

        if (dao.getWalletBalancesOnce() == null) {
            val defaultWallet = WalletBalances(
                id = 1,
                totalBalance = 15840.50,
                availableBalance = 12450.20,
                pendingBalance = 890.30,
                referralIncome = 6250.00,
                salaryIncome = 3500.00,
                taskIncome = 1240.50,
                marketplaceIncome = 1820.00,
                depositBalance = 2500.00,
                withdrawableBalance = 11200.00,
                bonusBalance = 550.00,
                cashbackBalance = 280.00
            )
            dao.insertWalletBalances(defaultWallet)
        }

        val existingTx = dao.getAllTransactionsFlow().first()
        if (existingTx.isEmpty()) {
            val initialTxList = listOf(
                TransactionEntity(
                    id = "TX_" + UUID.randomUUID().toString().take(8),
                    title = "Crown Rank Monthly Salary Payout",
                    amount = 3500.00,
                    category = "SALARY",
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 86400000L * 2,
                    referenceNumber = "REF-SLR-9981"
                ),
                TransactionEntity(
                    id = "TX_" + UUID.randomUUID().toString().take(8),
                    title = "Level 1 Referral Commission - Marcus B.",
                    amount = 2.00,
                    category = "REFERRAL_L1",
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 86400000L * 3,
                    referenceNumber = "REF-L1-4412"
                ),
                TransactionEntity(
                    id = "TX_" + UUID.randomUUID().toString().take(8),
                    title = "Deposit via Visa Gold Card",
                    amount = 1000.00,
                    category = "DEPOSIT",
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 86400000L * 5,
                    referenceNumber = "DEP-VISA-1082"
                ),
                TransactionEntity(
                    id = "TX_" + UUID.randomUUID().toString().take(8),
                    title = "Lucky Wheel Spin Reward",
                    amount = 25.00,
                    category = "TASK",
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 3600000L * 4,
                    referenceNumber = "TSK-SPN-7721"
                ),
                TransactionEntity(
                    id = "TX_" + UUID.randomUUID().toString().take(8),
                    title = "Withdrawal to Bank Account",
                    amount = 500.00,
                    category = "WITHDRAWAL",
                    status = "PENDING",
                    timestamp = System.currentTimeMillis() - 3600000L * 1,
                    referenceNumber = "WTH-BNK-3021"
                )
            )
            initialTxList.forEach { dao.insertTransaction(it) }
        }

        val existingMembers = dao.getReferralMembersFlow().first()
        if (existingMembers.isEmpty()) {
            val sampleMembers = listOf(
                ReferralMember("REF_101", "Marcus Bennett", "marcus@domain.com", 1, "2026-07-28", true, 480.00, 18),
                ReferralMember("REF_102", "Elena Rostova", "elena@domain.com", 1, "2026-07-29", true, 620.00, 24),
                ReferralMember("REF_103", "David Chen", "david.c@domain.com", 2, "2026-07-30", true, 150.00, 8),
                ReferralMember("REF_104", "Sophia Martinez", "sophia@domain.com", 2, "2026-08-01", true, 210.00, 12),
                ReferralMember("REF_105", "Tariq Al-Mansoor", "tariq@domain.com", 3, "2026-08-02", true, 95.00, 5),
                ReferralMember("REF_106", "Chloe Dubois", "chloe@domain.com", 4, "2026-08-03", true, 45.00, 2),
                ReferralMember("REF_107", "Kenji Sato", "kenji@domain.com", 5, "2026-08-04", true, 30.00, 1)
            )
            sampleMembers.forEach { dao.insertReferralMember(it) }
        }

        val existingTasks = dao.getAllTasksFlow().first()
        if (existingTasks.isEmpty()) {
            val sampleTasks = listOf(
                TaskItem("TSK_01", "Daily Check-in Bonus", "Claim your continuous daily login reward", "DAILY", 5.00, 1, 1, false, "check_circle"),
                TaskItem("TSK_02", "Lucky Wheel Spin", "Spin the gold wheel to win up to $100", "SPIN", 25.00, 0, 1, false, "star"),
                TaskItem("TSK_03", "Golden Scratch Card", "Scratch to match luxury icons", "SCRATCH", 15.00, 0, 1, false, "card_giftcard"),
                TaskItem("TSK_04", "Watch Sponsored FinTech Video", "Watch 30sec video on global wealth trends", "VIDEO", 3.50, 0, 1, false, "play_circle"),
                TaskItem("TSK_05", "Complete FinTech Survey", "Answer 5 quick market insights questions", "SURVEY", 10.00, 0, 1, false, "assignment"),
                TaskItem("TSK_06", "BaizPay Security Quiz", "Test your knowledge on 2FA & wallet protection", "QUIZ", 12.00, 0, 1, false, "quiz")
            )
            sampleTasks.forEach { dao.insertTask(it) }
        }

        val existingProducts = dao.getAllProductsFlow().first()
        if (existingProducts.isEmpty()) {
            val sampleProducts = listOf(
                MarketplaceProduct("PRD_01", "VIP Crypto Hardware Wallet", "Military-grade hardware wallet with BaizPay auto-sync", 199.00, "Electronics", "", "BaizPay Global Store", 4.9f, 312, 10.0, true, true),
                MarketplaceProduct("PRD_02", "Annual Financial Masterclass", "1-on-1 strategy coaching with top wealth advisors", 499.00, "Digital Goods", "", "Vance Advisory Group", 5.0f, 88, 15.0, true, true),
                MarketplaceProduct("PRD_03", "$100 Amazon Gold E-Gift Card", "Instant delivery e-gift card with 5% cashback", 100.00, "Gift Cards", "", "Instant Rewards Inc", 4.8f, 1200, 5.0, true, false),
                MarketplaceProduct("PRD_04", "Global Priority Debit Card", "Solid stainless steel metallic physical debit card", 79.00, "Financial Tools", "", "BaizPay Issuing", 4.9f, 540, 8.0, true, true)
            )
            sampleProducts.forEach { dao.insertProduct(it) }
        }

        val existingNotifs = dao.getAllNotificationsFlow().first()
        if (existingNotifs.isEmpty()) {
            val sampleNotifs = listOf(
                NotificationItem("NOTIF_01", "Crown Rank Achieved!", "Congratulations! You reached Crown Rank with 1000+ referrals.", "10 mins ago", "SALARY", false),
                NotificationItem("NOTIF_02", "Deposit Received", "Deposit of $1,000.00 has been credited to your wallet.", "2 hours ago", "DEPOSIT", false),
                NotificationItem("NOTIF_03", "Level 1 Commission", "Earned $2.00 from Marcus Bennett's account verification.", "Yesterday", "REFERRAL", true)
            )
            sampleNotifs.forEach { dao.insertNotification(it) }
        }
    }

    suspend fun verifyAccountFiveDollars(): Boolean {
        val user = dao.getUserOnce() ?: return false
        val wallet = dao.getWalletBalancesOnce() ?: return false
        if (user.isAccountVerified) return true

        if (wallet.availableBalance < 5.0) return false

        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance - 5.0,
            availableBalance = wallet.availableBalance - 5.0
        )
        dao.insertWalletBalances(updatedWallet)

        val updatedUser = user.copy(isAccountVerified = true)
        dao.insertUser(updatedUser)

        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "Account Verification Fee ($5 USD)",
                amount = 5.00,
                category = "WITHDRAWAL",
                status = "COMPLETED",
                referenceNumber = "VER-USD5-" + (1000..9999).random()
            )
        )
        return true
    }

    suspend fun processDeposit(amount: Double, paymentMethod: String) {
        val wallet = dao.getWalletBalancesOnce() ?: return
        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance + amount,
            availableBalance = wallet.availableBalance + amount,
            depositBalance = wallet.depositBalance + amount
        )
        dao.insertWalletBalances(updatedWallet)

        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "Deposit via $paymentMethod",
                amount = amount,
                category = "DEPOSIT",
                status = "COMPLETED",
                referenceNumber = "DEP-" + (10000..99999).random()
            )
        )
        dao.insertNotification(
            NotificationItem(
                id = "NT_" + UUID.randomUUID().toString().take(8),
                title = "Deposit Successful",
                message = "Successfully deposited $${String.format("%.2f", amount)} via $paymentMethod.",
                timestamp = "Just now",
                type = "DEPOSIT"
            )
        )
    }

    suspend fun processWithdrawal(amount: Double, destination: String): Boolean {
        val wallet = dao.getWalletBalancesOnce() ?: return false
        if (wallet.availableBalance < amount || wallet.withdrawableBalance < amount) return false

        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance - amount,
            availableBalance = wallet.availableBalance - amount,
            withdrawableBalance = wallet.withdrawableBalance - amount,
            pendingBalance = wallet.pendingBalance + amount
        )
        dao.insertWalletBalances(updatedWallet)

        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "Withdrawal Request to $destination",
                amount = amount,
                category = "WITHDRAWAL",
                status = "PENDING",
                referenceNumber = "WTH-" + (10000..99999).random()
            )
        )
        dao.insertNotification(
            NotificationItem(
                id = "NT_" + UUID.randomUUID().toString().take(8),
                title = "Withdrawal Submitted",
                message = "Withdrawal request for $${String.format("%.2f", amount)} is pending approval.",
                timestamp = "Just now",
                type = "WITHDRAWAL"
            )
        )
        return true
    }

    suspend fun claimTaskReward(task: TaskItem): Boolean {
        val wallet = dao.getWalletBalancesOnce() ?: return false
        val updatedTask = task.copy(isCompleted = true, progress = task.totalSteps)
        dao.updateTask(updatedTask)

        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance + task.rewardAmount,
            availableBalance = wallet.availableBalance + task.rewardAmount,
            taskIncome = wallet.taskIncome + task.rewardAmount,
            withdrawableBalance = wallet.withdrawableBalance + task.rewardAmount
        )
        dao.insertWalletBalances(updatedWallet)

        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "Task Reward: ${task.title}",
                amount = task.rewardAmount,
                category = "TASK",
                status = "COMPLETED",
                referenceNumber = "TSK-" + (10000..99999).random()
            )
        )
        return true
    }

    suspend fun claimSalary(): Boolean {
        val user = dao.getUserOnce() ?: return false
        val wallet = dao.getWalletBalancesOnce() ?: return false
        val rank = SalaryRank.getRankForReferrals(user.directReferralsCount)
        val salaryAmount = rank.monthlySalary

        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance + salaryAmount,
            availableBalance = wallet.availableBalance + salaryAmount,
            salaryIncome = wallet.salaryIncome + salaryAmount,
            withdrawableBalance = wallet.withdrawableBalance + salaryAmount
        )
        dao.insertWalletBalances(updatedWallet)

        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "${rank.rankName} Rank Monthly Salary Payout",
                amount = salaryAmount,
                category = "SALARY",
                status = "COMPLETED",
                referenceNumber = "SLR-" + (10000..99999).random()
            )
        )
        dao.insertNotification(
            NotificationItem(
                id = "NT_" + UUID.randomUUID().toString().take(8),
                title = "Monthly Salary Credited!",
                message = "Your ${rank.rankName} rank monthly salary of $${String.format("%.2f", salaryAmount)} has been credited.",
                timestamp = "Just now",
                type = "SALARY"
            )
        )
        return true
    }

    suspend fun addNewReferralMember(name: String, email: String, level: Int) {
        val user = dao.getUserOnce() ?: return
        val wallet = dao.getWalletBalancesOnce() ?: return

        val commAmount = when (level) {
            1 -> 2.00
            2 -> 0.50
            3 -> 0.25
            4 -> 0.15
            5 -> 0.10
            else -> 0.00
        }

        val newMember = ReferralMember(
            id = "REF_" + UUID.randomUUID().toString().take(6),
            name = name,
            email = email,
            level = level,
            joinedDate = "Today",
            isVerified = true,
            totalEarnedFromMember = commAmount
        )
        dao.insertReferralMember(newMember)

        val newDirects = if (level == 1) user.directReferralsCount + 1 else user.directReferralsCount
        val newRank = SalaryRank.getRankForReferrals(newDirects).rankName

        val updatedUser = user.copy(
            directReferralsCount = newDirects,
            totalTeamCount = user.totalTeamCount + 1,
            currentSalaryRank = newRank,
            level1Count = if (level == 1) user.level1Count + 1 else user.level1Count,
            level2Count = if (level == 2) user.level2Count + 1 else user.level2Count,
            level3Count = if (level == 3) user.level3Count + 1 else user.level3Count,
            level4Count = if (level == 4) user.level4Count + 1 else user.level4Count,
            level5Count = if (level == 5) user.level5Count + 1 else user.level5Count
        )
        dao.insertUser(updatedUser)

        val updatedWallet = wallet.copy(
            totalBalance = wallet.totalBalance + commAmount,
            availableBalance = wallet.availableBalance + commAmount,
            referralIncome = wallet.referralIncome + commAmount,
            withdrawableBalance = wallet.withdrawableBalance + commAmount
        )
        dao.insertWalletBalances(updatedWallet)

        val catName = "REFERRAL_L$level"
        dao.insertTransaction(
            TransactionEntity(
                id = "TX_" + UUID.randomUUID().toString().take(8),
                title = "Level $level Referral Bonus - $name",
                amount = commAmount,
                category = catName,
                status = "COMPLETED",
                referenceNumber = "REF-L$level-" + (1000..9999).random()
            )
        )
    }

    suspend fun adminApproveTransaction(txId: String) {
        val transactions = dao.getAllTransactionsFlow().first()
        val tx = transactions.find { it.id == txId } ?: return
        val wallet = dao.getWalletBalancesOnce() ?: return

        if (tx.status == "PENDING" && tx.category == "WITHDRAWAL") {
            val updatedTx = tx.copy(status = "COMPLETED")
            dao.insertTransaction(updatedTx)

            val updatedWallet = wallet.copy(
                pendingBalance = (wallet.pendingBalance - tx.amount).coerceAtLeast(0.0)
            )
            dao.insertWalletBalances(updatedWallet)
        }
    }

    suspend fun registerUserWithEmail(
        email: String,
        username: String,
        firstName: String,
        lastName: String
    ): UserEntity {
        val fullName = "$firstName $lastName".trim()
        val newUser = UserEntity(
            userId = "USR_" + UUID.randomUUID().toString().take(6).uppercase(),
            fullName = if (fullName.isNotBlank()) fullName else username,
            firstName = firstName,
            lastName = lastName,
            username = username,
            email = email,
            authProvider = "EMAIL",
            phoneNumber = "+1 (555) 000-0000",
            isAccountVerified = false,
            kycStatus = "UNVERIFIED",
            twoFactorEnabled = false,
            referralCode = "BAIZ-${username.uppercase().take(6)}-88",
            currentSalaryRank = "Silver",
            directReferralsCount = 0,
            totalTeamCount = 0,
            isAdmin = false
        )
        dao.insertUser(newUser)
        return newUser
    }

    suspend fun loginOrRegisterOAuthUser(
        email: String,
        displayName: String,
        provider: String // "GOOGLE" or "FACEBOOK"
    ): UserEntity {
        val nameParts = displayName.trim().split(" ")
        val fName = nameParts.firstOrNull() ?: displayName
        val lName = if (nameParts.size > 1) nameParts.subList(1, nameParts.size).joinToString(" ") else ""
        val uname = email.substringBefore("@").replace(".", "_")

        val user = UserEntity(
            userId = "USR_" + UUID.randomUUID().toString().take(6).uppercase(),
            fullName = displayName,
            firstName = fName,
            lastName = lName,
            username = uname,
            email = email,
            authProvider = provider,
            phoneNumber = "+1 (555) 123-4567",
            isAccountVerified = true,
            kycStatus = "VERIFIED",
            twoFactorEnabled = false,
            referralCode = "BAIZ-${uname.uppercase().take(6)}-99",
            currentSalaryRank = "Silver",
            directReferralsCount = 0,
            totalTeamCount = 0,
            isAdmin = false
        )
        dao.insertUser(user)
        return user
    }
}
