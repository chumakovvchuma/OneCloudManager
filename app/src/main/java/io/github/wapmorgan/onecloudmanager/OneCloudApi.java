package io.github.wapmorgan.onecloudmanager;

import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.wapmorgan.onecloudmanager.api.Network;
import io.github.wapmorgan.onecloudmanager.api.Server;

/**
 * Created by wm on 29.05.2015.
 */
public class OneCloudApi {
    private static String key;
    private static String BASE = "https://api.1cloud.ru";

    public static String httpGet(String urlStr) throws IOException {
        return httpGet(urlStr, new HashMap<String, String>());
    }

    public static String httpGet(String urlStr, Map<String, String> headers) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        for (String key : headers.keySet()) {
            conn.setRequestProperty(key, headers.get(key));
        }

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }

    public static String httpPost(String urlStr, String[] paramName,
                                  String[] paramVal) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        // Create the form content
        OutputStream out = conn.getOutputStream();
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        for (int i = 0; i < paramName.length; i++) {
            writer.write(paramName[i]);
            writer.write("=");
            writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
            writer.write("&");
        }
        writer.close();
        out.close();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }

    public static void setKey(String key) {
        OneCloudApi.key = key;
    }

    public static boolean checkKey() {
        if (TextUtils.isEmpty(OneCloudApi.key)) {
            Log.d("API", "Checking failed.");
            return false;
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + OneCloudApi.key);
        String result = "{}";
        try {
            result = OneCloudApi.httpGet(OneCloudApi.BASE + "/server", headers);
            Log.d("API", "Checking passed.");
            return true;
        } catch (IOException e) {
            Log.d("API", "Checking failed.");
            return false;
        }
    }

    public static List<Server> getServerList() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + OneCloudApi.key);
        String result = "{}";
        try {
            result = OneCloudApi.httpGet(OneCloudApi.BASE + "/server", headers);
        } catch (IOException e) {
            Log.e("API", "Could not download /servers");
            throw new RuntimeException(e);
        }
        List<Network> networkList = new ArrayList<Network>();
        List<Server> serverList = new ArrayList<Server>();
        try {
            final JSONArray servers = new JSONArray(result);
            JSONArray networks;
            final int n = servers.length();
            for (int i = 0; i < n; i++) {
                final JSONObject server = servers.getJSONObject(i);
                networkList.clear();
                networks = server.getJSONArray("LinkedNetworks");
                int nn = networks.length();
                for (int j = 0; j < nn; j++) {
                    final JSONObject network = networks.getJSONObject(j);
                    networkList.add(new Network().setId(network.getInt("NetworkID"))
                        .setIp(network.getString("IP"))
                        .setMac(network.getString("MAC")));
                }
                serverList.add(new Server().setId(server.getInt("ID"))
                    .setName(server.getString("Name"))
                    .setState(server.getString("State"))
                    .setIsPowerOn(server.getBoolean("IsPowerOn"))
                    .setCpu(server.getInt("CPU"))
                    .setRam(server.getInt("RAM"))
                    .setHdd(server.getInt("HDD"))
                    .setIp(server.getString("IP"))
                    .setAdminUserName(server.getString("AdminUserName"))
                    .setAdminPassword(server.getString("AdminPassword"))
                    .setImage(server.getString("Image"))
                    .setIsHighPerformance(server.getBoolean("IsHighPerformance"))
                    .setHddType(server.getString("HDDType"))
                    .setLinkedNetworks(networkList));
                Log.d("API", "Received server - " + server.getString("Name"));
            }
        } catch (JSONException e) {
            Log.e("API", "Could not understand JSON for /servers");
            throw new RuntimeException(e);
        }
        ApiCache.serversList = serverList;
        return serverList;
    }
}
