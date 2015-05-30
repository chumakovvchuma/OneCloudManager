package io.github.wapmorgan.onecloudmanager.api;

/**
 * Created by Сергей on 30.05.2015.
 */
public class Network {
    public Network setId(int id) {
        Id = id;
        return this;
    }

    public Network setIp(String ip) {
        Ip = ip;
        return this;
    }

    public Network setMac(String mac) {
        Mac = mac;
        return this;
    }

    public int getId() {
        return Id;
    }

    public String getIp() {
        return Ip;
    }

    public String getMac() {
        return Mac;
    }

    private int Id;
    private String Ip, Mac;
}
