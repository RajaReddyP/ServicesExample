package polamrapps.servicesexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }

    private void sendData() {
        Movie movie = new Movie("TitleMovie", "GenreMvoie","2016");
        Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
        intent.putExtra("MOVIE", movie);
        startActivity(intent);
    }
}
