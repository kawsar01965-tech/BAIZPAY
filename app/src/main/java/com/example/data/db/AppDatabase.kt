package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.models.MarketplaceProduct
import com.example.data.models.NotificationItem
import com.example.data.models.ReferralMember
import com.example.data.models.TaskItem
import com.example.data.models.TransactionEntity
import com.example.data.models.UserEntity
import com.example.data.models.WalletBalances

@Database(
    entities = [
        UserEntity::class,
        WalletBalances::class,
        TransactionEntity::class,
        ReferralMember::class,
        TaskItem::class,
        MarketplaceProduct::class,
        NotificationItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun baizPayDao(): BaizPayDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "baizpay_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
