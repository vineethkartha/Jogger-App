package bits.csg523.project;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;


public class DBclass extends SQLiteOpenHelper{

	private static final String DB_NAME="jogger.db";
	private static final int schemaversion=1;
	
	private static final String TABLE="joggers_table";
	private static final String COLUMN_ID="_id";
	private static final String COLUMN_NAME="Name";
	private static final String COLUMN_AGE="Age";
	private static final String COLUMN_HEIGHT="Height";
	private static final String COLUMN_WEIGHT="Weight";
	private static final String COLUMN_BMI="BMI";
	private static final String COLUMN_PASSWD="Password";
	private static final String TABLE_CREATE = "CREATE TABLE "+ TABLE + "(" + COLUMN_ID
		      + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME+ " TEXT NOT NULL UNIQUE,"+ COLUMN_AGE+" INTEGER,"+COLUMN_HEIGHT+" REAL,"+ COLUMN_WEIGHT +" REAL,"+COLUMN_BMI+" REAL," +COLUMN_PASSWD+" TEXT );";
	
	public DBclass(Context context){
		super(context,DB_NAME,null,schemaversion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
		try{
		db.execSQL(TABLE_CREATE);
		}
		catch(SQLException e){
			Log.e("Error Writting to database",e.toString());//add error dialog box
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
		
	}
	
	public String getTable(){
		return TABLE;
	}
	
	public String getCID(){
		return COLUMN_ID;
	}
	
	public String getCName(){
		return COLUMN_NAME;
	}
	
	public String getCAge(){
		return COLUMN_AGE;
	}
	
	public String getCHeight(){
		return COLUMN_HEIGHT;
	}
	
	public String getCWeight(){
		return COLUMN_WEIGHT;
	}
	
	public String getCBMI(){
		return COLUMN_BMI;
	}
	
	public String getCPasswd(){
		return COLUMN_PASSWD;
	}
	
}