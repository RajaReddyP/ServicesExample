package polamrapps.servicesexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        if(getIntent() != null) {
            Intent intent = getIntent();
            Movie movie = intent.getParcelableExtra("MOVIE");
            Utils.show("movie data : "+movie.getTitle());
        }
    }
}
