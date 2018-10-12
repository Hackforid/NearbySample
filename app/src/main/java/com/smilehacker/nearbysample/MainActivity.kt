package com.smilehacker.nearbysample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class MainActivity : AppCompatActivity() {

    private val mBtnRua by lazy { findViewById<Button>(R.id.btn_rua) }
    private val mEtName by lazy { findViewById<EditText>(R.id.et_name) }
    private val mTvIds by lazy { findViewById<TextView>(R.id.tv_ids) }
    private val mConnClient : ConnectionsClient by lazy { Nearby.getConnectionsClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnRua.setOnClickListener {
            val name = mEtName.text.toString()
            discovery(name)
        }

    }

    private fun discovery(name: String) {
        mConnClient
                .startAdvertising(name, "com.smilehacker.nearbysample",
                        object: ConnectionLifecycleCallback() {
                            override fun onConnectionResult(endpointId: String, info: ConnectionResolution) {
                            }

                            override fun onDisconnected(endpointId: String) {
                            }

                            override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
                            }

                        },
                        AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build())
                .addOnSuccessListener {
                    Log.i("xx", "start advertising succ")
                }
                .addOnFailureListener {
                    e -> Log.e("xx", "start advertising fail", e)
                }
        mConnClient
            .startDiscovery("com.smilehacker.nearbysample",
            object: EndpointDiscoveryCallback() {
                @SuppressLint("SetTextI18n")
                override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                    Log.i("xx", "endpointId=$endpointId name=${info.endpointName} serviceId=${info.serviceId}")
                    mTvIds.post {
                        mTvIds.text = "${mTvIds.text}\n${info.endpointName}"
                    }
                }

                override fun onEndpointLost(endpointId: String) {
                    Log.i("xx", "endpoint lost $endpointId")
                }

            },
                    DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build())
            .addOnSuccessListener {
                Log.i("", "start discovery succ")
            }
            .addOnFailureListener {
                e -> Log.e("", "start discovery fail", e)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        mConnClient.stopAdvertising()
        mConnClient.stopDiscovery()
    }
}
