package polamrapps.servicesexample;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;

public class MyService extends Service {

    private final IBinder mBinder = new MyBinder();
    private Messenger outMessenger;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.show("onCreate");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.show("onUnbind");
        return super.onUnbind(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.show("onStartCommand");
        return START_STICKY;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Utils.show("onConfigurationChanged");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.show("onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Utils.show("onLowMemory");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Utils.show("onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Utils.show("onTaskRemoved");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Utils.show("onTrimMemory");
    }

    @Override
    public ComponentName startService(Intent service) {
        Utils.show("startService");
        return super.startService(service);

    }

    @Override
    public IBinder onBind(Intent intent) {
        Utils.show("onBind");

        Bundle extras = intent.getExtras();

        // Get messager from the Activity
        if (extras != null) {
            Utils.show("onBind with extra");
            outMessenger = (Messenger) extras.get("MESSENGER");
        }
        return mBinder;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
