package io.github.wapmorgan.onecloudmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
            server = ApiCache.serversList.get(getArguments().getInt(ARG_ITEM_ID));
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
}
