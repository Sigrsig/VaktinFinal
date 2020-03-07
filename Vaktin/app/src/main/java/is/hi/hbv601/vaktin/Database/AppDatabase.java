package is.hi.hbv601.vaktin.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import is.hi.hbv601.vaktin.Entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static AppDatabase INSTANCE; // = Room.databaseBuilder(null, AppDatabase.class, "user-database").allowMainThreadQueries().build();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


    //private static AppDatabase obj = Room.databaseBuilder(null, AppDatabase.class, "database-name").build();

    public static AppDatabase getInstance() {
        return INSTANCE;
    }


}
