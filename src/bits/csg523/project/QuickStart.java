package bits.csg523.project;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class QuickStart extends FragmentActivity implements OnClickListener{
	
	/* defining some constants*/
	protected final int GPS_ALERT=1;
	protected final int WEIGHT_ALERT=2;
	private float currentLat=0,currentLong=0;
	protected float lastLat=0;
	protected float lastLong=0;
	/*variable*/
	protected GoogleMap mymap;
	Context context;
	TextView t2,t3,t4;
	Location location=null;;
	String PROVIDER=LocationManager.GPS_PROVIDER;
	LocationManager locationManager;
	protected float weight=0;
	private EditText input;
	private float distance=0;
	private float velocity=0;
	private float calories=0;
	protected boolean enabled;
	
	SQLController SQLcon;
	Jogger j=new Jogger();
	protected String usrname;
	
    
protected LocationListener locationListener = new LocationListener() {
		
		public void onLocationChanged(Location location) {
			float deltaLambda = currentLong-lastLong;
			currentLat=(float) location.getLatitude();
			currentLong=(float) location.getLongitude();
			/* calculating distance based on the Great Circle Distance method*/
			distance+=6371000*((Math.sin(lastLat)*Math.sin(currentLat)) + (Math.cos(lastLat)*Math.cos(currentLat)*Math.cos(deltaLambda)));
			velocity=location.getSpeed();
			
			/*Updating the display*/
			showMyLocation(location);
			
			/* calculating the calories burned*/
			calories+=(0.2*velocity*60 +3.5)*3.5*weight/(200*60);
			//Toast.makeText(QuickStart.this, "Loc changed",Toast.LENGTH_SHORT).show();  //For debugging
			
			lastLat=currentLat;
			lastLong=currentLong;

		}

		public void onStatusChanged(String provider, int status,
				Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
			showMyLocation(location);
		}

		public void onProviderDisabled(String provider) {
			showMyLocation(null);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SQLcon = new SQLController(this);
		SQLcon.open();
		
		setContentView(R.layout.quick_start_screen);
		
		mymap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapjog)).getMap();
		
		Intent intent=getIntent();
		
		//If the intent came from the Login screen
		if(intent.hasExtra("joggerName")){
			usrname=intent.getStringExtra("joggerName");
			j=SQLcon.getData(usrname);
			weight=j.getWeight();
			Toast.makeText(this, usrname,Toast.LENGTH_SHORT).show(); //For debugging
		}
		
		t2=(TextView)findViewById(R.id.textView2);
		t3=(TextView)findViewById(R.id.textView3);
		t4=(TextView)findViewById(R.id.textView4);
		ToggleButton ststop= (ToggleButton)findViewById(R.id.startstop); //To start and stop
		ststop.setOnClickListener((OnClickListener) this);

		// Acquire a reference to the system Location Manager
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		enabled = locationManager.isProviderEnabled(PROVIDER);
		location = locationManager.getLastKnownLocation(PROVIDER);
		
		/* Checking if GPS is ON*/
		
		if (!enabled) {
			showDialog(GPS_ALERT);
		} 
		if(weight==0)
			showDialog(WEIGHT_ALERT);
		
		if(location!=null)
		{
		lastLat=(float) location.getLatitude();
		lastLong=(float) location.getLongitude();
		}
		showMyLocation(this.location);
		//Toast.makeText(this, "HI " +PROVIDER,Toast.LENGTH_SHORT).show(); //For debugging
		
	}

	
	@Override
	 protected void onResume() {
	  super.onResume();
	  location = locationManager.getLastKnownLocation(PROVIDER);
		showMyLocation(location);
	  locationManager.requestLocationUpdates(PROVIDER,1000,0, locationListener); //LocationListener
	 }
	
	@Override
	protected void onPause() {
		//handler.removeCallbacks(call);
		  super.onPause();
		  locationManager.removeUpdates(locationListener);
		 }
	
	@Override
	protected void onDestroy() {
		  super.onDestroy();
		  locationManager.removeUpdates(locationListener);
		 }
	

	protected void showMyLocation(Location l){
		  if(l == null){
				t2.setText("Distance: " + distance);
				t3.setText("Speed: "+velocity);
				t4.setText("Calories: "+calories);
				//Toast.makeText(this, "Null",Toast.LENGTH_SHORT).show(); //For debugging
			}
			else{
			/*Set the map and markers*/
				mymap.setMyLocationEnabled(true);
				MarkerOptions marker = new MarkerOptions().position(new LatLng(l.getLatitude(),l.getLongitude())).title("Me");
				marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pt));
				mymap.addMarker(marker);
				mymap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(),l.getLongitude()), 16));
				t2.setText("Distance: " + distance);
				t3.setText("Speed: "+velocity);
				t4.setText("Calories: "+calories);
			}
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
	  switch (id) {
	    case GPS_ALERT:
	      Builder gpsbuilder = new AlertDialog.Builder(this);
	      gpsbuilder.setTitle("Enable GPS");
	      gpsbuilder.setMessage("Please start GPS");
	      gpsbuilder.setCancelable(true);
	      gpsbuilder.setPositiveButton("I agree", new OkOnClickListener(GPS_ALERT));
	      gpsbuilder.setNegativeButton("No", new CancelOnClickListener(GPS_ALERT));
	      AlertDialog dialog = gpsbuilder.create();
	      dialog.show();
	      break;
	    
	    case WEIGHT_ALERT:
	    	Builder wtbuilder = new AlertDialog.Builder(this);
		    wtbuilder.setTitle("Enter Weight");
		    wtbuilder.setMessage("Please enter your weight");
		    input = new EditText(this);
		    input.setInputType(InputType.TYPE_CLASS_NUMBER);
		    wtbuilder.setView(input);
		    wtbuilder.setCancelable(true);
		    wtbuilder.setPositiveButton("Ok", new OkOnClickListener(WEIGHT_ALERT));
		    wtbuilder.setNegativeButton("Cancel", new CancelOnClickListener(WEIGHT_ALERT));
		    AlertDialog wtdialog = wtbuilder.create();
		    wtdialog.show();
		      break;
	  }
	  return super.onCreateDialog(id);
	}

	/* The cancel button on the dialog screens*/
	protected final class CancelOnClickListener implements DialogInterface.OnClickListener {
		private int opt=0;
		
		public CancelOnClickListener(int opt){
			this.opt=opt;
		}
	  public void onClick(DialogInterface dialog, int opt) {
		switch(this.opt){
			case GPS_ALERT:
			Toast.makeText(getApplicationContext(), "No GPS selected",Toast.LENGTH_LONG).show();
			finish();
			break;
			
			case WEIGHT_ALERT:
				finish();
				
		}
	  }
	}
	
	/* The OK button on the dialog screens*/

	protected final class OkOnClickListener implements DialogInterface.OnClickListener {
		private int opt=0;
		
		public OkOnClickListener(int opt){
			this.opt=opt;
		}
	  public void onClick(DialogInterface dialog, int opt) {
		  switch(this.opt){
			case GPS_ALERT:
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			break;
			case WEIGHT_ALERT:
				 weight = Float.parseFloat(input.getText().toString());
				 break;
		}
		  
	  }
	}

	
	/* the function for the Toggl button*/
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.startstop:
			boolean on = ((ToggleButton) v).isChecked();
			if(on){

				location = locationManager.getLastKnownLocation(PROVIDER);
				locationManager.requestLocationUpdates(PROVIDER, 1000,0, locationListener);
				//Toast.makeText(this, "ON",Toast.LENGTH_SHORT).show(); //For debugging
				showMyLocation(location);
			}
			else{
				locationManager.removeUpdates(locationListener);
				location=null;
				//Toast.makeText(this, "OFF",Toast.LENGTH_SHORT).show(); //For debugging
			}
			break;
		}
	} 
}

