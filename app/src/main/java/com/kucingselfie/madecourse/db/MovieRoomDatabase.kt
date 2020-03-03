package com.kucingselfie.madecourse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movieDb.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

//        class MovieDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.movieDao())
//                    }
//                }
//            }
//
//            private suspend fun populateDatabase(movieDao: MovieDao) {
//                movieDao.deleteAllMovie()
//            }
//        }
    }
}