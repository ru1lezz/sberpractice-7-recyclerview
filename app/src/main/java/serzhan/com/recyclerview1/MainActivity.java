package serzhan.com.recyclerview1;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import serzhan.com.recyclerview1.recyclerview.CustomAdapter;
import serzhan.com.recyclerview1.recyclerview.DummyValue;

public class MainActivity extends AppCompatActivity {

    private List<DummyValue> mValues;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private Messenger mServiceMessenger;
    @SuppressLint("HandlerLeak")
    private Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mValues = new ArrayList<>();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new CustomAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addToList(DummyValue value) {
        mValues.add(value);
        mAdapter.setItems(mValues);
    }

    private void deleteFromList() {
        if(mValues.size() > 0) {
            mValues.remove(0);
            mAdapter.setItems(mValues);
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
            Message msg = Message.obtain(null, MyService.MESSAGE_REGISTER);
            msg.replyTo = mMessenger;
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService();
    }

    private void bindService() {
        bindService(MyService.newIntent(MainActivity.this),
                mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        Message msg = Message.obtain(null,
                MyService.MESSAGE_UNREGISTER);
        msg.replyTo = mMessenger;
        try{
            mServiceMessenger.send(msg);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyService.MESSAGE_ADD:
                    addToList((DummyValue) msg.obj);
                    break;
                case MyService.MESSAGE_DELETE:
                    deleteFromList();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
