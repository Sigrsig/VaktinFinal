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

/***
 *  Room database for entities: User, Employee, Token, Comment, Footer, Workstation
 *  Update version if schema is changed
 */
@Database(entities = {User.class, Token.class, Employee.class, Comment.class, Footer.class, Workstation.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /***
     * Handles for service classes of entities
     */
    public abstract UserDao userDao();
    public abstract TokenDao tokenDao();
    public abstract EmployeeDao employeeDao();
    public abstract CommentDao commentDao();
    public abstract FooterDao footerDao();
    public abstract WorkstationDao workstationDao();
    public abstract WorkstationWithEmployeesDao mWorkstationWithEmployeesDao();

    private static AppDatabase INSTANCE;

    /***
     * Returns instance of Room database given a context
     * @param context
     */
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


    /***
     * Can return instance of Room database when context not provided
     */
    public static AppDatabase getInstance() {
        return INSTANCE;
    }


}