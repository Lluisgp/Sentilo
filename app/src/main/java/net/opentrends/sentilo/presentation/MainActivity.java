package net.opentrends.sentilo.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;

import net.opentrends.sentilo.R;
import net.opentrends.sentilo.data.models.SentiloConfig;
import net.opentrends.sentilo.data.sentilo.SentiloDataRepository;
import net.opentrends.sentilo.data.sentilo.SentiloRepository;

import br.com.safety.locationlistenerhelper.core.LocationTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private LocationTracker locationTracker;

    @BindView(R.id.token)
    EditText token;

    @BindView(R.id.url)
    EditText url;

    @BindView(R.id.nick)
    EditText nick;

    @BindView(R.id.secondsBetweenLocationUpdates)
    EditText secondsBetweenLocationUpdates;

    @BindView(R.id.switchLocationTracker)
    Switch switchLocationTracker;

    SentiloRepository sentiloRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        sentiloRepository = SentiloDataRepository.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SentiloConfig sentiloConfig = sentiloRepository.getSentiloData();
        token.setText(sentiloConfig.getToken());
        url.setText(sentiloConfig.getUrl());
        nick.setText(sentiloConfig.getNick());
        secondsBetweenLocationUpdates.setText(String.valueOf(sentiloConfig.getSecondsBetweenLocationUpdates()));
        switchLocationTracker.setChecked(sentiloConfig.isEnableLocation());
    }

    @OnClick(R.id.buttonSave)
    public void onClickSaveButton() {
        updateSentiloData();
        startLocationTracker();
    }

    private void updateSentiloData() {
        SentiloConfig sentiloConfig = new SentiloConfig();
        sentiloConfig.setToken(token.getText().toString());
        sentiloConfig.setUrl(url.getText().toString());
        sentiloConfig.setNick(nick.getText().toString());
        sentiloConfig.setSecondsBetweenLocationUpdates(Integer.parseInt(secondsBetweenLocationUpdates.getText().toString()) * 1000);
        sentiloConfig.setEnableLocation(switchLocationTracker.isChecked());

        sentiloRepository.updateSentiloData(sentiloConfig);
    }

    private void startLocationTracker() {
        if (locationTracker != null) {
            locationTracker.stopLocationService(getBaseContext());
        }
        if (switchLocationTracker.isChecked()) {
            locationTracker = new LocationTracker("sentilo.location")
                    .setInterval(sentiloRepository.getSentiloData().getSecondsBetweenLocationUpdates())
                    .setGps(true)
                    .setNetWork(false)
                    .start(getBaseContext(), MainActivity.this);
        }
    }
}
