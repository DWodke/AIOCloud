package de.aiocloud.worker.objects;

import java.util.HashMap;

public class ProxyManager {

    private HashMap<String, Proxy> availableproxies;

    /**
     * This is the initial method of the 'ProxyManager' object.
     */
    public ProxyManager() {
        this.availableproxies = new HashMap<String, Proxy>();
    }
    /**
     * Add proxy to the map.
     * @param proxy
     */
    public void addProxy(Proxy proxy) {
        if(proxy != null) {
            this.availableproxies.put(proxy.getName(), proxy);
        }
    }
    /**
     * Remove proxy from the map.
     * @param proxyname
     */
    public void removeProxy(String proxyname) {
        if(this.availableproxies.containsKey(proxyname)) {
            this.availableproxies.remove(proxyname);
        }
    }
    /**
     * GET proxy from map.
     * @param proxyname is the name of the proxy.
     * @return the proxy-object.
     */
    public Proxy getProxy(String proxyname) {
        return this.availableproxies.get(proxyname);
    }
}
