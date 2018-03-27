package com.bit.cse;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Suhas on 3/6/2017.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

    }
}
