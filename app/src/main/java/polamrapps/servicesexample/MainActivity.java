package polamrapps.servicesexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackBar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG);
                View v = snackBar.getView();
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                snackBar.setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("PolamR", " view = "+view);
                    }
                });
                snackBar.setActionTextColor(getResources().getColor(android.R.color.holo_green_dark));
                TextView textView= (TextView) v.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackBar.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button bottmSheets = (Button) findViewById(R.id.btnBottomSheets);
        bottmSheets.setOnClickListener(this);

        Button start = (Button) findViewById(R.id.btnStartService);
        start.setOnClickListener(this);
        Button bound = (Button) findViewById(R.id.btnStartBoundService);
        bound.setOnClickListener(this);

        Button startServcie = (Button) findViewById(R.id.btnIntentService);
        startServcie.setOnClickListener(this);

        Button startBR = (Button) findViewById(R.id.btnBR);
        startBR.setOnClickListener(this);

        View bottomSheet = findViewById(R.id.bottmSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setPeekHeight(0);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private MyService myServiceBinder;
    MyService2 service2;
    MyIntentService intentService;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Utils.show("onServiceConnected , componentName : "+componentName.getClassName());
            if(componentName.getClassName().equals("polamrapps.servicesexample.MyService"))
                myServiceBinder = ((MyService.MyBinder) iBinder).getService();
            else if(componentName.getClassName().equals("polamrapps.servicesexample.MyIntentService")) {
                Utils.show("Intent servcie");
                intentService = ((MyIntentService.MyLocalBinder) iBinder).getService();
                Utils.show("time : "+intentService.getTime());
            } else {
                service2 = ((MyService2.LocalBinder) iBinder).getService();
                Utils.show("time : "+service2.getTime());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Utils.show("onServiceDisconnected , componentName : "+componentName.getClassName());
            myServiceBinder = null;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle data = message.getData();
        }
    };

    private void startServiceEx() {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        Messenger messenger = new Messenger(myHandler);
        intent.putExtra("MESSENGER", messenger);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartService:
                startServiceEx();
                break;
            case R.id.btnStartBoundService:
                startBound();
                break;
            case R.id.btnBottomSheets:
                createBottomSheets();
                break;
            case R.id.btnSnackBar:
                break;
            case R.id.btnBR:
                startBR();
                break;
            case R.id.btnIntentService:
                startIntentService();
                break;
        }
    }

    private void startBR() {
        Utils.show("time : " );

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(new MyReceiver(), intentFilter);
    }

    private void startIntentService () {
        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);

        //MyIntentService.startActionBaz(MainActivity.this, "Raja", "Reddy");

        //MyIntentService.startActionFoo(MainActivity.this, "Polam", "R");
    }

    private void startBound() {
        Intent intent = new Intent(MainActivity.this, MyService2.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);

    }

    private void createBottomSheets() {
        Log.i("PolamR", "createBottomSheets");
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setPeekHeight(300);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        BottomSheetDialogFragment bottomSheetDialogFragment = new TutsPlusBottomSheetDialogFragment();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, Main2Activity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, Main3Activity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
