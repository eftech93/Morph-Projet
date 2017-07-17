package com.tunaprojects.morph.Controller.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.tunaprojects.morph.Controller.Dialog.Dialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Esteban Puello on 5/08/2016.
 * This class handle the files created, read and write
 */
public class FileHandler {

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * This function reads a file
     * @param context Activity
     * @param filename file name that will be read
     * @return ArraList of string of each line
     */
    public static ArrayList<String> readFromFile(Context context, String filename) {
        ArrayList<String> result = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null) {
                    //result.add(new String(decipher(stringToBytesUTFCustom(receiveString))));
                    result.add(receiveString);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            System.out.println("Error in file handler");
        }
        return result;
    }

    private static final String KEY = "Ix2pH3ASxMeP9tKmNWkP4(/Gm4&CkU4K";

    /**
     * No implemented yet
     * @param str
     * @return
     */
    private static byte[] cipher(String str) {
        try {
            Key aesKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            Log.e("Cipher error", e.getMessage());
            return null;
        }
    }

    /**
     * Not implemented yet
     * @param str
     * @return
     */
    private static byte[] decipher(byte[] str) {
        try {
            Key aesKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return cipher.doFinal(str);
        } catch (Exception e) {
            Log.e("Decipher error", e.getMessage());
            return null;
        }
    }

    /**
     * Not implemented yet
     * @param str
     * @return
     */
    private static byte[] stringToBytesUTFCustom(String str) {
        char[] buffer = str.toCharArray();
        byte[] b = new byte[buffer.length << 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            b[bpos] = (byte) ((buffer[i] & 0xFF00) >> 8);
            b[bpos + 1] = (byte) (buffer[i] & 0x00FF);
        }
        return b;
    }

    /**
     * Not implemented yet
     * @param bytes
     * @return
     */
    private static String bytesToStringUTFCustom(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));
            buffer[i] = c;
        }
        return new String(buffer);
    }

    /**
     * This functions saves an ArrayList of string into a file
     * @param context Activity
     * @param filename File name
     * @param lines ArrayList of strings with the lines to be stored
     * @return bool whether or not it was saved
     */
    public static boolean saveFile(Context context, String filename, ArrayList<String> lines) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            for (String line : lines) {
                //bw.write(bytesToStringUTFCustom(cipher(line)));
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This function saves a string into a file
     * @param context Activity
     * @param filename file name
     * @param doc string to be written
     * @return bool whether or not it was saved
     */
    public static boolean writeFile(Context context, String filename, String doc) {
        try {
            try {
                File file = new File(context.getFilesDir(), filename);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(doc.getBytes());
                outputStream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This function creates a input stream from a file
     * @param context Activity
     * @param filename filename
     * @return can be a null whether could not create the inputstream object
     */
    public static InputStream getFile(Context context, String filename) {
        try {
            File k = context.getFileStreamPath(filename);
            return new FileInputStream(k);
        } catch (Exception e) {
            //Dialog.showAlert(context, "Archivo no encontrado " + e.getLocalizedMessage());
            return null;
        }
    }

}
