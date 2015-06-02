package io.github.wapmorgan.onecloudmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


import org.w3c.dom.Text;

import io.github.wapmorgan.onecloudmanager.api.Server;
import io.github.wapmorgan.onecloudmanager.dummy.DummyContent;

/**
 * A fragment representing a single Server detail screen.
 * This fragment is either contained in a {@link ServerListActivity}
 * in two-pane mode (on tablets) or a {@link ServerDetailActivity}
 * on handsets.
 */
public class ServerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Server server;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ServerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Log.d("UI", "Given ID: " + getArguments().getString(ARG_ITEM_ID));
            server = ApiCache.serversList.get(Integer.valueOf(getArguments().getString(ARG_ITEM_ID)) - 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_server_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (server != null) {
            ((TextView) rootView.findViewById(R.id.server_name)).setText(server.getName());
            ((TextView) rootView.findViewById(R.id.server_id)).setText("#" + server.getId());

            ((TextView) rootView.findViewById(R.id.server_state)).setText(server.getState());

            ToggleButton toggle = (ToggleButton) rootView.findViewById(R.id.server_power_toggle);
            toggle.setChecked(server.isPowerOn());
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (server.isPowerOn()) {
                        new PowerChanger().execute("PowerOff");
                    } else {
                        new PowerChanger().execute("PowerOn");
                    }
                }
            });

            ((TextView) rootView.findViewById(R.id.server_cpu)).setText(String.valueOf(server.getCpu()));
            int ram = server.getRam();
            if (ram >= 1024)
                ((TextView) rootView.findViewById(R.id.server_ram)).setText((ram / 1024) + " Gb");
            else
                ((TextView) rootView.findViewById(R.id.server_ram)).setText(ram + " Mb");
            ((TextView) rootView.findViewById(R.id.server_hdd)).setText(server.getHdd() + " Gb (" + server.getHddType() + ")");
            ((TextView) rootView.findViewById(R.id.server_ip)).setText(server.getIp());
            ((TextView) rootView.findViewById(R.id.server_performance)).setText(server.isHighPerformance() ? "High" : "Normal");

            ((TextView) rootView.findViewById(R.id.server_admin_username)).setText(server.getAdminUserName());
            ((TextView) rootView.findViewById(R.id.server_admin_password)).setText(server.getAdminPassword());
        }

        return rootView;
    }

    public class PowerChanger extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... params) {
            return OneCloudApi.changeServerPower(server.getId(), params[0]);
        }

        protected void onPostExecute(Boolean result) {
            NotificationCompat.Builder mBuilder;
            if (result) {
                mBuilder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.power_changed_title))
                        .setContentText(server.getName() + ": " + getString(R.string.power_changed_content));
            } else {
                mBuilder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.power_hasnt_changed_title))
                        .setContentText(server.getName() + ": " + getString(R.string.power_hasnt_changed_content));
            }
            ((NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, mBuilder.build());
        }
    }
}
