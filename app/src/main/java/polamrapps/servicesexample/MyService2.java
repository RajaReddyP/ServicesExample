package polamrapps.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.IBinder;

import java.util.Date;

public class MyService2 extends Service {
    public MyService2() {
    }

    IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MyService2 getService() {
            return MyService2.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Utils.show("Service2 onbind");

        return mBinder;
    }

    public String getTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }
}
