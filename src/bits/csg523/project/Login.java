package bits.csg523.project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{

	SQLController SQLcon;
	Jogger j=new Jogger();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button logb= (Button)findViewById(R.id.login);
		logb.setOnClickListener((OnClickListener) this);
		SQLcon = new SQLController(this);
		SQLcon.open();
		 
	}
	
	
	@Override
	public void onClick(View v)
	{
		EditText namebox=(EditText)findViewById(R.id.usrlogname);
		EditText passbox=(EditText)findViewById(R.id.usrlogpass);
		switch(v.getId())
		{
		case R.id.login: 
			String storedPassword=SQLcon.loginCheck(namebox.getText().toString());
			String password=passbox.getText().toString();
			if(password.equals(storedPassword))
            {
				j=SQLcon.getData(namebox.getText().toString());
				Intent qstartintent = new Intent(this, MainScreen.class);
				 qstartintent.putExtra("Activity","LOGIN");
			    qstartintent.putExtra("joggerWeight", j.getWeight());
			    qstartintent.putExtra("joggerName", j.getName());
			    //qstartintent.putExtra("joggerPasswd", j.getPasswd());
			    startActivity(qstartintent);  
				finish();
                Toast.makeText(this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
            }
			SQLcon.close();
			break;
		}
	}
}
