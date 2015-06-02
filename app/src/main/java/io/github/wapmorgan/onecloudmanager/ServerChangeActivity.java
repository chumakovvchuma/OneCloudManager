package io.github.wapmorgan.onecloudmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import io.github.wapmorgan.onecloudmanager.api.Server;


public class ServerChangeActivity extends ActionBarActivity {

    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_change);
        server = ApiCache.serversList.get(Integer.valueOf(getIntent().getStringExtra(ServerDetailFragment.ARG_ITEM_ID)) - 1);

        final TextView cpu_value = (TextView) findViewById(R.id.cpu_value);
        ((SeekBar) findViewById(R.id.cpu_seekbar)).setProgress(server.getCpu());
        ((SeekBar)findViewById(R.id.cpu_seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cpu_value.setText(progress > 1 ? progress + " cores" : "1 core");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final TextView ram_value = (TextView) findViewById(R.id.ram_value);
        final int min_ram;
        if (server.getImage().substring(0, 7).toLowerCase().equals("windows")) {
            min_ram = 1024;
        } else {
            min_ram = 512;
        }
        ((SeekBar)findViewById(R.id.ram_seekbar)).setProgress(server.getRam());
        ((SeekBar)findViewById(R.id.ram_seekbar)).setMax(16384 - min_ram);
        ((SeekBar)findViewById(R.id.ram_seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += min_ram;
                if (progress < 1024) {
                    if (progress % 256 > 0) {
                        progress = Math.round(progress / 256) * 256;
                    }
                } else {
                    if (progress % 1024 > 0) {
                        progress = Math.round(progress / 1024) * 1024;
                    }
                }
                seekBar.setProgress(progress);
                if (progress < 1024)
                    ram_value.setText(progress + " Mb");
                else
                    ram_value.setText((progress / 1024) + " Gb");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final TextView hdd_value = (TextView) findViewById(R.id.hdd_value);
        final int min_hdd;
        if (server.getImage().substring(0, 7).toLowerCase().equals("windows")) {
            min_hdd = 40;
        } else {
            min_hdd = 10;
        }
        ((SeekBar) findViewById(R.id.hdd_seekbar)).setProgress(server.getHdd());
        ((SeekBar) findViewById(R.id.hdd_seekbar)).setMax(250 - min_hdd);
        ((SeekBar) findViewById(R.id.hdd_seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += min_hdd;
                if (progress % 10 > 0) {
                    progress = Math.round(progress / 10) * 10;
                }
                hdd_value.setText(progress + " Gb");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
