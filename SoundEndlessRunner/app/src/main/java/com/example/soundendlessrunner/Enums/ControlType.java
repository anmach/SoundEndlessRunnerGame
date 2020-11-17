package com.example.soundendlessrunner.Enums;

public enum ControlType {
    SWIPES("Swipes", 0),
    TAPS("Tap on screen sides", 1),
    SENSOR("Sensor", 2),
    SPEECH("Speech recognition", 3);

    String msg;
    int value;

    private ControlType(String msg, int value){
        this.msg = msg;
        this.value = value;
    }

    public String getMsg(){
        return msg;
    }

    public int getValue(){
        return value;
    }

    public static ControlType findByValue(int val){
        for(ControlType v : values()){
            if( v.getValue() == val){
                return v;
            }
        }
        return null;
    }

    public static int getMaxValue(){
        return 3;
    }

    public static int getMinValue(){
        return 0;
    }
}