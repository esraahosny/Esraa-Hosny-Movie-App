package firstapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SelectPosterPathListener  {

boolean isTwopane =false;
    MovieFragment movieFragment = new MovieFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set activity to be a listener to the fragment
          movieFragment.setPosterListener(this);
        if(getSupportFragmentManager().findFragmentById(R.id.activity) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity, movieFragment).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().add(R.id.activity, movieFragment).commit();
        }


        //check of tablet
        if(null!= findViewById(R.id.detail_activity))
          isTwopane = true;

   }



    @Override
    public void setSelectedPosterPath(String image,String overview ,String release_date,String title , String vote_average , String ids) {
        //one pane
        if(!isTwopane) {

            Intent intent = new Intent(MainActivity.this,Detail.class);
            intent.putExtra("i", image);
            intent.putExtra("o", overview);
            intent.putExtra("r", release_date);
            intent.putExtra("t", title);
            intent.putExtra("a", vote_average);
            intent.putExtra("id", ids);
            startActivity(intent);


        }
        else {

            //two pane
            DetailFragment detailFragment = new DetailFragment();
            Bundle extras = new Bundle();
            extras.putString("i",image);
            extras.putString("overview",overview);
            extras.putString("r",release_date);
            extras.putString("t",title);
            extras.putString("v",vote_average);
            extras.putString("id",ids);
            detailFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_activity,detailFragment).commit();
        }
    }
}
