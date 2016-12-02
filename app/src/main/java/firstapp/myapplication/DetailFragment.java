package firstapp.myapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    public TrailerAdapter movieAdapter2;
    public List<TrailerData> listVideos = new ArrayList<>();
    public List<ReviewsData> listReviews = new ArrayList<>();
    DataBaseFavourites dbf;
    String idmovie;
    TextView tvReviewData;
    String Poster_key;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbf = new DataBaseFavourites(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_item, container, false);
        //intent Bundle to get data
        final Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        if(extras == null){
            extras = getArguments();
        }
        idmovie = extras.getString("id");
        // image of each movie poster
        ImageView posterpath = (ImageView) view.findViewById(R.id.posterpathtv);
        //text views of info from grid view
        TextView title = (TextView) view.findViewById(R.id.titletv);
        final TextView release_date = (TextView) view.findViewById(R.id.release_datetv);
        TextView overview = (TextView) view.findViewById(R.id.overviewtv);
        TextView voteAverage = (TextView) view.findViewById(R.id.vote_average);
        ListView lvTrailers = (ListView) view.findViewById(R.id.listViewVideo);
        tvReviewData = (TextView) view.findViewById(R.id.textViewReviews);
        Button starbutton = (Button) view.findViewById(R.id.starbutton);
        Button delete = (Button) view.findViewById(R.id.button2);



        //Receive the sent Bundle
        String image1 = "http://image.tmdb.org/t/p/w185/" + extras.getString("i");
        Log.v("mmmm",image1);
        Poster_key = extras.getString("i");
        Glide.with(getActivity()).load(image1).into(posterpath);

        //overview
        final String overview1 = extras.getString("o");
        overview.setText(overview1);

        //release date
        final String release_date1 =extras.getString("r");
        release_date.setText(release_date1);

        //title
        final String title1 = extras.getString("t");
        title.setText(title1);

        //vote_count
        final String average = String.valueOf(extras.getString("a"));
        voteAverage.setText(average);


        //save movie
        starbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save movie in favourite list
                DataBaseFavourites db = new DataBaseFavourites(getActivity());
                if (!db.Search_movie(idmovie)) {
                    boolean isInserted = dbf.insertData(idmovie, title1, release_date1, average, Poster_key, overview1);
                    Log.v("ifins", String.valueOf(isInserted));
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Do you want to delete this movie");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dbf.Delete_moview(idmovie);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        Toast.makeText(getActivity(), "movie still exists", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        movieAdapter2 = new TrailerAdapter(getActivity(), listVideos);
        lvTrailers.setAdapter(movieAdapter2);
        lvTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailerData trailerData = listVideos.get(position);
                String SemiPartVideoUrl = trailerData.getKey();
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + SemiPartVideoUrl));
                startActivity(intent1);
                //Toast.makeText(getContext(),"trailer",Toast.LENGTH_SHORT).show(); this line for check
            }
        });


        //this method handles the scrolling in the listview in the details activity
       lvTrailers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;

                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        //trailers part
        JsonTask2 jsonTask2 = new JsonTask2();
        String api_key = "6be3beeecf3e73c7baf052936de346da";
        jsonTask2.execute("https://api.themoviedb.org/3/movie/" + idmovie + "/videos?api_key="+api_key);
        //https://www.youtube.com/watch?v=FnZF82_3Cts
        //key example: FnZF82_3Cts
        Log.v("backid", idmovie);
        //  Log.i("Esraa", String.valueOf(listVideos.get(1)));


        //reviews part
        JsonTask3 jsonTask3 = new JsonTask3();
        jsonTask3.execute();


        return view;

    }

    public class JsonTask2 extends AsyncTask<String, Void, List<TrailerData>> {
        //log part to check data
        private final String LOG = JsonTask2.class.getSimpleName();


        //doInBackground part contains connection + url of api + exception handlers
        protected List<TrailerData> doInBackground(String... params) {
            Log.v("nnnn", "inback");
            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;

            try {
                URL url = new URL(params[0]);
                Log.v("nnnn1", url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                if (stream == null) {
                    Log.v("nnnn2", "stream null");
                    return null;
                }

                StringBuffer buffer = new StringBuffer();


                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                finaljson = buffer.toString();
                Log.v("jsonstr", idmovie + "\n" + finaljson);

            } catch (IOException e1) {
                Log.v("nnnn3", "Excp3");

                Log.e(LOG, "error", e1);

                return null;

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        //log statement or any message
                        Log.e(LOG, "error", ioe);
                        Log.v("nnnn4", "excp4");

                    }
                }

                try {
                    Log.v("nnn1", "send str");
                    return getData(finaljson);
                } catch (JSONException e) {
                    //e.printStackTrace();
                    Log.e(LOG, "error", e);
                }
                return null;
            }

        }

        private List<TrailerData> getData(String jsontoString) throws JSONException {

            Log.v("nnn2", "jsonstart");
            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray trailerArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String TrailerName = "name";
            final String TrailerID = "id";
            final String key = "key";
            for (int i = 0; i < trailerArray.length(); i++) {

                JSONObject finalObject = trailerArray.getJSONObject(i);
                TrailerData td = new TrailerData();
                td.setTrailer_name(finalObject.getString(TrailerName));
                td.setTrailer_id(finalObject.getString(TrailerID));
                td.setKey(finalObject.getString(key));
                listVideos.add(td);

                Log.v("name", TrailerName);
                Log.v("id", TrailerID);
                Log.v("jsonkey", finalObject.getString(key));
            }
            Log.v("nnn3", "jsonfinished");

            return listVideos;

        }

        @Override
        protected void onPostExecute(List<TrailerData> trailerDatas) {
            super.onPostExecute(trailerDatas);
            movieAdapter2.notifyDataSetChanged();

        }
    }

    public class JsonTask3 extends AsyncTask<String, Void, List<ReviewsData>> {
        private final String LOG = JsonTask2.class.getSimpleName();

        //doInBackground part contains connection + url of api + exception handlers
        protected List<ReviewsData> doInBackground(String... params) {

            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;


            try {
                Log.v("rev", "Started");
                String api_key = "6be3beeecf3e73c7baf052936de346da";
                URL url = new URL("https://api.themoviedb.org/3/movie/" + idmovie + "/reviews?api_key="+api_key);


                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                if (stream == null) {
                    // Nothing to do.
                    return null;
                }
                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                finaljson = buffer.toString();

            } catch (IOException e1) {
                Log.e(LOG, "error", e1);
                return null;

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        //log statement or any message
                        Log.e(LOG, "error", ioe);
                    }
                }

                try {
                    return getData(finaljson);
                } catch (JSONException e) {
                    //e.printStackTrace();
                    Log.e(LOG, "error", e);
                }
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<ReviewsData> reviewsDatas) {
            super.onPostExecute(reviewsDatas);
            String revs = "";
            for (int i = 0; i < reviewsDatas.size(); i++) {
                revs += reviewsDatas.get(i).getContent();
            }
            tvReviewData.setText(revs);
        }

        private List<ReviewsData> getData(String jsontoString) throws JSONException {

            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray movieArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String ReviewContent = "content";
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject finalObject = movieArray.getJSONObject(i);
                ReviewsData r = new ReviewsData();
                r.setContent(finalObject.getString(ReviewContent));
                Log.v("revs", finalObject.getString(ReviewContent));
                listReviews.add(r);

            }

            return listReviews;

        }


    }
}


