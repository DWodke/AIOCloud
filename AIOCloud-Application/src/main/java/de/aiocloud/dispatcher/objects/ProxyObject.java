package de.aiocloud.dispatcher.objects;

public class ProxyObject {

    private String name;
    private String address;
    private Integer dispatcherport;
    private Integer serverport;
    private ProxyGroupObject proxygroup;

    /**
     * This is the initial method of the 'ProxyObject'.
     */
    public ProxyObject(String name, String address, Integer dispatcherport, Integer serverport, ProxyGroupObject proxygroup) {
        this.name = name;
        this.address = address;
        this.dispatcherport = dispatcherport;
        this.proxygroup = proxygroup;
        this.serverport = serverport;
    }
}
