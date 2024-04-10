package ro.pub.cs.systems.eim.colocviu1_2;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Colocviu1_2Service extends Service {
    private ProcessingThread processingThread = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int sum = intent.getIntExtra(Constants.SUM_SAVE, -1);
        Log.d("debug", "i am in service " + sum);
        processingThread = new ProcessingThread(this, sum);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    public void onDestroy() {
        processingThread.stopThread();
    }
}
