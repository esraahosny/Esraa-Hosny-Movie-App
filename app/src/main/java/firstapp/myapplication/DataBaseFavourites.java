package firstapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBaseFavourites extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME ="Favorits.db";
    public static final String TABLE_NAME ="favoriteMovieTable";
    private static final int DATABASE_VERSION =6;
    public static final String COL_1 ="ID";
    public static final String COL_2 ="TITLE";
    public static final String COL_3="RELEASE_DATE";
    public static final String COL_4 ="VOTE_AVERAGE";
    public static final String COL_5 ="POSTER_PATH";
    public static final String COL_6 ="OVERVIEW";

    String Create_Table = "CREATE TABLE " + TABLE_NAME + "("
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COL_2 + " TEXT ," + COL_3 + " TEXT ,"
            + COL_4+ " TEXT ," + COL_5+ " TEXT ," + COL_6 +");";

    String Drop_Table= "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataBaseFavourites(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table);
        Toast.makeText(context,"database created",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Drop_Table);
        Toast.makeText(context,"database upgraded",Toast.LENGTH_SHORT).show();
        onCreate(db);
    }

    public boolean insertData( String id , String title , String release_date ,String vote_average , String poster_path , String overview)
    {
        //Log.v("insid",id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Integer.parseInt(id));
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,release_date);
        contentValues.put(COL_4, vote_average);
        contentValues.put(COL_5,poster_path);
        contentValues.put(COL_6, overview);
        long result = db.insert(TABLE_NAME,null,contentValues);
        Toast.makeText(context,result+"",Toast.LENGTH_SHORT).show();
        if(result == -1)
            return false;
        else
            return true;

    }

    public ArrayList<Movie> GetAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Movie>arrayList_Favorite_movie= new ArrayList<>();
        Cursor movieDataBase = db.rawQuery("Select * from " + TABLE_NAME , null );
        while (movieDataBase.moveToNext()) {
            Movie movieData = new Movie();
            movieData.setId(String.valueOf(movieDataBase.getInt(0)) );
            movieData.setTitle(movieDataBase.getString(1));
            movieData.setRelease_date(movieDataBase.getString(2));
            movieData.setVote_average(movieDataBase.getString(3));
            movieData.setPosterPath(movieDataBase.getString(4));
            movieData.setOverview(movieDataBase.getString(5));
            arrayList_Favorite_movie.add(movieData);
        }
        return arrayList_Favorite_movie;
    }

    public void Delete_moview(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int m= db.delete(TABLE_NAME,COL_1 +"="+ID,null);
        if (m==0){
            Toast.makeText(context,"not deleted",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
            db.close();
    }

    public boolean Search_movie(String ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_1};
        String []args =new String[] { ID };
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "=?",args, null, null, null, null);
        if (cursor.getCount()== 0)
            return false;
        else
            return true;
    }


}

