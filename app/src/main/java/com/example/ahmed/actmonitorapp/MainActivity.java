package com.example.ahmed.actmonitorapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity
    {
        private Accelerometer accelerometer;
        private Gyroscope gyroscope;
        private Light light;
        private Button sensingButton;

        private PendingIntent pendingIntent;
        private AlarmManager manager;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            accelerometer = Accelerometer.getInstance(this);
            gyroscope = Gyroscope.getInstance(this);
            light = Light.getInstance(this);
            sensingButton = (Button) findViewById(R.id.sensingButton);











          Calendar cur_cal = new GregorianCalendar();
          cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

          Calendar cal = new GregorianCalendar();
          cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
          cal.set(Calendar.HOUR_OF_DAY, 18);
          cal.set(Calendar.MINUTE, 32);
          cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
          cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
          cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
          cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
          // Retrieve a PendingIntent that will perform a broadcast
          Intent alarmIntent = new Intent(this, AlarmReceiver.class);
          pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
          AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
          alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pendingIntent);
        }

        @Override
        protected void onResume()
        {
            super.onResume();
            //setButtonText();
        }

        @Override
        protected void onPause()
        {
            super.onPause();
            stopSensing();
        }

    public void startSensing()
    {
        if ((!accelerometer.isSensing()) && (!light.isSensing()) && (!gyroscope.isSensing()))
        {
            try
            {
                accelerometer.start(this);
                gyroscope.start(this);
                light.start(this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void stopSensing()
    {
        if ((accelerometer.isSensing()) && (light.isSensing()) && (gyroscope.isSensing()))
        {
            try
            {
                accelerometer.stop();
                gyroscope.stop();
                light.stop();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

/*    private void setRecyclerView()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (recyclerView != null)
        {
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            LabelAdapter adapter = new LabelAdapter();
            recyclerView.setAdapter(adapter);
        }
    }*/

    /*private void setButtonText()
    {
        if ((!accelerometer.isSensing()) && (!light.isSensing())  && (!gyroscope.isSensing()))
        {
            sensingButton.setText(R.string.sensing_start);
        }
        else
        {
            sensingButton.setText(R.string.sensing_stop);
        }
    }*/

    public void onSensingButtonClicked(final View view)
    {
            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = 10000;

            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();


            if ((!accelerometer.isSensing()) && (!light.isSensing()) && (!gyroscope.isSensing())) {
                startSensing();
            } else {
                stopSensing();
            }
            //setButtonText();


/*        if (sensingButton.getText() == "Stop sensing")
        {
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
            return;
        }*/
    }

    public void onSensingButtonClickedToStop(final View view)
    {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();

        if ((!accelerometer.isSensing()) && (!light.isSensing()) && (!gyroscope.isSensing())) {
            startSensing();
        } else {
            stopSensing();
        }

    }
    }
