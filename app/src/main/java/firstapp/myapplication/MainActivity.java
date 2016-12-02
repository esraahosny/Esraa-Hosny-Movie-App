package firstapp.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {
    //implements SelectPosterPathListener
boolean isTwopane;
    MovieFragment movieFragment = new MovieFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set activity to be a listener to the fragment
          //movieFragment.setPosterListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.activity, movieFragment).commit();

//        if(null!= findViewById(R.id.detail_activity))
//            //tablet
//        isTwopane = true;
//        else
//        {
//            //mobile phone
//
//        }
   }

   // @Override
   // public void setSelectedPosterPath(String image) {


        //one pane
       // if(!isTwopane) {

//        Intent intent = new Intent(getActivity(), firstapp.myapplication.Detail.class);
//        intent.putExtra("i", image);//i=Key , image = value)
//        //i should put all images in arraylist
//        // intent.putExtra("imageID", ImageArray[position]);
//        intent.putExtra("o", overview);
//        intent.putExtra("r", release_date);
//        intent.putExtra("t", title);
//        intent.putExtra("a", vote_average);
//        // intent.putExtra("video", video);
//        intent.putExtra("id", ids);
//
//
//        // getActivity().
//        startActivity(intent);
//    }
//});
//        }
//       else {
//
//            //two pane
//            DetailFragment detailFragment = new DetailFragment();
//            Bundle extras = new Bundle();
//            extras.putString("i",image);
//            detailFragment.setArguments(extras);
//            getSupportFragmentManager().beginTransaction().replace(R.id.detail_activity,movieFragment).commit();
//        }


  //  }
}
