package demo;

import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class NIOreadFile {
	public static void main(String[] args) throws IOException {
		FileChannel fc=null;
		RandomAccessFile file=null;
		try {
			file=new RandomAccessFile("C://Users//Administrator//Desktop//use NERDTree.txt","rw");
			fc=file.getChannel();
			ByteBuffer bf=ByteBuffer.allocate(1024);
			int readBytes=fc.read(bf);
			
			//定义字符集
			Charset cs=Charset.forName("utf8");
			while(readBytes!=-1){
				//将ByteBuffer置为读模式
				bf.flip();
				while(bf.hasRemaining()){
					System.out.print(cs.decode(bf));
				}
				//（置为写模式）clear()将location置为缓冲刚刚开始的位置，但是内部数据并不清除，而是被遗忘掉
				bf.clear();
				readBytes=fc.read(bf);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(file!=null){
				file.close();
			}
			if(fc!=null){
				fc.close();
			}
		}
	}
}
