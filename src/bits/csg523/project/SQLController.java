package bits.csg523.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLController {
 private DBclass dbhelper;
 private Context ourcontext;
 private SQLiteDatabase database;
 
 //private static final String[] COLUMNS = {"_id","Name","Age","Height","Weight","BMI","Password"}; // The columns of the database table

 //Constructor for this class
 public SQLController(Context c) {
  ourcontext = c;
 }

 //Function to create an instance of DBclass and get a database opened or throw an exception if opening of database is not possible
 public SQLController open() throws SQLException {
  dbhelper = new DBclass(ourcontext);
  database = dbhelper.getWritableDatabase();  //This will call the onCreate() function of the DBclass 
  return this;

 }

 //function to close the instance of DBclass
 public void close() {
  dbhelper.close(); 
 }

 //The data to be pushed to database are packed into the contentValues class object and they are passed to the insert function.
 public boolean insert(String name,int age, float height, float weight,float BMI,String passwd) {
		ContentValues cv=new ContentValues();
		cv.put(dbhelper.getCName(), name);
		cv.put(dbhelper.getCAge(), age);
		cv.put(dbhelper.getCHeight(), height);
		cv.put(dbhelper.getCWeight(), weight);
		cv.put(dbhelper.getCBMI(), BMI);
		cv.put(dbhelper.getCPasswd(), passwd);
		try
		{
			database.insertOrThrow(dbhelper.getTable(), null, cv);  //This function pushes the data to the table if failure occurs it throws an exception.
			return true;
		} catch (SQLException e){
			Log.e("Error Writting to database",e.toString());// Have to add a error dialog box
			return false;
		}
	}

 public void update(String name,int age, float height, float weight,float BMI,String passwd){
	  ContentValues cv=new ContentValues();
		cv.put(dbhelper.getCAge(), age);
		cv.put(dbhelper.getCHeight(), height);
		cv.put(dbhelper.getCWeight(), weight);
		cv.put(dbhelper.getCBMI(), BMI);
		cv.put(dbhelper.getCPasswd(), passwd);
	  database.update(dbhelper.getTable(), cv, dbhelper.getCName()+"=?",new String[]{name});
	 }
 public Jogger getData(String usrName){
	    
	 SQLiteDatabase db = dbhelper.getReadableDatabase();
	 Cursor cursor=db.query(dbhelper.getTable(), null, dbhelper.getCName()+"=?", new String[]{usrName}, null, null, null); //get all rows in the table that have the username as provided by the argument
	 if(cursor!=null)
	 {
	 cursor.moveToFirst(); // In case of multiple rows move to the first row with the query condition satisfied.
	 //In Our program there will be only one row 
	 
	 //Get the values from the table and assign them to the object of jogger class.
	 Jogger j = new Jogger();
	 j.setName(cursor.getString(1));
	 j.setAge(Integer.parseInt(cursor.getString(2)));
	 j.setHeight(Float.parseFloat(cursor.getString(3)));
	 j.setWeight(Float.parseFloat(cursor.getString(4)));
	 j.setBMI();
	 return j;
	 }
	 else
		 return null;
	}
 
 	public String loginCheck(String userName)
 	{
 		SQLiteDatabase db = dbhelper.getReadableDatabase();
 		Cursor cursor=db.query(dbhelper.getTable(), null, dbhelper.getCName()+"=?", new String[]{userName}, null, null, null);
 		if(cursor.getCount()<1) // UserName Not Exist
 		{
 			cursor.close();
 			return "NOT EXIST";
 		}
 		cursor.moveToLast();
 		String password= cursor.getString(cursor.getColumnIndex(dbhelper.getCPasswd()));
 		cursor.close();
 		return password;                
 }

}
