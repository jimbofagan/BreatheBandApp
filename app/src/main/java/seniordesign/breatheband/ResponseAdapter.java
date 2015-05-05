package seniordesign.breatheband;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James on 3/18/2015.
 */
public class ResponseAdapter extends ArrayAdapter<StressResponse>
{
    Context context;
    int layoutResourceId;
    ArrayList<StressResponse> data = null;

    public ResponseAdapter(Context context, int layoutResourceId, ArrayList<StressResponse> data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResponseHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResponseHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (ResponseHolder)row.getTag();
        }

        StressResponse response = data.get(position);
        holder.txtTitle.setText(Html.fromHtml(response.toString()));

        return row;
    }

    static class ResponseHolder
    {
        TextView txtTitle;
    }
}
