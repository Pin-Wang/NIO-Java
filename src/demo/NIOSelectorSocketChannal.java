package demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class NIOSelectorSocketChannal {
	public static void main(String[] args) throws Exception {
		SocketChannel sc=SocketChannel.open();
		ByteBuffer bf=ByteBuffer.allocate(1024);
		sc.connect((new InetSocketAddress("localhost",80)));
		
		//将ServerSocketChannel设置成非阻塞
		sc.configureBlocking(false);
		while(true){
			if(!sc.finishConnect()){
				continue;
			}
			for(int i=0;i<10;i++){
				TimeUnit.SECONDS.sleep(1);
				bf.clear();
				bf.put(("info"+i).getBytes());
				bf.flip();
				while(bf.hasRemaining()){
					sc.write(bf);
				}
			}
			break;
		}
		sc.close();
		
	}
}
