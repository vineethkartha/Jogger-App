package bits.csg523.project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeScreen extends Activity implements OnClickListener{

	/* The first function that runs once the application starts*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen); // calling the home screen layout file
		
		/* Creating the buttons and attaching them to onClickListners*/
		
		Button b1=(Button)findViewById(R.id.qstart); // For quick start
		b1.setOnClickListener((OnClickListener) this);
		
		Button b2= (Button)findViewById(R.id.newusr); // For Create new user
		b2.setOnClickListener((OnClickListener) this);
		
		Button b3= (Button)findViewById(R.id.button3); //For Login
		b3.setOnClickListener((OnClickListener) this);
		
		Button b4= (Button)findViewById(R.id.button4); //For quit
		b4.setOnClickListener((OnClickListener) this);
	}
	
	/* The Functions to perform when buttons are clicked*/
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.qstart:
			Intent qstartintent = new Intent(this, QuickStart.class);
		    startActivity(qstartintent);  //Passing control to the Quick start class
			break;
		case R.id.newusr:
			Intent newusrintent = new Intent(this, NewUsr.class);
		    startActivity(newusrintent);      //Passing control to the New User class
			break;
		case R.id.button3:
			Intent logintent = new Intent(this, Login.class);
		    startActivity(logintent); //Passing control to the Login class
			break;
		case R.id.button4: //Quitting from the app
			finish();
			break;
		}
	}

}
