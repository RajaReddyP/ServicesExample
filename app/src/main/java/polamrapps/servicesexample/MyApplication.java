package polamrapps.servicesexample;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Rajareddy on 01/07/16.
 */
public class MyApplication extends android.support.multidex.MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
