package bits.csg523.project;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Alarm extends Activity implements OnClickListener{

	private DatePicker dp; 
	private TimePicker tp;
	private int year;
	private int month;
	private int day;
	private int minute;
	private int hour;
	private Button set,cancel;
	private TextView alarmdisp;
	
	private String usrname;
	
	//private Jogger j=new Jogger();
	
	PendingIntent pendingIntent;
	Intent intent;
	AlarmManager am;
	
	static int HELLO_ID=1;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		Intent intent=getIntent();
		usrname=intent.getStringExtra("joggerName");
		dp=(DatePicker)findViewById(R.id.datePicker1); //Initialisisng the date picker time picker and buttons
		tp=(TimePicker)findViewById(R.id.timePicker1);
		alarmdisp=(TextView)findViewById(R.id.curalarm);
		alarmdisp.setText("No");
		set=(Button)findViewById(R.id.setalarm);
		set.setOnClickListener(this);
		cancel=(Button)findViewById(R.id.canelalarm);
		cancel.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		/* This case statement takes care of the alarm Set Button*/
		case R.id.setalarm:
			Calendar cal=Calendar.getInstance();   //Getting current date and time
			year=dp.getYear();
			month=dp.getMonth();
			day=dp.getDayOfMonth(); //getDayOfMonth() returns a value which is 1-31
			hour=tp.getCurrentHour();
			minute=tp.getCurrentMinute();
			
			cal.set(Calendar.DATE,day);
			cal.set(Calendar.MONTH,month);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MINUTE,minute);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			
			alarmdisp.setText(cal.getTime().toLocaleString()); //Display the set alarm
			intent = new Intent(this, MainScreen.class);
			intent.putExtra("joggerName",usrname);
	        pendingIntent = PendingIntent.getActivity(this,12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);//If any alarm with ID 12345 exist it will be cancelled
	        am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);
			Toast.makeText(getApplicationContext(),"Alarm set for " + cal.getTime().toLocaleString(), Toast.LENGTH_LONG).show();
			break;
		
			/*This statment takes care of the cancelling of alarm*/
		case R.id.canelalarm:
			if(am!=null){
				am.cancel(pendingIntent);
				alarmdisp.setText("No alarm");
			}
			break;
		}
		
	}
	
}