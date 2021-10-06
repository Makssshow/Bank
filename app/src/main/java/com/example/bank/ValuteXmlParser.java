package com.example.bank;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.StringReader;

public class ValuteXmlParser {

    private ArrayList<Valute> valutes;

    public ValuteXmlParser(){
        valutes = new ArrayList<>();
    }

    public ArrayList<Valute> getValute(){
        return valutes;
    }

    public int getCount() {return valutes.size();}

    public boolean parse(String xmlData){
        boolean status = true;
        Valute currentValute = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            StringReader a = new StringReader(xmlData);

            xpp.setInput(a);
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("Valute".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentValute = new Valute();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("Valute".equalsIgnoreCase(tagName)){
                                valutes.add(currentValute);
                                inEntry = false;
                            } else if("CharCode".equalsIgnoreCase(tagName)){
                                currentValute.setCharCode(textValue);
                            } else if("Value".equalsIgnoreCase(tagName)){
                                currentValute.setValue(textValue);
                            } else  if("NumCode".equalsIgnoreCase(tagName)){
                                currentValute.setNumCode(textValue);
                            } else  if("Nominal".equalsIgnoreCase(tagName)){
                                currentValute.setNominal(textValue);
                            } else  if("Name".equalsIgnoreCase(tagName)){
                                currentValute.setName(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}