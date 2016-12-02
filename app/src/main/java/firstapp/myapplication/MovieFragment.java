package firstapp.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment  {


    public MovieAdapter1 movieAdapter1;
    public List<Movie> arrayList = new ArrayList<>();
    public GridView gridView;
    DataBaseFavourites dbf;
    ArrayList<Movie> arrayList_Fav_movie = new ArrayList<>();
    String ids;

    private SelectPosterPathListener selectPosterPathListener;


    void setPosterListener(SelectPosterPathListener selectPosterPathListener)
    {
        this.selectPosterPathListener = selectPosterPathListener;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbf = new DataBaseFavourites(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayList.clear();
        MostPopular();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView);
        // MostPopular();
        movieAdapter1 = new MovieAdapter1(getActivity(), arrayList);
        gridView.setAdapter(movieAdapter1);
       // MostPopular();


        //on clicking item of grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   ArrayList<Movie> adapterArryList = new ArrayList<>();
                Movie movie;
                if (arrayList.size()== 0)
                     movie = arrayList_Fav_movie.get(position);
                else
                      movie = arrayList.get(position);

                String image = movie.getPosterPath();
                String overview = movie.getOverview();
                String release_date = movie.getRelease_date();
                String title = movie.getTitle();
                String vote_average = movie.getVote_average();
                ids = movie.getId();

                selectPosterPathListener.setSelectedPosterPath(image,overview,release_date,title,vote_average,ids);
            }
        });
        return v;
    }

//    public void updateMovie() {
//        JsonTask movieTask = new JsonTask();
//        movieTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=6be3beeecf3e73c7baf052936de346da");
//    }

    public void TopRated() {
        arrayList_Fav_movie.clear();
        movieAdapter1.notifyDataSetChanged();
        gridView.setAdapter(movieAdapter1);
        JsonTask movieTask = new JsonTask();
        movieTask.execute("https://api.themoviedb.org/3/movie/top_rated?api_key= "); //my api key : 6be3beeecf3e73c7baf052936de346da
    }

    public void MostPopular() {
        arrayList_Fav_movie.clear();
        movieAdapter1.notifyDataSetChanged();
        gridView.setAdapter(movieAdapter1);
        JsonTask movieTask = new JsonTask();
        movieTask.execute("https://api.themoviedb.org/3/movie/popular?api_key= "); //my api key : 6be3beeecf3e73c7baf052936de346da
    }


    public class JsonTask extends AsyncTask<String, Void, List<firstapp.myapplication.Movie>> {
        // private final String LOG = JsonTask.class.getSimpleName();


        //doInBackground part contains connection + url of api + exception handlers
        protected List<Movie> doInBackground(String... params) {

            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;
            try {
                URL url = new URL(params[0]);
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
                Log.v("finalJSON", finaljson);

            } catch (IOException e1) {
                Log.e("LOG", "error", e1);
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
                        Log.e("LOG", "error", ioe);
                    }
                }
                try {
                    return getData(finaljson);
                } catch (JSONException e) {
                    //e.printStackTrace();
                    Log.e("LOG", "error", e);
                }
                return null;
            }

        }

        private List<Movie> getData(String jsontoString) throws JSONException {

            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray movieArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String posterPath = "poster_path";
            final String title = "title";
            final String vote_average = "vote_average";
            final String release_date = "release_date";
            final String overview = "overview";
            final String id = "id";


            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject finalObject = movieArray.getJSONObject(i);
                Movie movie = new Movie();

                movie.setPosterPath(finalObject.getString(posterPath));
                movie.setTitle(finalObject.getString(title));
                movie.setRelease_date(finalObject.getString(release_date));
                movie.setOverview(finalObject.getString(overview));
                movie.setId(finalObject.getString(id));
                movie.setVote_average(finalObject.getString(vote_average));
                Log.v("avg", finalObject.getString(vote_average));
                arrayList.add(movie);
            }

            Log.d("vote data", vote_average);
            return arrayList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(arrayList);
            movieAdapter1.notifyDataSetChanged();

        }
    }
//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_top_rated:
                arrayList.clear();
                TopRated();
                break;

            case R.id.action_most_popular:
                arrayList.clear();
                MostPopular();
                break;

            case R.id.action_favorites:
                arrayList.clear();
                arrayList = dbf.GetAllData();
                Log.v("favcount", arrayList.size() + "");
                movieAdapter1 = new MovieAdapter1(getContext(), arrayList);
                movieAdapter1.notifyDataSetChanged();
                gridView.setAdapter(movieAdapter1);

                for (int i = 0; i < arrayList.size(); i++) {
                    Log.v("FID", arrayList.get(i).getId());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }}
