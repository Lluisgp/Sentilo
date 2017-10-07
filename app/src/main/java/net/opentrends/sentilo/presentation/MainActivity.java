package net.opentrends.sentilo.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import net.opentrends.sentilo.R;
import net.opentrends.sentilo.domain.models.ApplicationConfig;
import net.opentrends.sentilo.data.sentilo.repository.SentiloDataRepository;
import net.opentrends.sentilo.domain.LocationRepository;
import net.opentrends.sentilo.domain.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.textView)
    TextView textView;


    LocationRepository locationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        locationRepository = SentiloDataRepository.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationConfig applicationConfig = locationRepository.getApplicationConfig();
        token.setText(applicationConfig.getToken());
        url.setText(applicationConfig.getUrl());

        nick.setText(applicationConfig.getNick());
        secondsBetweenLocationUpdates.setText(String.valueOf(applicationConfig.getSecondsBetweenLocationUpdates()));
        switchLocationTracker.setChecked(applicationConfig.isEnableLocation());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        this.textView.setText(event.getMessage());
    };

    @OnClick(R.id.buttonSave)
    public void onClickSaveButton() {
        updateApplicationConfig();
        startLocationTracker();
    }

    private void updateApplicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setToken(token.getText().toString());
        applicationConfig.setUrl(url.getText().toString());
        applicationConfig.setNick(nick.getText().toString());
        applicationConfig.setSecondsBetweenLocationUpdates(Integer.parseInt(secondsBetweenLocationUpdates.getText().toString()));
        applicationConfig.setEnableLocation(switchLocationTracker.isChecked());

        locationRepository.updateApplicationConfig(applicationConfig);
    }

    private void startLocationTracker() {
        if (locationTracker != null) {
            locationTracker.stopLocationService(getBaseContext());
        }
        if (switchLocationTracker.isChecked()) {
            locationTracker = new LocationTracker("sentilo.location")
                    .setInterval(locationRepository.getApplicationConfig().getSecondsBetweenLocationUpdates() * 1000)
                    .setGps(true)
                    .setNetWork(false)
                    .start(getBaseContext(), MainActivity.this);
        }
    }

    public void sentTextToConsole(String consoleText) {
        //this.consoleText.setText(consoleText);
    }
}
