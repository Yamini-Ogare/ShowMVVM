package ognora.showmvvm.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ognora.showmvvm.Model.FileModel;

@Database(entities = FileModel.class , version = 1)
public abstract class FileDatabase  extends RoomDatabase {

    public static  FileDatabase INSTANCE ;
    public abstract FileDao fileDao();

    public static FileDatabase getDatabase(Context context)
    {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), FileDatabase.class, "File-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }

        return INSTANCE;

    }

    public static void destroyInstance() {

        INSTANCE = null;
    }


}
