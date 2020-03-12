package com.kucingselfie.madecourse.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kucingselfie.madecourse.MovieBannerWidget
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.common.BASE_URL_IMAGE
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import timber.log.Timber


class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = mutableListOf<MovieEntity>()
    private lateinit var db: MovieRoomDatabase

    override fun onCreate() {
        db = Room.databaseBuilder(mContext.applicationContext, MovieRoomDatabase::class.java, "movieDb.db")
            .allowMainThreadQueries().build()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        mWidgetItems.clear()
        val movies = db.movieDao().getFavorites()
        mWidgetItems.addAll(movies)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        val favorite = mWidgetItems[position]

        try {
            val bitmap: Bitmap = Glide
                .with(mContext)
                .asBitmap()
                .load(BASE_URL_IMAGE + favorite.posterPath)
                .apply(RequestOptions().fitCenter())
                .submit()
                .get()
            rv.setImageViewBitmap(R.id.imageWidget, bitmap)
        } catch (e: Exception) {
            Timber.v("BITMAP_ERROR ${e.message}")
        }

        val extras = bundleOf(
            MovieBannerWidget.EXTRA_ITEM to favorite.id
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageWidget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}