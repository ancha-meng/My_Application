package com.example.myapplication.database;

import java.util.ArrayList;

public class Point {
    private String point_name;
    private String type;
    private String x;
    private String y;
    private String temperature;
    private String loudian_temperature;
    private String diqiu_temperature;
    private String shiqiu_temperature;
    private String xiangdui_wetness;
    private String CHILL;
    private String HEAT_INDEX;
    private String T_limit;
    private String pressure;
    private String altitude;
    private String density;
    private String air_TVOC;
    private String air_C6H6;
    private String air_HCHO;
    private String air_CO2;
    private String air_PM25;
    private String air_PM10;
    private String light;
    private String wind;
    private String hot;
    private String body;
    private String[] score = new String[7];
    private String[] factor = new String[7];
    private ArrayList<byte[]> image_data = new ArrayList<>();
    private ArrayList<String> image_name = new ArrayList<>();
    private ArrayList image_size = new ArrayList();
    private ArrayList<byte[]> audio_data = new ArrayList<>();
    private ArrayList<String> audio_name = new ArrayList<>();
    public Point() {}
    public void setname(String name) {
        this.point_name = name;
    }
    public void set_type(String type) {
        this.type = type;
    }
    public void set_xy(String x, String y) {
        this.x = x;
        this.y = y;
    }
    public void add_image(byte[] image_data, String image_name, int image_size){
        this.image_data.add(image_data);
        this.image_name.add(image_name);
        this.image_size.add(image_size);
    }
    public void add_audio(byte[] audio_data, String audio_name){
        this.audio_data.add(audio_data);
        this.audio_name.add(audio_name);
    }
    public void del_audio(){
        this.audio_data.clear();
        this.audio_name.clear();
    }
    public void clear_image(){
        this.image_data.clear();
        this.image_name.clear();
        this.image_size.clear();
    }
    public void set_value(String CHILL,String HEAT_INDEX,String shiqiu_temp,String T_limit,String diqiu_temp,String temp,String xiangdui_wet,String loudian_temp,
                          String pressure,String altitude,String density,
                          String air_TVOC,String air_C6H6,String air_HCHO,String air_CO2,String air_PM25,String air_PM10,
                          String light,String wind){
        this.CHILL=CHILL;
        this.HEAT_INDEX=HEAT_INDEX;
        this.shiqiu_temperature=shiqiu_temp;
        this.T_limit=T_limit;
        this.diqiu_temperature=diqiu_temp;
        this.temperature=temp;
        this.xiangdui_wetness=xiangdui_wet;
        this.loudian_temperature=loudian_temp;
        this.pressure=pressure;
        this.altitude=altitude;
        this.density=density;
        this.air_TVOC=air_TVOC;
        this.air_HCHO=air_HCHO;
        this.air_C6H6=air_C6H6;
        this.air_CO2=air_CO2;
        this.air_PM25=air_PM25;
        this.air_PM10=air_PM10;
        this.light=light;
        this.wind=wind;
    }
    public void set_score(String beauty,String safe,String clean,String alive,String depress,String boring,String rich){
        this.score[0]=beauty;
        this.score[1]=safe;
        this.score[2]=clean;
        this.score[3]=alive;
        this.score[4]=depress;
        this.score[5]=boring;
        this.score[6]=rich;
    }
    public void set_factor(String beauty,String safe,String clean,String alive,String depress,String boring,String rich){
        this.factor[0]=beauty;
        this.factor[1]=safe;
        this.factor[2]=clean;
        this.factor[3]=alive;
        this.factor[4]=depress;
        this.factor[5]=boring;
        this.factor[6]=rich;
    }
    public void set_hotbody(String hot,String body){
        this.hot=hot;
        this.body=body;
    }
    public String getPoint_name() {
        return point_name;
    }
    public String getType(){return type;}
    public String get_X(){return x;}
    public String get_Y(){return y;}
    public ArrayList<byte[]> getImage_data(){return image_data;}
    public ArrayList<String> getImage_name(){return image_name;}
    public ArrayList getImage_size(){return  image_size;}
    public ArrayList<byte[]> getAudio_data(){return audio_data;}
    public ArrayList<String> getAudio_name(){return audio_name;}
    public String[] getScore(){return score;}
    public String[] getFactor(){return factor;}
    public String getCHILL(){return CHILL;}
    public String getHEAT_INDEX() {return HEAT_INDEX;}
    public String getT_limit() {return T_limit;}
    public String getTemperature(){return temperature;}
    public String getLoudian_temperature() {return loudian_temperature;}
    public String getDiqiu_temperature() {return diqiu_temperature;}
    public String getShiqiu_temperature() {return shiqiu_temperature;}
    public String getXiangdui_wetness() {return xiangdui_wetness;}
    public String getPressure() {return pressure;}
    public String getAir_TVOC() {return air_TVOC;}
    public String getAir_C6H6() {return air_C6H6;}
    public String getAir_CO2() {return air_CO2;}
    public String getAir_HCHO() {return air_HCHO;}
    public String getAir_PM10() {return air_PM10;}
    public String getAir_PM25() {return air_PM25;}
    public String getLight() {return light;}
    public String getWind() {return wind;}
    public String getHot(){return hot;}
    public String getBody() {return body;}
    public String getAltitude() {return altitude;}
    public String getDensity() {return density;}
}
