package seniordesign.breatheband;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import seniordesign.breatheband.R;


public class StressLog extends Activity {

    private ArrayAdapter<StressResponse> adapter;
    private ListView listView;
    private static ArrayList<StressResponse> stressResponses;
    private static int indexOfEditedResponse;
    private static StressResponse newResponse;
    private static ArrayList<StressResponse> newStressResponses;

    public void loadResponses()
    {
//        if(stressResponses != null)
//            return;
        String filename = "arrayFile";
        FileInputStream filein;

        try{
            File file = new File(filename);

            filein = openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(filein);
            ArrayList<StressResponse> fileResponses = (ArrayList<StressResponse>)in.readObject();
            if(newStressResponses != null) {
                fileResponses.addAll(newStressResponses);
                System.out.println("new responses");
                newStressResponses = null;
            }
            setLoadedResponses(fileResponses);
            in.close();
            filein.close();
        } catch(Exception e)
        {
            e.printStackTrace();
            ArrayList<StressResponse> defaultResponses = new ArrayList<StressResponse>();

            defaultResponses.add(new StressResponse(new GregorianCalendar(2015, 5, 5, 14, 53)));
            defaultResponses.add(new StressResponse(new GregorianCalendar(2015, 5, 7, 4, 3)));
            defaultResponses.add(new StressResponse(new GregorianCalendar(2015, 5, 9, 23, 35)));
            defaultResponses.add(new StressResponse(new GregorianCalendar(2015, 10, 31, 0, 1)));
            defaultResponses.add(new StressResponse(new GregorianCalendar(2015, 11, 5, 12, 19)));
            setLoadedResponses(defaultResponses);
        }

    }

    //used to add multiple stress responses when synced from watch
    public static void addNewResponse(StressResponse response)
    {
        if(newStressResponses == null)
            newStressResponses = new ArrayList<StressResponse>();
        for(StressResponse s: newStressResponses)
        {
            if(s.compareTo(response) == 0)
                return;
        }
        newStressResponses.add(response);
    }

    private static void setLoadedResponses(ArrayList<StressResponse> fileResponses) {
        stressResponses = fileResponses;

        Collections.sort(stressResponses);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_log);

        loadResponses();

        adapter = new ResponseAdapter(this, R.layout.listview_item_row, stressResponses);

        OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if(position != 0) {
                    StressLog.setIndexOfEditedResponse(position - 1);
                    EditDescription.setResponse(stressResponses.get(position - 1));
                    Intent appInfo = new Intent(StressLog.this, EditDescription.class);
                    startActivity(appInfo);
                }
            }
        };

        listView = (ListView) findViewById(R.id.stressList);
        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    public static void setIndexOfEditedResponse(int newIndex)
    {
        indexOfEditedResponse = newIndex;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(newResponse != null && newResponse.isToBeDeleted())
            stressResponses.remove(indexOfEditedResponse);
        else if(newResponse != null)
            stressResponses.set(indexOfEditedResponse, newResponse);
        newResponse = null;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        writeArrayToFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stress_log, menu);
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

    //used when only a single response is being added, which is the case for manual response entry
    public static void setNewResponse(StressResponse response)
    {
        newResponse = response;
    }

    private void writeArrayToFile()
    {
        String filename = "arrayFile";
        FileOutputStream fileout;

        try{
            File file = new File(filename);

            fileout = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(stressResponses);
            out.close();
            fileout.close();

        } catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void addNewResponse(View view)
    {
        stressResponses.add(new StressResponse());
        Collections.sort(stressResponses);
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
        view.refreshDrawableState();
    }
}