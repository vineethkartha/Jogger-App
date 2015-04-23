package bits.csg523.project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUsr extends Activity implements OnClickListener{

	Jogger j=new Jogger();
	SQLController SQLcon;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_usr);
		SQLcon = new SQLController(this);
		SQLcon.open();
		Button b5= (Button)findViewById(R.id.sub);
		b5.setOnClickListener((OnClickListener) this);
	}
	
	@Override
	public void onClick(View v)
	{
		EditText namebox=(EditText)findViewById(R.id.name);
		EditText agebox=(EditText)findViewById(R.id.age);
		EditText heightbox=(EditText)findViewById(R.id.height);
		EditText weightbox=(EditText)findViewById(R.id.weight);
		EditText passbox=(EditText)findViewById(R.id.pass);
		switch(v.getId())
		{
		case R.id.sub: 
			
			try{
				j.setName(namebox.getText().toString());
				j.setAge(Integer.parseInt(agebox.getText().toString()));
				j.setHeight(Integer.parseInt(heightbox.getText().toString()));
				j.setWeight(Integer.parseInt(weightbox.getText().toString()));
				j.setPasswd(passbox.getText().toString());
				j.setBMI();
				SQLcon.open();
				if(SQLcon.insert(j.getName(), j.getAge(), j.getHeight(), j.getWeight(), j.getBMI(), j.getPasswd()))
				{
					Toast.makeText(this, "Submitted",Toast.LENGTH_LONG).show();
					Intent logintent = new Intent(this, Login.class);
					startActivity(logintent);
					finish();
				}
				else
					Toast.makeText(this, "Please Use a different user name",Toast.LENGTH_LONG).show();
				SQLcon.close();
				
			}
				catch(NumberFormatException e){
					Toast.makeText(this, "Please Enter data",Toast.LENGTH_LONG).show();
				}
			break;
		}
	}
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

}
