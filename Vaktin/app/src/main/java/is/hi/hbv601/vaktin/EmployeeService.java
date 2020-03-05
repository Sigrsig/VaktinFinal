package is.hi.hbv601.vaktin;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;

public class EmployeeService extends IntentService {

    // Veit ekki af hverju þetta er til
    public EmployeeService() {
        super("EmployeeService");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, EmployeeService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = EmployeeService.newIntent(context);
        //Intent(context, EmployeeService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_DAY, pi);
        }
        else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
    }
    public static boolean isServiceAlarmOn(Context context) {
        Intent i = EmployeeService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);

        return pi != null;
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }


}
