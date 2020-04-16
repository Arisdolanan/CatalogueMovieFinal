package com.hokagelab.www.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hokagelab.www.cataloguemovie.R;
import com.hokagelab.www.cataloguemovie.db.DatabaseContract;
import com.hokagelab.www.cataloguemovie.db.MovieHelper;
import com.hokagelab.www.cataloguemovie.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public static ArrayList<Movie> listFilm = new ArrayList<>();
    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;

    private static final String TAG = "Stack";

    private void getData(Context context){
        MovieHelper helper = new MovieHelper(context);
        helper.open();
        listFilm = helper.query();
        helper.close();
    }

    public StackRemoteViewsFactory(Context context, Intent intent){
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getData(context);
    }

    @Override
    public void onDataSetChanged() {
        getData(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.e("LIST FILM", "getCount: "+listFilm.size());
        return listFilm.size();
//        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

//        Bundle extras = new Bundle();
//        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//
//        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
//        return rv;

        Bitmap bmp = null;
        Movie movies = listFilm.get(position);


        String imgPath = "http://image.tmdb.org/t/p/w185/"+movies.getBackdrop();

        Log.e(TAG, "film apa: "+imgPath );

        try {

            bmp = Glide.with(context)
                    .load(imgPath)
                    .asBitmap()
                    .error(new ColorDrawable(context.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        rv.setImageViewBitmap(R.id.imgWidget, bmp);

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imgWidget, fillIntent);
        return rv;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
