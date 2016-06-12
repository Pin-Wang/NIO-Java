package demo;

import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class NIOPipe {
	//定义一个线程池
	private static ExecutorService pool=null;
	//定义一个管道
	private static Pipe pipe=null;
	private static ByteBuffer bf=null;
	//定义字符集
	private static Charset cs=null;
	public static void main(String[] args) throws IOException {
		pool=Executors.newCachedThreadPool();
		pipe=Pipe.open();
		
		final SinkChannel sinkChannel=pipe.sink();
		final SourceChannel sourceChannel=pipe.source();
		bf=ByteBuffer.allocate(1024);
		cs=Charset.forName("utf8");
		pool.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				bf.clear();
				bf.put(("I am sourse data").getBytes());
				bf.flip();
				sinkChannel.write(bf);
				return null;
			}
		});
		pool.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				bf.clear();
				sourceChannel.read(bf);
				bf.flip();
				System.out.println("read data from other thread is:"+cs.decode(bf));
				return null;
			}
		});
	}
}
