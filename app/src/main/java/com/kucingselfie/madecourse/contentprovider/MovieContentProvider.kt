package com.kucingselfie.madecourse.contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.room.RoomMasterTable.TABLE_NAME
import com.kucingselfie.madecourse.common.DB_NAME
import com.kucingselfie.madecourse.common.DB_TABLE
import com.kucingselfie.madecourse.db.MovieDao
import com.kucingselfie.madecourse.db.MovieRoomDatabase

class MovieContentProvider : ContentProvider() {

    private lateinit var db: MovieRoomDatabase
    private lateinit var dao: MovieDao

    companion object {
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val AUTHORITY = "com.kucingselfie.madecourse.provider"
        private const val MOVIE = 1
        private const val MOVIE_ID = 2

        init {
            sUriMatcher.addURI(
                AUTHORITY, DB_TABLE,
                MOVIE
            )
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                MOVIE_ID
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun onCreate(): Boolean {
        db = Room.databaseBuilder(context as Context, MovieRoomDatabase::class.java, DB_NAME).build()
        dao = db.movieDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val code = sUriMatcher.match(uri)

        val cursor: Cursor?

        cursor = when(code) {
            MOVIE -> dao.selectMovies()
            MOVIE_ID -> dao.selectMovieById((ContentUris.parseId(uri)))
            else -> null
        }

        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = 0
}
