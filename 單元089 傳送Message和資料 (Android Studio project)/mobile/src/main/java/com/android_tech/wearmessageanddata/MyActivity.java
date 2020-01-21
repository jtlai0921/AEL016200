package com.android_tech.wearmessageanddata;

import android.content.IntentSender;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Date;
import java.util.List;

public class MyActivity extends ActionBarActivity {

    //排除Google Play services連線錯誤用的辨識碼。
    private static final int GOO_API_CLIENT_REQUEST_RESOLVE_ERROR = 1000;
    private static final String WEARABLE_PATH_MESSAGE = "/message";
    private static final String WEARABLE_PATH_SEND_DATA = "/send-data";
    private static final String DATA_KEY = "data key";
    private static final String DATA_TIME = "time";

    private GoogleApiClient mGoogleApiClient;

    // 用來處理與Google Play services連線的錯誤。
    private boolean mbResolvingGooApiClientError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(btnSendMsgOnClick);

        Button btnSendData = (Button) findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(btnSendDataOnClick);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(gooApiClientConnCallback)
                .addOnConnectionFailedListener(gooApiClientOnConnFail)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        }
                    });
                }
            };

    private class AsyncTaskSendMessageToOtherDevices extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            // 取得所有連線的Android裝置。
            NodeApi.GetConnectedNodesResult connectedWearableDevices =
                    Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();

            // 傳送message給每一個連線的Android裝置。
            for (Node node : connectedWearableDevices.getNodes()) {
                // 把Message要附帶的資料儲存在一個位元組陣列中。
                byte[] payload = "手機App傳來的message。".getBytes();

                // 傳送message。
                Wearable.MessageApi.sendMessage(
                        mGoogleApiClient, node.getId(), WEARABLE_PATH_MESSAGE,
                        payload);
            }

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
                    Toast.makeText(getApplicationContext(), "onDataChanged().", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    };

    private View.OnClickListener btnSendMsgOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AsyncTaskSendMessageToOtherDevices().execute();
        }
    };

    private View.OnClickListener btnSendDataOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 將App專案中的影像檔包裝成Asset物件。
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image);
            Asset assetImage = Asset.createFromUri(uri);

            // 建立傳送資料的相關物件。
            PutDataMapRequest dataMapRequest = PutDataMapRequest.create(WEARABLE_PATH_SEND_DATA);
            dataMapRequest.getDataMap().putAsset(DATA_KEY, assetImage);

            // 一定要加時間，否則下次傳送資料時就不會更新（對方不會收到資料）。
            dataMapRequest.getDataMap().putLong(DATA_TIME, new Date().getTime());

            PutDataRequest request = dataMapRequest.asPutDataRequest();
            Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            Toast.makeText(getApplicationContext(),
                                    "資料傳送成功。",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

        }
    };
}
