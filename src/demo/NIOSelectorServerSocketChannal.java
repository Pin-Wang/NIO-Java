package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.logging.Handler;

public class NIOSelectorServerSocketChannal {
	private static Selector selector=null;
	private static ServerSocketChannel ssc=null;
	private static ByteBuffer bf=ByteBuffer.allocate(1024);
	private static Charset cs=Charset.forName("utf8");
	
	public static void main(String[] args) throws Exception{
		select();
	}
	
	private static void select() throws Exception{
		ssc=ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(80));
		selector=Selector.open();
		//将ServerSocketChannel设置成非阻塞
		ssc.configureBlocking(false);
		//向selector注册ServerSocketChannel，对接受就绪感兴趣。
		ssc.register(selector,SelectionKey.OP_ACCEPT);
		
		while(true){
			int count=selector.select(4000);
			if(count==0){
				System.out.println("==");
				continue;
			}
			System.out.println("被选择的数目："+count);
			//从selector中得到注册到的SelectionKey
			Iterator<SelectionKey> iter=selector.selectedKeys().iterator();
			//遍历Iterator
			while(iter.hasNext()){
				SelectionKey selectionKey=iter.next();
				//处理接受就绪的selectionKey
				if(selectionKey.isAcceptable()){
					System.out.println("有连接进来");
					handleAccept(selectionKey);
				}
				//处理连接就绪的selectionKey
				else if(selectionKey.isConnectable()){
					handleConnect(selectionKey);
				}
				//处理读取就绪的selectionKey
				else if(selectionKey.isReadable()){
					System.out.println("有可读的channel");
					handleRead(selectionKey);
				}
				//处理写就绪的selectionKey
				else if(selectionKey.isWritable()){
					System.out.println("有可写的channel");
					handleWrite(selectionKey);
				}
				iter.remove();
			}
		}
		
	}
	private static void handleAccept(SelectionKey selectionKey) throws Exception{
		ServerSocketChannel ssc=(ServerSocketChannel) selectionKey.channel();
		SocketChannel sc=ssc.accept();
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ,bf);
	}
	
	private static void handleConnect(SelectionKey selectionKey){}
	private static void handleRead(SelectionKey selectionKey) throws IOException{
		SocketChannel sc=(SocketChannel)selectionKey.channel();
		bf.clear();
		long readBytes=sc.read(bf);
		while(readBytes!=-1){
			//将ByteBuffer设置为读模式
			bf.flip();
			while(bf.hasRemaining()){
				System.out.println("Received: "+cs.decode(bf));
			}
			//将ByteBuffer设置为写模式
			bf.clear();
		    readBytes=sc.read(bf);
		}
		sc.close();
	}
	private static void handleWrite(SelectionKey selectionKey){}
	
}
