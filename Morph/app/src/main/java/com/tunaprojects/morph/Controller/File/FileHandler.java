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

    private static String bytesToStringUTFCustom(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));
            buffer[i] = c;
        }
        return new String(buffer);
    }

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

    public static void writeFile(Context context, String name, String doc) {
        try {
            try {
                File file = new File(context.getFilesDir(), name);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(doc.getBytes());
                outputStream.close();
            } catch (Exception e) {
         //       Dialog.showAlert(context, "No se pudo escribir en el archivo");
                return;
            }
        } catch (Exception e) {
       //     Dialog.showAlert(context, "No se pudo crear el archivo");
            return;
        }
     //   Dialog.showAlert(context, "Archivo creado correctamente");
    }

    public static InputStream getFile(Context context, String name) {
        try {
            File k = context.getFileStreamPath(name);
            return new FileInputStream(k);
        } catch (Exception e) {
            Dialog.showAlert(context, "Archivo no encontrado " + e.getLocalizedMessage());
            return null;
        }
    }

}
