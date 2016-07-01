package polamrapps.servicesexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.show("onReceive");
        StringBuilder msgStr = new StringBuilder("Current time : ");
        Format formatter = new SimpleDateFormat("hh:mm:ss");
        msgStr.append(formatter.format(new Date()));
        Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
    }
}
