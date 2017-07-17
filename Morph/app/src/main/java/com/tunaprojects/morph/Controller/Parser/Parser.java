package com.tunaprojects.morph.Controller.Parser;

import android.content.Context;
import android.graphics.Color;
import android.util.Xml;
import android.widget.ArrayAdapter;

import com.tunaprojects.morph.Controller.Error.ParserError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Esteban Puello on 25/11/2016.
 * This class allows users to do differents types of parsing
 * that are pretty difficult on lua
 */
public class Parser {

    /**
     * This function parse a string to color
     * @param color this string is the color (#FFFFFF or 0;0;0)
     * @return an integer, whether can't be parsed a -1 it's sent
     */
    public static int getColorFromString(String color) throws ParserError {
        /*
           For verifying weather it's #FFFFFF or 0;0;0 it's better to use regex
         */
        String[] colors = color.split(";");
        if (colors.length == 3) {
            int r = parseIntegerFromString(colors[0]);
            int g = parseIntegerFromString(colors[1]);
            int b = parseIntegerFromString(colors[2]);
            try {
                return parseColorFromInteger(r, g, b, 1.0f);
            } catch (Exception e) {
                throw new ParserError("Color Parser Error");
            }
        } else if (colors.length == 1) {
            try {
                return Color.parseColor(color);
            } catch (Exception e) {
                throw new ParserError("Color Parser Error");
            }
        }
        return -1;
    }

    /**
     * This function parse 3 integers to a color
     * @param red red color from 0 - 254
     * @param green green color from 0 - 254
     * @param blue blue color from 0 - 254
     * @param prtage changing porcentage
     * @return an integer
     */
    public static int parseColorFromInteger(int red, int green, int blue, float prtage) {
        return Color.rgb((int) (red * prtage), (int) (green * prtage), (int) (blue * prtage));
    }

    /**
     * This function parse a boolean from string
     * @param text
     * @return Returns a boolean
     * @throws ParserError this error is thrown when it was impossible to parse
     */
    public static boolean parseBooleanFromString(String text) throws ParserError {
        try {
            return Boolean.parseBoolean(text);
        } catch (Exception e) {
            throw new ParserError("Boolean Parser Error");
        }
    }

    /**
     * This function parse an integer from string
     *
     * @param text
     * @return Returns an integer
     * @throws ParserError this error is thrown when it was impossible to parse
     */
    public static int parseIntegerFromString(String text) throws ParserError {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            throw new ParserError("Integer Parser Error");
        }
    }

    /**
     * This fonction parse an ArrayList into Stirng[]
     * @param al ArrayList
     * @return
     */
    public static String[] parserArrayListToArrayString(ArrayList al) {
        String[] re = Arrays.copyOf(al.toArray(), al.size(), String[].class);
        return re;
    }

    /**
     * This function created an ArrayAdapter for a dropdown item
     * @param context Activity
     * @param als ArrayList of Strings to be added on the adapter
     * @return ArraAdapter with the items of the arraylist
     */
    public static ArrayAdapter<String> getArrayAdapter(Context context, ArrayList<String> als) {
        return new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, als.toArray(new String[]{}));
    }

    /**
     * ArrayList of objects into an ArrayList of String, this function uses the toString object function
     * @param objl ArrayList of object
     * @return whether an object is null, it will add a null reference to the list
     */
    public static ArrayList<String> parserArrayListObjectToArrayListString(ArrayList objl) {
        ArrayList<String> strings = new ArrayList();
        for (Object object : objl) {
            strings.add(object != null ? object.toString() : null);
        }
        return strings;
    }

    public static String objectToString(Object obj) {
        return obj.toString();
    }

    /**
     * Parse a string into a JSONArray
     * @param obj String object to be parsed
     * @return if the String can't be parsed, a null reference is returned
     */
    public static JSONArray stringToJSONArray(String obj) {
        try {
            return new JSONArray(obj);
        } catch (Exception t) {
            return null;
        }
    }

    /**
     * This function tries to get a JSONObject from a JSONArray using an index
     * @param jsonarray JSONArray
     * @param index
     * @return If the JSONObject can't be created, a null reference is sent
     */
    public static JSONObject getJSONObject(JSONArray jsonarray, int index) {
        try {
            return jsonarray.getJSONObject(index);
        } catch (Exception e) {
            return null;
        }

    }
}
