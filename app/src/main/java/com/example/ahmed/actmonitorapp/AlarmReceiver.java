package com.example.ahmed.actmonitorapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ahmed on 20/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        //just displaying a message
        //instead
        //i want to read sensors data and write em to CSV
        Toast.makeText(arg0, "I'm running instead of some sensors being captured", Toast.LENGTH_SHORT).show();
    }
}
