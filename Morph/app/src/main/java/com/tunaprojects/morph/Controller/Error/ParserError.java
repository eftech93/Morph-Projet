package com.tunaprojects.morph.Controller.Error;

/**
 * Created by Esteban Puello on 25/11/2016.
 */
public class ParserError extends Throwable{
    public ParserError(String type){
        super(type);
    }
}
