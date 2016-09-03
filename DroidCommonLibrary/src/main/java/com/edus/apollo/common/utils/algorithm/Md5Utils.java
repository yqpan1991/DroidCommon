package com.edus.apollo.common.utils.algorithm;

import android.text.TextUtils;

import com.edus.apollo.common.utils.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by panyongqiang on 2016/7/6.
 */
public class Md5Utils {
    private static final int HEADER_LENGTH = 1024;
    private static final int FOOTER_LENGTH = 1024;


    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String getStringMD5(String strObj) {
        String resultString = null;
        if(strObj != null){
            try {
                resultString = new String(strObj);
                MessageDigest md = MessageDigest.getInstance("MD5");
                // md.digest() 该函数返回值为存放哈希值结果的byte数组
                resultString = byteToString(md.digest(strObj.getBytes()));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }

        return resultString;
    }

    public static String getFileMd5(String filePath) {
        return getFileMd5(new File(filePath));
    }

    public static String getFileMd5(File file) {
        if(file != null && file.exists()){
            return getFileHeaderMd5(file, file.length());
        }
        return null;
    }

    public static String getFileHeaderMd5(String filePath, long headerLength) {
        return getFileHeaderMd5(new File(filePath), headerLength);

    }

    public static String getFileHeaderMd5(File file, long headerLength) {
        if(file != null && file.exists() && headerLength > 0){
            long calculateLength = headerLength > file.length() ? file.length() : headerLength;
            String result = null;
            MessageDigest md;
            BufferedInputStream bis = null;
            try {
                md = MessageDigest.getInstance("MD5");
                bis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024*8];
                int readStepLength = 0;
                long leftLength = calculateLength;
                int stepLength = (int) Math.min(leftLength, buffer.length);
                while(leftLength > 0 && (readStepLength = bis.read(buffer, 0, stepLength)) != -1){
                    md.update(buffer, 0, readStepLength);
                    leftLength -= readStepLength;
                }
                result = byteToString(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                IOUtils.closeSilently(bis);
            }
            return result;
        }
        return null;
    }

    public static String getFileFooterMd5(File file , long footerLength){
        if(file != null && file.exists() && footerLength > 0){
            long calculateLength = footerLength > file.length() ? file.length() : footerLength;
            long skipLength = file.length() - calculateLength;
            String result = null;
            MessageDigest md;
            BufferedInputStream bis = null;
            try {
                md = MessageDigest.getInstance("MD5");
                bis = new BufferedInputStream(new FileInputStream(file));
                bis.skip(skipLength);
                byte[] buffer = new byte[1024*8];
                int readStepLength = 0;
                long leftLength = calculateLength;
                int stepLength = (int) Math.min(leftLength, buffer.length);
                while(leftLength > 0 && (readStepLength = bis.read(buffer, 0, stepLength)) != -1){
                    md.update(buffer, 0, readStepLength);
                    leftLength -= readStepLength;
                }
                result = byteToString(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                IOUtils.closeSilently(bis);
            }
            return result;


        }
        return null;
    }
}
