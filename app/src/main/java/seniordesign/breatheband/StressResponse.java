package seniordesign.breatheband;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by James on 3/17/2015.
 */
public class StressResponse implements Serializable, Comparable<StressResponse>
{
    private GregorianCalendar date;
    private String description;
    private boolean toBeDeleted = false;

    public StressResponse(GregorianCalendar date, String description)
    {
        this.date = date;
        this.description = description;
    }

    public StressResponse(GregorianCalendar date)
    {
        this.date = date;
        this.description = "";
    }

    public StressResponse()
    {
        this.date = new GregorianCalendar();
        this.description = "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getShortenedDescription() {
        if(description.length() < 20)
            return description;
        else
            return description.substring(0,17)+"...";
    }

    public String getCalendarDate()
    {
        String calDate = "";
        int month = date.get(Calendar.MONTH) + 1;
        if(month < 10)
            calDate += "0";
        calDate += month + "/";
        int day = date.get(Calendar.DAY_OF_MONTH);
        if(day < 10)
            calDate += "0";
        calDate += day + "/";
        calDate += date.get(Calendar.YEAR);
        return calDate;
    }

    public String getTime()
    {
        String time = "";
        int hour = date.get(Calendar.HOUR_OF_DAY)%12;
        if(hour == 0)
            hour += 12;
        if(hour < 10)
            time +=0;
        time += hour+":";
        int minute = date.get(Calendar.MINUTE);
        if(minute < 10)
            time+=0;
        time+=minute + " ";
        int am_pm = date.get(Calendar.AM_PM);
        if(am_pm == Calendar.AM)
            time += "AM";
        else
            time += "PM";
        return time;
    }

    @Override
    public String toString() {
        String toReturn = "";
        toReturn += "<b>"+getCalendarDate()+" "+getTime()+" - "+"</b>" + description;
        return toReturn;
    }

    public void deleteResponse()
    {
        toBeDeleted = true;
    }
    public void undoDelete() {toBeDeleted = false;}

    public boolean isToBeDeleted()
    {
        return toBeDeleted;
    }

    public int compareTo(StressResponse other)
    {
        if(this.date.get(Calendar.MINUTE) == other.date.get(Calendar.MINUTE) &&
                this.date.get(Calendar.HOUR) == other.date.get(Calendar.HOUR) &&
                this.date.get(Calendar.DAY_OF_MONTH) == other.date.get(Calendar.DAY_OF_MONTH) &&
                this.date.get(Calendar.MONTH) == other.date.get(Calendar.MONTH))
            return 0;

        return other.date.compareTo(this.date);
    }
}
