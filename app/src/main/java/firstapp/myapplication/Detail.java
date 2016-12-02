package firstapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Detail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();

        //inflate details fragment & send the bundle to it
        firstapp.myapplication.DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().add(R.id.detail_activity,detailFragment).commit();


    }
}
