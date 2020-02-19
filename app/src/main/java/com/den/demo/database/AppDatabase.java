package com.den.demo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.den.demo.database.convert.DateConvert;
import com.den.demo.database.dao.ChatListDao;
import com.den.demo.database.entities.ChatList;

@Database(entities = {ChatList.class}, version = 1)
@TypeConverters({DateConvert.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();
    public abstract ChatListDao chatListDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user.db")
                                .allowMainThreadQueries()
                                .build();
                System.out.println("数据库已创建");
            }
            return INSTANCE;
        }
    }
}
