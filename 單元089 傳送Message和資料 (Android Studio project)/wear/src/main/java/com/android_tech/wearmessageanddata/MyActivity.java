package com.android_tech.wearmessageanddata;

import android.app.Activity;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;
import java.util.List;

public class MyActivity extends Activity {

    //排除Google Play services連線錯誤用的辨識碼。
    private static final int GOO_API_CLIENT_REQUEST_RESOLVE_ERROR = 1000;
    private static final String WEARABLE_PATH_MESSAGE = "/message";
    private static final String WEARABLE_PATH_SEND_DATA = "/send-data";
    private static final String DATA_KEY = "data key";

    private GoogleApiClient mGoogleApiClient;

    // 用來處理與Google Play services連線的錯誤。
    private boolean mbResolvingGooApiClientError = false;

    private TextView mTextView;
    private View mViewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mViewRoot = (View) stub.findViewById(R.id.viewRoot);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(gooApiClientConnCallback)
                .addOnConnectionFailedListener(gooApiClientOnConnFail)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mbResolvingGooApiClientError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (!mbResolvingGooApiClientError) {
            Wearable.MessageApi.removeListener(mGoogleApiClient, wearableMsgListener);
            Wearable.DataApi.removeListener(mGoogleApiClient, wearableDataListener);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private GoogleApiClient.ConnectionCallbacks gooApiClientConnCallback =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    mbResolvingGooApiClientError = false;
                    Wearable.MessageApi.addListener(mGoogleApiClient, wearableMsgListener);
                    Wearable.DataApi.addListener(mGoogleApiClient, wearableDataListener);
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Toast.makeText(getApplicationContext(),
                            "Google API Client無法連線。", Toast.LENGTH_LONG)
                            .show();
                }
            };

    private GoogleApiClient.OnConnectionFailedListener gooApiClientOnConnFail =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (mbResolvingGooApiClientError) {
                        return;  // 正在處理目前的錯誤。
                    } else if (connectionResult.hasResolution()) {
                        try {    // Google Play services連線錯誤，但是可以排除。
                            mbResolvingGooApiClientError = true;
                            connectionResult.startResolutionForResult(MyActivity.this,
                                    GOO_API_CLIENT_REQUEST_RESOLVE_ERROR);
                        } catch (IntentSender.SendIntentException e) {
                            // 嘗試重新連線Google Play services。
                            mbResolvingGooApiClientError = false;
                            mGoogleApiClient.connect();
                        }
                    } else {
                        mbResolvingGooApiClientError = false;
                        Wearable.MessageApi.removeListener(mGoogleApiClient, wearableMsgListener);
                        Wearable.DataApi.removeListener(mGoogleApiClient, wearableDataListener);
                    }
                }
            };

    private MessageApi.MessageListener wearableMsgListener = new
            MessageApi.MessageListener() {
                @Override
                public void onMessageReceived(final MessageEvent messageEvent) {
                    // 呼叫messageEvent.getData()取得message中附帶的位元組陣列。
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = null;
                            try {
                                s = new String(messageEvent.getData(), "UTF-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
                                    .show();

                            new AsyncTaskReplyMessage().execute(messageEvent.getSourceNodeId());
                        }
                    });
                }
            };

    private class AsyncTaskReplyMessage extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            byte[] payload = "收到Message".getBytes();
            // Send the message and get the result.
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                    mGoogleApiClient, strings[0], WEARABLE_PATH_MESSAGE,
                    payload).await();
            return null;
        }
    }

    private DataApi.DataListener wearableDataListener = new DataApi.DataListener() {
        @Override
        public void onDataChanged(DataEventBuffer dataEvents) {
            // 取得目前收到的所有資料項目。
            List<DataEvent> listDataEvents = FreezableUtils.freezeIterable(dataEvents);
            dataEvents.close();

            // 比對每一筆資料項目，找出我們需要的資料。
            for (DataEvent event : listDataEvents) {
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_PATH_SEND_DATA)) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    Asset asset = dataMapItem.getDataMap()
                            .getAsset(DATA_KEY);
                    InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                            mGoogleApiClient, asset).await().getInputStream();
                    final Bitmap bitmap =  BitmapFactory.decodeStream(assetInputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewRoot.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }
                    });
                }
            }
        }
    };

}
