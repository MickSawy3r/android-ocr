package com.sixbits.androvisionocv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sixbits.androvisionocv.entity.AttendanceLog;

import java.util.List;

import static com.sixbits.androvisionocv.entity.AttendanceLog.TABLE_NAME;

@Dao
public interface LogDao {

    @Insert
    void insert(AttendanceLog attendanceLogRecord);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(AttendanceLog attendanceLogRecord);

    @Delete
    void delete(AttendanceLog attendanceLogRecord);

    @Query("SELECT * FROM " + TABLE_NAME)
    LiveData<List<AttendanceLog>> getAllLogs();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id = :id LIMIT 1")
    AttendanceLog getLogById(String id);



}
