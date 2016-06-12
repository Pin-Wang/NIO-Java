package demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class NIOwriteFile {
	public static void main(String[] args) throws IOException {
		FileChannel fc=null;
		RandomAccessFile file=null;
		try {
			file=new RandomAccessFile("C://Users//Administrator//Desktop//nio.txt","rw");
			fc=file.getChannel();
			ByteBuffer bf=ByteBuffer.allocate(1024);
			int i=0;
			//定义字符集
			//Charset cs=Charset.forName("utf8");
			while(i!=100){
				bf.clear();
				bf.put(("info"+i+++"换行\n").getBytes());
				bf.flip();
				while(bf.hasRemaining()){
					fc.write(bf);
				}
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
