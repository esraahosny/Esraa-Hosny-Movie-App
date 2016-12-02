package firstapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 4/11/2016.
 */
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
        String path = "http://image.tmdb.org/t/p/w185/";
        //http://image.tmdp.org/t/p/w185/     ( base url of images)
        Picasso.with(context).load(path + arrayList.get(position).getPosterPath()).into(image);
        return view;

    }}

    /*public static void sortGrid(int order) {
        Collections.sort(arrayList, new Sorter(order));
        //adapter.notifyDataSetChanged();
    }

    //inner class
    static class Sorter implements Comparator<Object> {
        int order = -1;

        Sorter(int order) {
            this.order = order;
        }

        public int compare(Object obj1, Object obj2) {
            if (obj1.toString().compareTo(obj2.toString()) == 0) {
                return 0;
            } else if (obj1.toString().compareTo(obj2.toString()) < 0) {
                return order;
            } else return (-1 * order);
        }

    }*/

