package barqsoft.footballscores.service;


import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.widget.DetailWidgetProvider;

/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = DetailWidgetRemoteViewsService.class.getSimpleName();
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_MATCHTIME = 2;
    public static final int COL_ID = 8;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LOG_TAG,"onGetViewFactory");
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                Date fragmentdate = new Date(System.currentTimeMillis());
                Uri ScoresUri = DatabaseContract.scores_table.buildScoreWithDate();
                SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
                String[] dates = {mformat.format(fragmentdate)};
                data = getContentResolver().query(ScoresUri, null, null, dates, null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_scores_list_item);

                views.setTextViewText(R.id.home_name, data.getString(COL_HOME));
                views.setTextViewText(R.id.score_textview, Utilies.getScores(data.getInt(COL_HOME_GOALS), data.getInt(COL_AWAY_GOALS)));
                views.setTextViewText(R.id.data_textview, data.getString(COL_MATCHTIME));
                views.setTextViewText(R.id.away_name, data.getString(COL_AWAY));
               // views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(data.getString(COL_HOME)));
             //   views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(data.getString(COL_AWAY)));

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(DetailWidgetProvider.ITEM_ID, data.getString(COL_ID));

                views.setOnClickFillInIntent(R.id.widget_detail_list_item, fillInIntent);
                return views;
            }
            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(COL_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}