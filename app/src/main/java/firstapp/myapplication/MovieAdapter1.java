package firstapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter1 extends BaseAdapter {
    //initialize context , arraylist
    private Context context;
    private List<Movie> arrayList;

    //costructor takes context ,arraylist
    public MovieAdapter1(Context context, List<Movie> arrayList) {
        super();
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.grid_item, parent,false);
         //view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
         ImageView image = (ImageView) view.findViewById(R.id.imageView);
        /**
         * this part means to detect the position
         * of image & load it in grid view
         */
        String path = "http://image.tmdb.org/t/p/w500/";
        //http://image.tmdp.org/t/p/w185/     ( base url of images)
        Picasso.with(context).load(path + arrayList.get(position).getPosterPath()).into(image);
        return view;

    }}



