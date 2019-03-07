package serzhan.com.recyclerview1;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import serzhan.com.recyclerview1.recyclerview.DummyValue;
import serzhan.com.recyclerview1.recyclerview.ItemTypes;

public class MyService extends Service {

    static final int MESSAGE_REGISTER = 0;
    static final int MESSAGE_UNREGISTER = 1;
    static final int MESSAGE_ADD = 2;
    static final int MESSAGE_DELETE = 3;

    private Random random = new Random();
    private List<Messenger> messengers = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Messenger messenger = new Messenger(new IncomingHandler());

    private Thread customThread = new Thread(){
        @Override
        public void run() {
            while(!isInterrupted()) {
                DummyValue dummyValue;
                switch (random.nextInt(4)) {
                    case 0: {
                        dummyValue = new DummyValue();
                        dummyValue.setType(ItemTypes.FIRST_VIEW_HOLDER.getType());
                        dummyValue.setValue("First View Holder " + getRandomNumbers());
                        sendMessageToClients(Message.obtain(null, MESSAGE_ADD, dummyValue));
                        break;
                    }
                    case 1: {
                        dummyValue = new DummyValue();
                        dummyValue.setType(ItemTypes.SECOND_VIEW_HOLDER.getType());
                        dummyValue.setValue("Second View Holder " + getRandomNumbers());
                        sendMessageToClients(Message.obtain(null, MESSAGE_ADD, dummyValue));
                        break;
                    }
                    case 2: {
                        dummyValue = new DummyValue();
                        dummyValue.setType(ItemTypes.THIRD_VIEW_HOLDER.getType());
                        dummyValue.setValue("Third View Holder " + getRandomNumbers());
                        sendMessageToClients(Message.obtain(null, MESSAGE_ADD, dummyValue));
                        break;
                    }
//                    case 3:
//                        dummyValue = new DummyValue();
//                        dummyValue.setType(ItemTypes.FOURTH_VIEW_HOLDER.getType());
//                        dummyValue.setValue("Fourth View Holder " + getRandomNumbers());
//                        sendMessageToClients(Message.obtain(null, MESSAGE_ADD, dummyValue));
//                        break;
                    case 3:
                        sendMessageToClients(Message.obtain(null, MESSAGE_DELETE));
                        break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        customThread.start();
    }

    private void sendMessageToClients(Message message) {
        try {
            for (Messenger messenger : messengers) {
                messenger.send(message);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private String getRandomNumbers() {
        return String.valueOf(random.nextInt(100));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customThread.interrupt();
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_REGISTER:
                    messengers.add(msg.replyTo);
                    break;
                case  MESSAGE_UNREGISTER:
                    messengers.remove(msg.replyTo);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }
}
