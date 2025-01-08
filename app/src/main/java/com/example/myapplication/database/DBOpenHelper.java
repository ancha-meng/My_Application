package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelper(Context context){
        super(context,"db_map",null,16);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS project(" +
                "project_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "project_name TEXT NOT NULL," +
                "user_name TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS point_feel(" +
                "feel_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT NOT NULL," +
                "project_name TEXT NOT NULL," +
                "point_name TEXT NOT NULL," +
                "type TEXT," +
                "x TEXT," +
                "y TEXT," +
                "hot TEXT," +
                "body TEXT," +
                "beauty TEXT," +
                "safety TEXT," +
                "clean TEXT," +
                "alive TEXT," +
                "depress TEXT," +
                "boring TEXT," +
                "rich TEXT," +
                "beauty_factor TEXT," +
                "safety_factor TEXT," +
                "clean_factor TEXT," +
                "alive_factor TEXT," +
                "depress_factor TEXT," +
                "boring_factor TEXT," +
                "rich_factor TEXT," +
                "image_name TEXT," +
                "audio_name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS point_value(" +
                "value_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT NOT NULL," +
                "project_name TEXT NOT NULL," +
                "point_name TEXT NOT NULL," +
                "type TEXT," +
                "x TEXT," +
                "y TEXT," +
                "CHILL TEXT," +
                "HEAT_INDEX TEXT," +
                "shiqiu_temperature TEXT," +
                "T_limit TEXT," +
                "diqiu_temperature TEXT," +
                "temperature TEXT," +
                "xiangdui_wetness TEXT," +
                "loudian_temperature TEXT," +
                "pressure TEXT," +
                "altitude TEXT," +
                "density TEXT," +
                "air_TVOC TEXT," +
                "air_HCHO TEXT," +
                "air_C6H6 TEXT," +
                "air_CO2 TEXT," +
                "air_PM25 TEXT," +
                "air_PM10 TEXT," +
                "daylight TEXT," +
                "wind TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS image(" +
                "image_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT NOT NULL," +
                "project_name TEXT NOT NULL," +
                "point_name TEXT NOT NULL," +
                "image_name TEXT," +
                "image_size INTEGER," +
                "image_data BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS audio(" +
                "audio_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT NOT NULL," +
                "project_name TEXT NOT NULL," +
                "point_name TEXT NOT NULL," +
                "audio_name TEXT," +
                "audio_data BLOB)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS project");
        db.execSQL("DROP TABLE IF EXISTS point_feel");
        db.execSQL("DROP TABLE IF EXISTS point_value");
        db.execSQL("DROP TABLE IF EXISTS image");
        db.execSQL("DROP TABLE IF EXISTS audio");
        onCreate(db);
    }

    public void add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public boolean isExistProject(String projectName, String userName) {
        boolean isExist = false;
        // 假设db是一个有效的SQLiteDatabase对象
        Cursor cursor = db.rawQuery(
                "SELECT EXISTS(SELECT 1 FROM project WHERE user_name = ? AND project_name = ?);",
                new String[]{userName, projectName}
        );
        if (cursor.moveToFirst()) {
            // EXISTS查询只返回一行一列，因此我们可以直接读取它
            isExist = cursor.getInt(0) > 0;
        }
        cursor.close();
        return isExist;
    }
    public void add_project(String project_name,String user_name){
        db.execSQL("INSERT INTO project (project_name,user_name) VALUES(?,?)",new Object[]{project_name,user_name});
    }
    public void add_points(String user_name,String project_name, ArrayList<Point> value_points, ArrayList<Point> feel_points){
        for(int i =0;i<value_points.size();i++){
            Point point = value_points.get(i);
            db.execSQL("INSERT INTO point_value (user_name,project_name,point_name,type,x,y," +
                    "CHILL,HEAT_INDEX,shiqiu_temperature,T_limit,diqiu_temperature,temperature,xiangdui_wetness,loudian_temperature," +
                    "pressure,altitude,density,air_TVOC,air_HCHO,air_C6H6,air_CO2,air_PM25,air_PM10,daylight,wind) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{user_name,project_name,point.getPoint_name(),
                    point.getType(),point.get_X(),point.get_Y(),point.getCHILL(),point.getHEAT_INDEX(),point.getShiqiu_temperature(),
                    point.getT_limit(),point.getDiqiu_temperature(), point.getTemperature(),point.getXiangdui_wetness(),point.getLoudian_temperature(),
                    point.getPressure(),point.getAltitude(),point.getDensity(), point.getAir_TVOC(),point.getAir_HCHO(),point.getAir_C6H6(),
                    point.getAir_CO2(),point.getAir_PM25(),point.getAir_PM10(), point.getLight(),point.getWind()});
        }
        for(int i =0;i<feel_points.size();i++){
            Point point = feel_points.get(i);
            String Image_names = "";
            String Audio_names = "";
            for(int x = 0;x<point.getImage_name().size();x++){
                Image_names = Image_names + point.getImage_name().get(x) + ",";
                db.execSQL("INSERT INTO image (user_name, project_name, point_name, image_name, image_size, image_data) " +
                        "VALUES(?,?,?,?,?,?)",new Object[]{user_name,project_name,point.getPoint_name(),
                        point.getImage_name().get(x),point.getImage_size().get(x),point.getImage_data().get(x)});
            }
            for(int y = 0;y<point.getAudio_name().size();y++){
                Audio_names = Audio_names + point.getAudio_name().get(y) + ",";
                db.execSQL("INSERT INTO audio (user_name, project_name, point_name, audio_name, audio_data) " +
                        "VALUES(?,?,?,?,?)",new Object[]{user_name,project_name,point.getPoint_name(),
                        point.getAudio_name().get(y),point.getAudio_data().get(y)});
            }
            db.execSQL("INSERT INTO point_feel (user_name,project_name,point_name,type,x,y," +
                    "hot,body,beauty,safety,clean,alive,depress,boring,rich," +
                    "beauty_factor,safety_factor,clean_factor,alive_factor,depress_factor,boring_factor,rich_factor,image_name,audio_name)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{user_name,project_name,point.getPoint_name(),
                    point.getType(), point.get_X(),point.get_Y(), point.getHot(),point.getBody(),
                    point.getScore()[0],point.getScore()[1],point.getScore()[2],point.getScore()[3],point.getScore()[4], point.getScore()[5],point.getScore()[6],
                    point.getFactor()[0],point.getFactor()[1],point.getFactor()[2],point.getFactor()[3],point.getFactor()[4], point.getFactor()[5],point.getFactor()[6],
                    Image_names,Audio_names});
        }
    }
    public ArrayList<String> getProject_data(String user_name){
        ArrayList<String> list = new ArrayList<>();
        String selection = "user_name=?";
        String[] selectionArgs = new String[]{user_name};
        Cursor cursor = db.query("project",null,selection,selectionArgs,null,null,"user_name DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            list.add(project_name);
        }
        return list;
    }

    public ArrayList<Point> getValue_points(String user_name,String project_name){
        ArrayList<Point> points = new ArrayList<>();
        String selection = "user_name=? AND project_name=?";
        String[] selectionArgs = new String[]{user_name, project_name};
        Cursor cursor = db.query("point_value",null,selection,selectionArgs,null,null,"user_name DESC");
        while(cursor.moveToNext()){
            Point point = new Point();
            @SuppressLint("Range") String point_name = cursor.getString(cursor.getColumnIndex("point_name"));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") String x = cursor.getString(cursor.getColumnIndex("x"));
            @SuppressLint("Range") String y = cursor.getString(cursor.getColumnIndex("y"));
            @SuppressLint("Range") String CHILL = cursor.getString(cursor.getColumnIndex("CHILL"));
            @SuppressLint("Range") String HEAT_INDEX = cursor.getString(cursor.getColumnIndex("HEAT_INDEX"));
            @SuppressLint("Range") String shiqiu_temperature = cursor.getString(cursor.getColumnIndex("shiqiu_temperature"));
            @SuppressLint("Range") String T_limit = cursor.getString(cursor.getColumnIndex("T_limit"));
            @SuppressLint("Range") String diqiu_temperature = cursor.getString(cursor.getColumnIndex("diqiu_temperature"));
            @SuppressLint("Range") String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
            @SuppressLint("Range") String xiangdui_wetness = cursor.getString(cursor.getColumnIndex("xiangdui_wetness"));
            @SuppressLint("Range") String loudian_temperature = cursor.getString(cursor.getColumnIndex("loudian_temperature"));
            @SuppressLint("Range") String pressure = cursor.getString(cursor.getColumnIndex("pressure"));
            @SuppressLint("Range") String altitude = cursor.getString(cursor.getColumnIndex("altitude"));
            @SuppressLint("Range") String density = cursor.getString(cursor.getColumnIndex("density"));
            @SuppressLint("Range") String air_TVOC = cursor.getString(cursor.getColumnIndex("air_TVOC"));
            @SuppressLint("Range") String air_C6H6 = cursor.getString(cursor.getColumnIndex("air_C6H6"));
            @SuppressLint("Range") String air_HCHO = cursor.getString(cursor.getColumnIndex("air_HCHO"));
            @SuppressLint("Range") String air_CO2 = cursor.getString(cursor.getColumnIndex("air_CO2"));
            @SuppressLint("Range") String air_PM25 = cursor.getString(cursor.getColumnIndex("air_PM25"));
            @SuppressLint("Range") String air_PM10 = cursor.getString(cursor.getColumnIndex("air_PM10"));
            @SuppressLint("Range") String daylight = cursor.getString(cursor.getColumnIndex("daylight"));
            @SuppressLint("Range") String wind = cursor.getString(cursor.getColumnIndex("wind"));
            point.setname(point_name);
            point.set_xy(x,y);
            point.set_type(type);
            point.set_value(CHILL,HEAT_INDEX,shiqiu_temperature,T_limit,diqiu_temperature,temperature,xiangdui_wetness,loudian_temperature,
                    pressure,altitude,density,air_TVOC,air_C6H6,air_HCHO,air_CO2,air_PM25,air_PM10,daylight,wind);
            points.add(point);
        }
        return points;
    }
    public ArrayList<Point> getFeel_points(String user_name,String project_name){
        ArrayList<Point> points = new ArrayList<>();
        String selection = "user_name=? AND project_name=?";
        String[] selectionArgs = new String[]{user_name, project_name};
        Cursor cursor = db.query("point_feel",null,selection,selectionArgs,null,null,"user_name DESC");
        while(cursor.moveToNext()){
            Point point = new Point();
            @SuppressLint("Range") String point_name = cursor.getString(cursor.getColumnIndex("point_name"));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") String x = cursor.getString(cursor.getColumnIndex("x"));
            @SuppressLint("Range") String y = cursor.getString(cursor.getColumnIndex("y"));
            @SuppressLint("Range") String hot = cursor.getString(cursor.getColumnIndex("hot"));
            @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
            @SuppressLint("Range") String beauty = cursor.getString(cursor.getColumnIndex("beauty"));
            @SuppressLint("Range") String safety = cursor.getString(cursor.getColumnIndex("safety"));
            @SuppressLint("Range") String clean = cursor.getString(cursor.getColumnIndex("clean"));
            @SuppressLint("Range") String alive = cursor.getString(cursor.getColumnIndex("alive"));
            @SuppressLint("Range") String depress = cursor.getString(cursor.getColumnIndex("depress"));
            @SuppressLint("Range") String boring = cursor.getString(cursor.getColumnIndex("boring"));
            @SuppressLint("Range") String rich = cursor.getString(cursor.getColumnIndex("rich"));
            @SuppressLint("Range") String beauty_factor = cursor.getString(cursor.getColumnIndex("beauty_factor"));
            @SuppressLint("Range") String safety_factor = cursor.getString(cursor.getColumnIndex("safety_factor"));
            @SuppressLint("Range") String clean_factor = cursor.getString(cursor.getColumnIndex("clean_factor"));
            @SuppressLint("Range") String alive_factor = cursor.getString(cursor.getColumnIndex("alive_factor"));
            @SuppressLint("Range") String depress_factor = cursor.getString(cursor.getColumnIndex("depress_factor"));
            @SuppressLint("Range") String boring_factor = cursor.getString(cursor.getColumnIndex("boring_factor"));
            @SuppressLint("Range") String rich_factor = cursor.getString(cursor.getColumnIndex("rich_factor"));
            point.setname(point_name);
            point.set_xy(x,y);
            point.set_type(type);
            point.set_hotbody(hot,body);
            point.set_score(beauty,safety,clean,alive,depress,boring,rich);
            point.set_factor(beauty_factor,safety_factor,clean_factor,alive_factor,depress_factor,boring_factor,rich_factor);

            String selection1 = "user_name=? AND project_name=? AND point_name=?";
            String[] selectionArgs1 = new String[]{user_name, project_name, point_name};
            Cursor cursor1 = db.query("image",null,selection1,selectionArgs1,null,null,"user_name DESC");
            while(cursor1.moveToNext()){
                @SuppressLint("Range") String image_name = cursor1.getString(cursor1.getColumnIndex("image_name"));
                @SuppressLint("Range") byte[] image_data = cursor1.getBlob(cursor1.getColumnIndex("image_data"));
                @SuppressLint("Range") int image_size = cursor1.getInt(cursor1.getColumnIndex("image_size"));
                point.add_image(image_data,image_name,image_size);
            }
            Cursor cursor2 = db.query("audio",null,selection1,selectionArgs1,null,null,"user_name DESC");
            while(cursor2.moveToNext()){
                @SuppressLint("Range") String audio_name = cursor2.getString(cursor2.getColumnIndex("audio_name"));
                @SuppressLint("Range") byte[] audio_data = cursor2.getBlob(cursor2.getColumnIndex("audio_data"));
                point.add_audio(audio_data,audio_name);
            }
            points.add(point);
        }
        return points;
    }
}


