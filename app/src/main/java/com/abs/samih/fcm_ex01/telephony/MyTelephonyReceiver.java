/**
 * 
 */
package com.abs.samih.fcm_ex01.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.ViewDebug.IntToString;
import android.widget.Toast;

import com.abs.samih.fcm_ex01.data.MyTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

/**
 * @author User
 * add to manifist before the application Tag:
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>//to catch incoming sms
*<uses-permission android:name="android.permission.SEND_SMS"/>// to have the ability to send sms
 *
 * add this decleration to the application clouse
 <receiver android:name=".telephony.MyTelephonyReceiver">// according to your class and package
	<intent-filter>
		<action android:name="android.provider.Telephony.SMS_RECEIVED"/>

	</intent-filter>
</receiver>
 *
 */
public class MyTelephonyReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(final Context context, Intent intent)
	{

		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED"))
		{
			// to get sms informations
			Bundle bundle = intent.getExtras();
			Object[] pdus = (Object[]) bundle.get("pdus");
			String smsInfo = "";
			String inPhoneNum="";
			for (int i = 0; i < pdus.length; i++)
			{
				SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) pdus[i]);
				inPhoneNum = smsMsg.getDisplayOriginatingAddress();//incoming number
				smsInfo += "Body:" + smsMsg.getDisplayMessageBody() + "\n"
						+ " From:" + inPhoneNum;

			}
			Log.d("MyTelephonyReceiver", "SMS_RECEIVED:" + smsInfo);
			Toast.makeText(context, "SMS_RECEIVED:" + smsInfo,
					Toast.LENGTH_LONG).show();
			Date date= Calendar.getInstance().getTime();
			MyTask myTask=new MyTask("sms:"+smsInfo,false,date,1,"",inPhoneNum);

			//6 save
			DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
			//
			String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
			//all my task will be under my mail under the root MyTasks
			//child can not contain chars: $,#,[,], . ,.....
			reference.child(email.replace(".","_")).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
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

//			if (smsInfo.indexOf("open") != -1)
//			{
//				Intent iactv = new Intent(context, TElphonyTestActivity.class);
//				iactv.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//��� ������ �������� ����� �������
//				iactv.putExtra("smsinfo", smsInfo);
//				context.startActivity(iactv);
//			}
//			if (smsInfo.contains("see you"))
//			{
//				// you need send sms permission
//				Log.d("MyTelephonyReceiver", "Good bye!" + inPhoneNum);
//				SmsManager smsMngr = SmsManager.getDefault();
//				smsMngr.sendTextMessage(inPhoneNum, null, "Good bye!", null,
//						null);
//			}
			

		}

	}

}
