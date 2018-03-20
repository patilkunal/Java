package com.inovisionsoftware.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {

	private int port;
	private ServerSocketChannel serverChannel;
	
	public NioServer(int port) throws Exception {
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		this.port = port;
	}
	
	public void start() throws Exception {
		ServerSocket ss = serverChannel.socket();
		ss.bind(new InetSocketAddress(port));
		SocketChannel clientChannel = serverChannel.accept();
		if(clientChannel != null) {
			clientChannel.configureBlocking(false);
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
