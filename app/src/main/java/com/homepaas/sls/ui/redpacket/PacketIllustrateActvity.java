package com.homepaas.sls.ui.redpacket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PacketIllustrateActvity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.packet_illustrate_text)
    TextView packetIllustrateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_illustrate_actvity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
