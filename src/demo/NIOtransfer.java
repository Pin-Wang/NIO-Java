package demo;

import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class NIOtransfer {
	public static void main(String[] args) throws IOException {
		FileChannel fcFrom=null;
		FileChannel fcTo=null;
		RandomAccessFile fileFrom=null;
		RandomAccessFile fileTo=null;
		try {
			fileFrom=new RandomAccessFile("C://Users//Administrator//Desktop//use NERDTree.txt","rw");
			fcFrom=fileFrom.getChannel();
			fileTo=new RandomAccessFile("C://Users//Administrator//Desktop//use NERDTreeTo.txt","rw");
			fcTo=fileTo.getChannel();
			fcTo.transferFrom(fcFrom, 0,fileFrom.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(fileFrom!=null){
				fileFrom.close();
			}
			if(fcFrom!=null){
				fcFrom.close();
			}
			if(fileTo!=null){
				fileFrom.close();
			}
			if(fcTo!=null){
				fcFrom.close();
			}
		}
	}
}
