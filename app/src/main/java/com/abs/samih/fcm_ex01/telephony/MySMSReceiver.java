package com.abs.samih.fcm_ex01.telephony;

import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.abs.samih.fcm_ex01.MngTaskActivity;
import com.abs.samih.fcm_ex01.R;
import com.abs.samih.fcm_ex01.data.MyTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

//1.
public class MySMSReceiver extends BroadcastReceiver {
    public MySMSReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        Toast.makeText(context,"HIIIIII SMS",Toast.LENGTH_LONG).show();
        //1.
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle=intent.getExtras();
            Object[] pdus=(Object[])bundle.get("pdus");
            String smsInfo = "";
            String inPhoneNum="";
            for (int i = 0; i <pdus.length ; i++)
            {
                SmsMessage smsMsg=SmsMessage.createFromPdu((byte[])pdus[i]);
                inPhoneNum= smsMsg.getDisplayOriginatingAddress();
                smsInfo+=smsMsg.getDisplayMessageBody()+",";
            }

            if (FirebaseDatabase.getInstance()!=null) //if the user signed in
            {
                Date date= Calendar.getInstance().getTime();
                MyTask myTask=new MyTask(smsInfo,false,date,1,"",inPhoneNum);
                //6 save
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                //
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","_");
                //all my task will be under my mail under the root MyTasks
                //child can not contain chars: $,#,[,], . ,.....
                reference.child(email).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null)
                        {
                            Toast.makeText(context,"Save successful",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
            //auto sms response
            if(smsInfo.contains("hello"))
            {
                //you need to add SEND_SMS uses permission
                SmsManager manager=SmsManager.getDefault();
                manager.sendTextMessage(inPhoneNum,null,"welcom",null,null);

            }

        }

    }

    private void makeNotification(Context context)
    {
        //1.
        NotificationCompat.Builder builder=
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Tasks Manger")
                .setContentText("New SMS Added to your Tasks");

        //2. response to the notification selection
        Intent resIntent=new Intent(context, MngTaskActivity.class);
        PendingIntent resPendingIntent=
                PendingIntent.getActivity(context,0,resIntent,PendingIntent.FLAG_UPDATE_CURRENT);


    }

}
