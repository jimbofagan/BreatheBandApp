package seniordesign.breatheband;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import seniordesign.breatheband.R;


public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView textView = (TextView) findViewById(R.id.stress_log);
        textView.setText(R.string.log_link);
        textView = (TextView) findViewById(R.id.breathing_exercise);
        textView.setText(R.string.exercise_link);
        textView = (TextView) findViewById(R.id.sync_with_device);
        textView.setText(R.string.sync_link);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void displayLog(View view)
    {
        Intent intent = new Intent(this, StressLog.class);
        startActivity(intent);
    }

    public void displayExercise(View view)
    {
        Intent intent = new Intent(this, BreathingExercise.class);
        startActivity(intent);
    }

    public void syncBreatheBand(View view)
    {
        Intent intent = new Intent(this, SyncStressLog.class);
        startActivity(intent);
    }
}
