package com.java.bufferd.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 带缓冲区的IO与不带缓冲区的IO比较
 * @version 0
 */public class FileOperator {

    /** buffer size in bytes */
    final static int BUFFER_SIZE = 100;

    /**
     * copy file using FileInputStream & FileOutputStream

     * @param src copy from
     * @param dest copy to

     * @return;
     */
    public static void copyWithFileStream(File src, File dest){
        FileInputStream input = null;
        FileOutputStream output = null;

        try {
            input = new FileInputStream(src);
            output = new FileOutputStream(dest);

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = input.read(buffer)) > 0){
                output.write(buffer, 0, copySize);
                output.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * copy file using BufferedInputStream & BufferedOutputStream

     * @param src copy from file
     * @param dest copy to file

     * @return;
     */
    public static void copyWithBufferedStream(File src, File dest){
        BufferedInputStream bufferedInput = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            bufferedInput = new BufferedInputStream(new FileInputStream(src));
            bufferedOutput = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = bufferedInput.read(buffer)) > 0){
                bufferedOutput.write(buffer, 0, copySize);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInput.close();
                bufferedOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //测试
    public static void main(String args[]){
        File src = new File("test.txt");
        File dest = new File("copyTest.txt");

        try {
            if (!dest.exists()){
                dest.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();

        FileOperator.copyWithFileStream(src, dest);

        long endTime = System.currentTimeMillis();
        System.out.println("Copy file using FileStream takes : " + (endTime - startTime) + " ms.");

        //test copy using BufferedStream startTime = System.currentTimeMillis();

        FileOperator.copyWithBufferedStream(src, dest);

        endTime = System.currentTimeMillis();
        System.out.println("Copy file using BufferedStream takes : " + (endTime - startTime) + " ms.");
    }
}
