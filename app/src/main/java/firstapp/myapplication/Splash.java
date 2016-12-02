package firstapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Splash extends AppCompatActivity {

    Button button;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Picasso.with(Splash.this).load("http://www.dogsgossip.com/image-files/dogs-in-movies.gif").into(imageView1);

    }}

