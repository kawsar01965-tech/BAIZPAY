package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.models.MarketplaceProduct
import com.example.data.models.NotificationItem
import com.example.data.models.ReferralMember
import com.example.data.models.TaskItem
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.data.models.WalletBalances
import kotlinx.coroutines.flow.Flow

@Dao
interface BaizPayDao {

    // User Profile Queries
    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    fun getUserFlow(id: String = "USR_883921"): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    suspend fun getUserOnce(id: String = "USR_883921"): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    // Wallet Balances Queries
    @Query("SELECT * FROM wallet_balances WHERE id = 1 LIMIT 1")
    fun getWalletBalancesFlow(): Flow<WalletBalances?>

    @Query("SELECT * FROM wallet_balances WHERE id = 1 LIMIT 1")
    suspend fun getWalletBalancesOnce(): WalletBalances?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalletBalances(wallet: WalletBalances)

    // Transactions Queries
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: String)

    // Referral Members Queries
    @Query("SELECT * FROM referral_members ORDER BY level ASC, joinedDate DESC")
    fun getReferralMembersFlow(): Flow<List<ReferralMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferralMember(member: ReferralMember)

    // Tasks Queries
    @Query("SELECT * FROM tasks")
    fun getAllTasksFlow(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem)

    @Update
    suspend fun updateTask(task: TaskItem)

    // Marketplace Queries
    @Query("SELECT * FROM marketplace_products")
    fun getAllProductsFlow(): Flow<List<MarketplaceProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: MarketplaceProduct)

    // Notifications Queries
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotificationsFlow(): Flow<List<NotificationItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItem)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markNotificationRead(id: String)
}
