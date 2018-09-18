package com.android.fxy.simplemediaclips.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;

@Database(entities = {MediaInfo.class}, version = 1)
public abstract class MediaInfoDatabase extends RoomDatabase {
    private static final String TAG = "MediaInfoDatabase";

    private static volatile MediaInfoDatabase INSTANCE;

    public abstract MediaInfoDao mediaInfoDao();

    public static MediaInfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MediaInfoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MediaInfoDatabase.class, "media_info")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    LogUtils.d(TAG, "onOpen");
                }

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    LogUtils.d(TAG, "onCreate");

                }
            };

}
