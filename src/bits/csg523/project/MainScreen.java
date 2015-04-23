package bits.csg523.project;

/**
 * This is the main screen of the app, which displays maps and other such features
 * 
 *
 */

import android.content.Intent;
import android.provider.MediaStore;

import android.view.Menu;
import android.view.MenuItem;




public class MainScreen extends QuickStart{

	/**
	 * This class is an extension of the Quick start class here additional options are avaialble from the menu. You can edit profile, play Mp3 and set alarms
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId())
	    {
	    case R.id.alarm:  //Go to the page to set alarm
	    	Intent alarm=new Intent(this, Alarm.class);
	    	alarm.putExtra("joggerName",usrname);
	    	startActivity(alarm);
	        break;
	    case R.id.profile: //Make changes to the profile
	    	Intent prof=new Intent(this,Profile.class);
	    	prof.putExtra("joggerName",usrname);
	    	startActivity(prof);
	        break;
	    case R.id.mp3:
			Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER); //Start the phones default MP3 player
			startActivity(intent);
	        break;
	    }
	    return true;
	}
}