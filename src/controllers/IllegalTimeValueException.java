package controllers;

public class IllegalTimeValueException extends Exception{
    public IllegalTimeValueException(){
        super();
    }
    public IllegalTimeValueException(String s) {
        super(s);
    }
}
