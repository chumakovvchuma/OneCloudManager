package io.github.wapmorgan.onecloudmanager.api;

import java.util.List;
import java.util.Set;

/**
 * Created by wm on 30.05.2015.
 */
public class Server {
    public Server setId(int id) {
        Id = id;
        return this;
    }

    public Server setName(String name) {
        Name = name;
        return this;
    }

    public Server setState(String state) {
        State = state;
        return this;
    }

    public Server setIsPowerOn(boolean isPowerOn) {
        IsPowerOn = isPowerOn;
        return this;
    }

    public Server setCpu(int cpu) {
        Cpu = cpu;
        return this;
    }

    public Server setRam(int ram) {
        Ram = ram;
        return this;
    }

    public Server setHdd(int hdd) {
        Hdd = hdd;
        return this;
    }

    public Server setIp(String ip) {
        Ip = ip;
        return this;
    }

    public Server setAdminUserName(String adminUserName) {
        AdminUserName = adminUserName;
        return this;
    }

    public Server setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
        return this;
    }

    public Server setImage(String image) {
        Image = image;
        return this;
    }

    public Server setIsHighPerformance(boolean isHighPerformance) {
        IsHighPerformance = isHighPerformance;
        return this;
    }

    public Server setHddType(String hddType) {
        HddType = hddType;
        return this;
    }

    public Server setLinkedNetworks(List<Network> linkedNetworks) {
        LinkedNetworks = linkedNetworks;
        return this;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public boolean isPowerOn() {
        return IsPowerOn;
    }

    public int getCpu() {
        return Cpu;
    }

    public int getRam() {
        return Ram;
    }

    public int getHdd() {
        return Hdd;
    }

    public String getIp() {
        return Ip;
    }

    public String getAdminUserName() {
        return AdminUserName;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public String getImage() {
        return Image;
    }

    public boolean isHighPerformance() {
        return IsHighPerformance;
    }

    public String getHddType() {
        return HddType;
    }

    public List<Network> getLinkedNetworks() {
        return LinkedNetworks;
    }

    private int Id;
    private String Name, State;
    private boolean IsPowerOn;
    private int Cpu, Ram, Hdd;
    private String Ip, AdminUserName, AdminPassword, Image;
    private boolean IsHighPerformance;
    private String HddType;
    private List<Network> LinkedNetworks;
}
