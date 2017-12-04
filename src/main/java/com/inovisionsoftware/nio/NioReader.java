package com.inovisionsoftware.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class NioReader {

	private SocketChannel client;
	
	NioReader() throws Exception {
		SocketAddress server = new InetSocketAddress("localhost", 1919);
		client = SocketChannel.open(server);
		client.configureBlocking(false);
	}
	
	public void readBlocking() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(74);
		WritableByteChannel output = Channels.newChannel(System.out);
		while(client.read(buffer) != -1) {
			buffer.flip();
			output.write(buffer);
			buffer.clear();
		}
	}
	
	public void readNonBlocking() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(74);
		WritableByteChannel output = Channels.newChannel(System.out);
		while(true) {
			int n = client.read(buffer);
			if(n > 0) {
				buffer.flip();
				output.write(buffer);
				buffer.clear();				
			} else {
				break;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
