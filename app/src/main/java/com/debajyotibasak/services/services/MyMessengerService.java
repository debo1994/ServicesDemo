package com.debajyotibasak.services.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by debajyotibasak on 04/03/18.
 */

public class MyMessengerService extends Service {

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 43:
                    Bundle bundle = msg.getData();
                    int numOne = bundle.getInt("numOne", 0);
                    int numTwo = bundle.getInt("numTwo", 0);

                    int result = addNumber(numOne, numTwo);
                    Toast.makeText(getApplicationContext(), "result : " + result, Toast.LENGTH_SHORT).show();

                    Messenger incomingMessenger = msg.replyTo;

                    // Send data back to activity
                    Message msgToActivity = Message.obtain(null, 87);
                    Bundle bundleToActivity = new Bundle();
                    bundleToActivity.putInt("result", result);

                    msgToActivity.setData(bundleToActivity);

                    try {
                        incomingMessenger.send(msgToActivity);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    Messenger mMessenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public int addNumber(int a, int b) {
        return a + b;
    }


}
