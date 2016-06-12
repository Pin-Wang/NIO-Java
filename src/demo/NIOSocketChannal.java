package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOSocketChannal {
	public static void main(String[] args) throws Exception {
		SocketChannel sc=SocketChannel.open();
		ByteBuffer bf=ByteBuffer.allocate(1024);
		sc.connect((new InetSocketAddress("localhost",80)));
		
		//将ServerSocketChannel设置成非阻塞
		//sc.configureBlocking(false);
		for(int i=0;i<10;i++){
			bf.clear();
			bf.put(("info"+i).getBytes());
			bf.flip();
			while(bf.hasRemaining()){
				sc.write(bf);
			}
		}
		sc.close();
		
	}
}
