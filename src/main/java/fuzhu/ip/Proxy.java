package fuzhu.ip;

/*******************************************************************************
 * Copyright (c) 2009 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

class Proxy{
	private String ip;
	private int port;
	private long delay;
	private InetSocketAddress address;
	private String geodata = "/res/geo/GeoIP.dat";
	private String geocity = "/res/geo/GeoLiteCity.dat";
	private int timeout = 3000; //ms
	private String anonChecker = "http://www.stilllistener.com/checkpoint1/test2/";
	
	Proxy (String ip,int port) throws UnknownHostException{
		this.ip = ip;
		this.port = port;
		address  = new InetSocketAddress(ip,port);
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public int getPort(){
		return this.port;
	}
	
	void printReachableIP(InetAddress remoteAddr, int port) {
		String retIP = null;

		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> localAddrs = ni.getInetAddresses();
				while (localAddrs.hasMoreElements()) {
					InetAddress localAddr = localAddrs.nextElement();
					if (isReachable(localAddr, remoteAddr, port, 5000)) {
						retIP = localAddr.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Error occurred while listing all the local network addresses.");
		}
		if (retIP == null) {
			System.out.println("NULL reachable local IP is found!");
		} else {
			System.out.println("Reachable local IP is found, it is " + retIP);
		}
	}
	
	boolean isReachable(InetAddress localInetAddr, InetAddress remoteInetAddr, int port, int timeout) {

		boolean isReachable = false;
		Socket socket = null;
		try {
			socket = new Socket();

			// 端口号设置为 0 表示在本地挑选一个可用端口进行连接
			SocketAddress localSocketAddr = new InetSocketAddress(localInetAddr, 0);
			socket.bind(localSocketAddr);
			InetSocketAddress endpointSocketAddr = new InetSocketAddress(remoteInetAddr, port);
			socket.connect(endpointSocketAddr, timeout);
			System.out.println("SUCCESS - connection established! Local: " + localInetAddr.getHostAddress()
					+ " remote: " + remoteInetAddr.getHostAddress() + " port" + port);
			isReachable = true;
		} catch (IOException e) {
			System.out.println("FAILRE - CAN not connect! Local: " + localInetAddr.getHostAddress() + " remote: "
					+ remoteInetAddr.getHostAddress() + " port" + port);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Error occurred while closing socket..");
				}
			}
		}
		return isReachable;
	}

	public boolean isAlive() throws IOException {
		boolean status = false;
		String retIP = null;

		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> localAddrs = ni.getInetAddresses();
				while (localAddrs.hasMoreElements()) {
					InetAddress localAddr = localAddrs.nextElement();
					long t0 = System.currentTimeMillis();
					status = isReachable(localAddr, address.getAddress(), port, timeout);
					if (status) {
						long t1 = System.currentTimeMillis();
						delay = t1-t0;
						retIP = localAddr.getHostAddress();						
						return status;
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Error occurred while listing all the local network addresses.");
		}
		if (retIP == null) {
			System.out.println("NULL reachable local IP is found!");
		} else {
			System.out.println("Reachable local IP is found, it is " + retIP);
		}
		
		return status;

	}
	
	public double getDelay() {
		return (double) delay/1000.0;
	}
	
	public String getHostName() {
		return address.getHostName();
		
	}
	

	public String getCountry() throws IOException {
		LookupService cl = new LookupService(geodata);
		String countrycode = cl.getCountry(ip).getCode();
		
		cl.close();
		
		return countrycode;
	}
	
	public String getCity() throws IOException {
		LookupService cl = new LookupService(geocity,LookupService.GEOIP_MEMORY_CACHE);
		Location l1 = cl.getLocation(ip);
		String city = l1.city;
		cl.close();
		return city;
	
	}
	
	
	public String getAnonLevel() throws IOException {

		String stuff = new WebUrl(anonChecker,ip,port).getResponseStr();
		if (stuff.equals("")) return "Time Out";
		
		String[] strs = stuff.split(">|<");
		for(int i=0;i<strs.length;i++){
			String str = strs[i];
			if(str.indexOf("AnonyLevel ")>=0){
				return strs[i+2];
			}
		}
		
		return "0";
	}
	

	
	
}
