package com.kucingselfie.movieconsumer

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val TABLE_NAME = "movie"
        const val CODE_FAVORITE = 1
        private const val AUTHORITY = "com.kucingselfie.madecourse.provider"
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY).appendPath(TABLE_NAME).build()

    }

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieAdapter(null, this)
        rvMovie.adapter = adapter

        //init loader manager
        LoaderManager.getInstance(this).initLoader(CODE_FAVORITE, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return when (id) {
            CODE_FAVORITE -> CursorLoader(
                applicationContext,
                CONTENT_URI,
                null,
                null,
                null,
                null
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (loader.id == CODE_FAVORITE) {
            try {
                adapter.submit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (loader.id == CODE_FAVORITE) {
            adapter.submit(null)
        }
    }
}
