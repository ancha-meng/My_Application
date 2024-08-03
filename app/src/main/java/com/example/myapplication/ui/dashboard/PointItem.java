package com.example.myapplication.ui.dashboard;

public class PointItem {

//    点的ID，点的经度与纬度，点的评分
//    点所在地区的天气，点区域拍摄的图片（这里存储文件中的地址），点区域的录音（这里同样存储地址），
//    点的名字（可为空的文本），点记录时的时间，
    private int id;
    private double latitude;
    private double longitude;
    private int rating;//评分的标准未知
    private String weather;//记录的气候文本（气候是否有枚举的范围？）
    private String imagePath;//图片的文件路径
    private String audioPath;//录音的文件路径
    private String name;//用户可选的标识性文本
    private long timestamp;

    // Constructor
    public PointItem(int id, double latitude, double longitude, int rating, String weather, String imagePath, String audioPath, String name, long timestamp) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.weather = weather;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
        this.name = name;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
