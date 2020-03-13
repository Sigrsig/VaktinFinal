package is.hi.hbv601.vaktin.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import is.hi.hbv601.vaktin.Entities.Comment;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Footer;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.User;
import is.hi.hbv601.vaktin.Entities.Workstation;

@Database(entities = {User.class, Token.class, Employee.class, Comment.class, Footer.class, Workstation.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TokenDao tokenDao();
    public abstract EmployeeDao employeeDao();
    public abstract CommentDao commentDao();
    public abstract FooterDao footerDao();
    public abstract WorkstationDao workstationDao();

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