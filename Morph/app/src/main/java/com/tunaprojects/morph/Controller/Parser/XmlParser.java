package com.tunaprojects.morph.Controller.Parser;

import android.content.Context;
import android.support.annotation.NonNull;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.PropertyValue;
import com.tunaprojects.morph.Model.Instance.TObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 25/11/2016.
 * This class parse the XML into an ArrayList of TOject
 */
public class XmlParser {

    private ArrayList<String> containers;
    private ArrayList<String> elements;
    private final ArrayList<String> components = new ArrayList<String>() {{add("content"); add("navbar");add("leftbartop");add("leftbarbottom");}};
    public XmlParser(ArrayList<String> c, ArrayList<String> e) {
        this.containers = c;
        this.elements = e;
    }

    public ArrayList<TObject>[] docParser(Context c, InputStream is) {
        XmlPullParserFactory xppf;
        try {
            xppf = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = xppf.newPullParser();
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xpp.setInput(is, null);
            return parseXML(c, xpp);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<TObject>[] docParser(Context c, String s) {
        XmlPullParserFactory xppf;
        try {
            xppf = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = xppf.newPullParser();
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            xpp.setInput(is, null);
            return parseXML(c, xpp);
        } catch (Exception e) {
            return null;
        }
    }

    private ArrayList<TObject>[] parseXML(Context c, XmlPullParser xpp) throws XmlPullParserException, IOException {
        ArrayList<TObject> alvcContent = new ArrayList<>();
        ArrayList<TObject> alvcActionBar = new ArrayList<>();
        ArrayList<TObject> alvcLeftBarTop = new ArrayList<>();
        ArrayList<TObject> alvcLeftBarBottom = new ArrayList<>();
        int eventType = xpp.getEventType();
        String startTag = null;
        try {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        startTag = parserXMLXPPStartTag(xpp, alvcContent, alvcActionBar, alvcLeftBarTop, alvcLeftBarBottom, startTag);
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Log.e("alvcContent size", alvcContent.size() + "");
        Log.e("alvcActionBar size", alvcActionBar.size() + "");
        Log.e("alvcLeftBarTop size", alvcLeftBarTop.size() + "");
        Log.e("alvcLeftBarBottom size", alvcLeftBarBottom.size() + "");*/
        return new ArrayList[]{alvcContent, alvcActionBar, alvcLeftBarTop, alvcLeftBarBottom};
    }

    @NonNull
    private String parserXMLXPPStartTag(XmlPullParser xpp, ArrayList<TObject> alvcContent, ArrayList<TObject> alvcActionBar, ArrayList<TObject> alvcLeftBarTop, ArrayList<TObject> alvcLeftBarBottom, String startTag) throws XmlPullParserException, IOException {
        String name;
        name = xpp.getName();
        if(components.contains(name)){
            startTag = name;
            xpp.next();
        }
        /**if (name.contentEquals("content")) {
            startTag = name;
            xpp.next();
        } else if (name.contentEquals("navbar")) {
            startTag = name;
            xpp.next();
        } else if (name.contentEquals("leftbartop")) {
            startTag = name;
            xpp.next();
        } else if (name.contentEquals("leftbarbottom")) {
            startTag = name;
            xpp.next();
        }
         */
        if (xpp.getName() == null) {
            return startTag;
        }
        xmlParserStartTagAddElement(xpp, alvcContent, alvcActionBar, alvcLeftBarTop, alvcLeftBarBottom, startTag);
        return startTag;
    }

    private void xmlParserStartTagAddElement(XmlPullParser xpp, ArrayList<TObject> alvcContent, ArrayList<TObject> alvcActionBar, ArrayList<TObject> alvcLeftBarTop, ArrayList<TObject> alvcLeftBarBottom, String startTag) throws XmlPullParserException, IOException {
        if (startTag.contentEquals("content")) {
            alvcContent.add(getElement(xpp, xpp.getName()));
        } else if (startTag.contentEquals("navbar")) {
            alvcActionBar.add(getElement(xpp, xpp.getName()));
        } else if (startTag.contentEquals("leftbartop")) {
            alvcLeftBarTop.add(getElement(xpp, xpp.getName()));
        } else if (startTag.contentEquals("leftbarbottom")) {
            alvcLeftBarBottom.add(getElement(xpp, xpp.getName()));
        }
    }

    protected TObject getElement(XmlPullParser xpp, String tag) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        TObject vc = null;
        ArrayList<Property> alp = new ArrayList<>();
        ArrayList<TObject> alvc = new ArrayList<>();
        xpp.next();
        while (xpp.getName() == null) {
            xpp.next();
        }
        while (eventType != XmlPullParser.END_TAG && !xpp.getName().contentEquals(tag)) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    getElementXPPStartTag(xpp, tag, alp, alvc);
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = xpp.next();
            while (xpp.getName() == null) {
                xpp.next();
            }
        }
        vc = new TObject(tag, alp, alvc);
        return vc;
    }

    private void getElementXPPStartTag(XmlPullParser xpp, String tag, ArrayList<Property> alp, ArrayList<TObject> alvc) throws XmlPullParserException, IOException {
        if (elements.contains(xpp.getName())) {
            if (containers.contains(tag)) {
                if (xpp.getName() == null) {
                    return;
                }
                TObject subView = getElement(xpp, xpp.getName());
                alvc.add(subView);
            }
        } else {
            Property p = new Property(xpp.getName(), new PropertyValue(xpp.nextText()));
            alp.add(p);
        }
    }
}


