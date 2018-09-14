package com.android.fxy.simplemediaclips.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.fxy.simplemediaclips.model.MediaInfo;

import java.util.List;

@Dao
public interface MediaInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MediaInfo ifo);

    @Query("SELECT * from media_info ORDER BY id ")
    LiveData<List<MediaInfo>> getAllMediaInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MediaInfo... mediainfo);

    @Query("DELETE FROM media_info")
    void deleteAll();
}
