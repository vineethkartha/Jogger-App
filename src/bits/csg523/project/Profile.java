package bits.csg523.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity implements OnClickListener{

	private Button update;
	private TextView profname,bmi;
	private EditText age,ht,wt,passwd;
	
	SQLController SQLcon;
	Jogger j=new Jogger();
	
	private String usrname;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		Intent intent=getIntent();
		usrname=intent.getStringExtra("joggerName");
		SQLcon = new SQLController(this);
		SQLcon.open();
		j=SQLcon.getData(usrname);
		update=(Button)findViewById(R.id.profupdt);
		update.setOnClickListener(this);
		
		profname=(TextView)findViewById(R.id.profusr);
		profname.setText(j.getName());
		
		bmi=(TextView)findViewById(R.id.profbmi);
		bmi.setText(Float.toString(j.getBMI()));
		
		age=(EditText)findViewById(R.id.profage);
		age.setText(Integer.toString(j.getAge()));
		
		ht=(EditText)findViewById(R.id.profht);
		ht.setText(Float.toString(j.getHeight()));
		
		wt=(EditText)findViewById(R.id.profwt);
		wt.setText(Float.toString(j.getWeight()));
		
		passwd=(EditText)findViewById(R.id.profpasswd);
		passwd.setText(j.getPasswd());
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.profupdt:
			j.setAge(Integer.parseInt(age.getText().toString()));
			j.setHeight(Float.parseFloat(ht.getText().toString()));
			j.setWeight(Float.parseFloat(wt.getText().toString()));
			j.setPasswd(passwd.getText().toString());
			j.setBMI();
			Toast.makeText(this, j.getPasswd(),Toast.LENGTH_LONG).show();
			SQLcon.update(j.getName(), j.getAge(), j.getHeight(), j.getWeight(), j.getBMI(), j.getPasswd());
			Toast.makeText(this, "Updated...",Toast.LENGTH_LONG).show();
			break;
		
		}
		
	}
	
}