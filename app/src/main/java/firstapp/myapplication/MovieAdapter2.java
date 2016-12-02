package firstapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 18/11/2016.
 */
public class MovieAdapter2 extends BaseAdapter {
    private Context context;
    private List<TrailerData> listVideos;

    public MovieAdapter2(Context context , List<TrailerData> listVideos) {
        super();
        this.context = context;
        this.listVideos = listVideos;

    }

    @Override
    public Object getItem(int position) {
        return listVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return listVideos.size();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.videos_item, parent, false);
        //view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        final ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.imageButton);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        textView.setText(listVideos.get(position).getTrailer_name() );
        imageButton.setImageResource(R.drawable.play);

//if(convertView==null)
//    Log.v("error","null");
//else
//Log.v("right","fill");

    return convertView;
    }

}
