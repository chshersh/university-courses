public class Message {
    private String ip;
    private String mac;
    private String name;

    public Message(String ip, String mac, String name) {
        this.ip = ip;
        this.mac = mac;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }
}
