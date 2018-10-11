package com.smilehacker.nearbysample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun foo() {
        Nearby.getConnectionsClient(this)
                .startAdvertising("Device A", "com.smilehacker.nearbysample",
                        object: ConnectionLifecycleCallback() {
                            override fun onConnectionResult(p0: String, p1: ConnectionResolution) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDisconnected(p0: String) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onConnectionInitiated(p0: String, p1: ConnectionInfo) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        },
                        AdvertisingOptions(Strategy.P2P_CLUSTER))

        Nearby.getConnectionsClient(this)
                .startDiscovery("Device B",
                        object: EndpointDiscoveryCallback() {
                            override fun onEndpointFound(p0: String, p1: DiscoveredEndpointInfo) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onEndpointLost(p0: String) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        },
                        DiscoveryOptions(Strategy.P2P_CLUSTER))
    }
}
