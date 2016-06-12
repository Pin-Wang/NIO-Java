package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NIOServerSocketChannal {
	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc=ServerSocketChannel.open();
		ByteBuffer bf=ByteBuffer.allocate(1024);
		ssc.socket().bind(new InetSocketAddress(80));
		
		//将ServerSocketChannel设置成非阻塞
		ssc.configureBlocking(false);
		
		Charset cs=Charset.forName("utf8");
		while(true){
			SocketChannel sc=ssc.accept();
			if(sc==null){
				continue;
			}
			bf.clear();
			long readBytes=sc.read(bf);
			
			while(readBytes!=-1){
				//将ByteBuffer设置为读模式
				bf.flip();
				while(bf.hasRemaining()){
					System.out.print("Received: "+cs.decode(bf));
				}
				//将ByteBuffer设置为写模式
				bf.clear();
			    readBytes=sc.read(bf);
			}
		}
	}
}
