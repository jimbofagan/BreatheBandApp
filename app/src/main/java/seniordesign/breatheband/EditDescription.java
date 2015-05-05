package seniordesign.breatheband;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class EditDescription extends Activity {

    private static StressResponse myResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);
        if(!myResponse.toString().equals("")) {
            EditText txt = (EditText) findViewById(R.id.description);
            txt.setText(myResponse.getDescription());
        }
        setTitle(myResponse.getCalendarDate()+" "+myResponse.getTime());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_description, menu);
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

    public static void setResponse(StressResponse response)
    {
        myResponse = response;
    }

    public void saveDescription(View view)
    {
        myResponse.undoDelete();
        myResponse.setDescription(((EditText) findViewById(R.id.description)).getText().toString());
        StressLog.setNewResponse(myResponse);
        ((TextView)findViewById(R.id.save_success)).setText("Save Successful!");
        ((TextView)findViewById(R.id.delete_success)).setText("");
    }

    public void clearSuccess(View view)
    {
        ((TextView)findViewById(R.id.save_success)).setText("");
    }

    public void removeResponse(View view)
    {
        myResponse.deleteResponse();
        StressLog.setNewResponse(myResponse);
        ((TextView)findViewById(R.id.delete_success)).setText("Delete Successful! Click \"Save Description\" to Undo");
        ((TextView)findViewById(R.id.save_success)).setText("");
    }
}
