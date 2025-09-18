package com.madarsoft.usermanagementapp.data.dio

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.madarsoft.usermanagementapp.data.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
