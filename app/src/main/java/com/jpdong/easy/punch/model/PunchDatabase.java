package com.jpdong.easy.punch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jpdong.easy.punch.PunchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2017/4/10.
 */

public class PunchDatabase {

    private static final String SP_NAME = "nagrand";

    private DataBaseHelper mDataBaseHelper;
    private Context mContext;

    public PunchDatabase(Context context) {
        mDataBaseHelper = DataBaseHelper.getInstance(context);
        mContext = context;
    }

    public List<PunchItem> punchItems() {
        List<PunchItem> punchItems = new ArrayList<>();
        SQLiteDatabase database = mDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseHelper.PUNCH_TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String lastTime = cursor.getString(cursor.getColumnIndex("last_time"));
            int duration = cursor.getInt(cursor.getColumnIndex("duration"));
            punchItems.add(new PunchItem(id, title, duration,Long.valueOf(lastTime)));
        }
        cursor.close();
        return punchItems;
    }

    public List<Long> punchTimes(String id) {
        List<Long> punchTimes = new ArrayList<>();
        SQLiteDatabase database = mDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseHelper.PUNCH_TIMES_TABLE_NAME, null, "id like '" + id + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            String punchTime = cursor.getString(cursor.getColumnIndex("punch_time"));
            punchTimes.add(Long.valueOf(punchTime));
        }
        cursor.close();
        return punchTimes;
    }

    public boolean savePunch(PunchItem punchItem) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        String id = punchItem.getId();
        String title = punchItem.title;
        int duration = punchItem.duration;
        String lastTime = String.valueOf(punchItem.lastTime);
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("title", title);
        values.put("duration", duration);
        values.put("last_time", lastTime);
        //return database.insertWithOnConflict(DataBaseHelper.VIDEO_TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_IGNORE) != -1;
        return database.replace(DataBaseHelper.PUNCH_TABLE_NAME, null, values) != -1;
    }

    public boolean savePunchTime(String id, long punchTime) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("punch_time", punchTime);
        //return database.insertWithOnConflict(DataBaseHelper.VIDEO_TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_IGNORE) != -1;
        return database.replace(DataBaseHelper.PUNCH_TIMES_TABLE_NAME, null, values) != -1;
    }

    /*public boolean saveVideoInfo(String fileName) {
        SQLiteDatabase database = mDataBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", fileName);
        return database.insertWithOnConflict(DataBaseHelper.VIDEO_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE) != -1;

    }

    public boolean updateVideoInfo(VideoInfo videoInfo) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String id = videoInfo.getId();
        String name = videoInfo.getFilename();
        String timePeriod = transTimeListToString(videoInfo.getTimelist());
        String md5sum = videoInfo.getMd5sum();
        values.put("id", id);
        values.put("time_period", timePeriod);
        values.put("md5sum", md5sum);
        return database.update(DataBaseHelper.VIDEO_TABLE_NAME, values, "name like '" + name + "'", null) > 0;
    }

    public boolean deleteVideoInfo(String fileName) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        return database.delete(DataBaseHelper.VIDEO_TABLE_NAME, "name like '" + fileName + "'", null) != 0;
    }

    private String transTimeListToString(List<TimelistBean> timelistBeanList) {
        if (timelistBeanList == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (TimelistBean timelistBean : timelistBeanList) {
            stringBuilder.append(timelistBean.getStart() + "-");
            stringBuilder.append(timelistBean.getEnd() + ",");
        }
        return stringBuilder.toString();
    }

    private List<TimelistBean> transStringToTimeList(String time) {
        List<TimelistBean> timelistBeanList = new ArrayList<>();
        String[] times = time.split(",");
        int length = times.length;
        for (int i = 0; i < length; i++) {
            if (times[i] != null && !"".equals(times[i])) {
                String[] periods = times[i].split("-");
                TimelistBean timelistBean = new TimelistBean();
                timelistBean.setStart(periods[0]);
                timelistBean.setEnd(periods[1]);
                timelistBeanList.add(timelistBean);
            }
        }
        return timelistBeanList;
    }

    public boolean addPlayHistory(String name, String time) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("name", name);
        return database.insertWithOnConflict(DataBaseHelper.HISTORY_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE) != -1;
    }

    public void storePlayList(List<String> playIdList) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (String id : playIdList) {
            stringBuilder.append(id + ",");
        }
        editor.putString("playlist", stringBuilder.toString());
        editor.apply();
    }

    @NonNull
    public String getCachePlayList() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("playlist", "");
    }

    public List<PlayHistoryData> getPlayHistory() {
        SQLiteDatabase database = mDataBaseHelper.getReadableDatabase();
        List<PlayHistoryData> histories = new ArrayList<>();
        Cursor cursor = database.query(DataBaseHelper.HISTORY_TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (time != null && name != null) {
                histories.add(new PlayHistoryData(time, name));
            }
        }
        cursor.close();
        return histories;
    }*/
}
