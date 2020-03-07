package is.hi.hbv601.vaktin.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.User;

@Database(entities = {User.class, Token.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TokenDao tokenDao();

    private static AppDatabase INSTANCE; // = Room.databaseBuilder(null, AppDatabase.class, "user-database").allowMainThreadQueries().build();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


    //private static AppDatabase obj = Room.databaseBuilder(null, AppDatabase.class, "database-name").build();

    // Hef þetta með því ég skil ekki Context context
    public static AppDatabase getInstance() {
        return INSTANCE;
    }


}
