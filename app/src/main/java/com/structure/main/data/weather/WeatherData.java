package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by yuchao on 2/16/16.
 * for example
 * http://api.openweathermap.org/data/2.5/weather?q=ShenZhen&APPID=ea574594b9d36ab688642d5fbeab847e
 * {
 *   "coord":{"lon":-122.08,"lat":37.39},
 *   "sys":{
 *           "type":1,"id":451,"message":1.1091,
 *           "country":"United States of America",
 *           "sunrise":1434545231,"sunset":1434598293
 *         },
 *   "weather":[{"id":701,"main":"Mist","description":"mist","icon":"50n"}],
 *   "base":"stations",
 *   "main":{"temp":288.73,"pressure":1014,"humidity":82,"temp_min":284.26,"temp_max":294.15},
 *   "visibility":16093,
 *   "wind":{"speed":4.1,"deg":350},
 *   "clouds":{"all":20},
 *   "dt":1434517063,
 *   "id":5375480,
 *   "name":"Mountain View",
 *   "cod":200
 * }
 */
public class WeatherData implements Parcelable{
    /**坐标*/
    @Expose
    @SerializedName("coord")
    public WeatherCoord coord;
    @Expose
    @SerializedName("weather")
    public List<SubWeather> subWeather;
    @Expose
    @SerializedName("base")
    public String base;
    @Expose
    @SerializedName("main")
    public WeatherMain main;
    @Expose
    @SerializedName("visibility")
    public int visibility;
    @Expose
    @SerializedName("wind")
    public Wind wind;
    @Expose
    @SerializedName("rain")
    public Rain rain;
    @Expose
    @SerializedName("clouds")
    public Clouds clouds;
    @Expose
    @SerializedName("dt")
    public int dt;
    @Expose
    @SerializedName("sys")
    public WeatherSys sys;
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("cod")
    public int cod;
    @Expose
    @SerializedName("name")
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coord, flags);
        dest.writeTypedList(this.subWeather);
        dest.writeString(this.base);
        dest.writeParcelable(this.main, flags);
        dest.writeInt(this.visibility);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.rain, flags);
        dest.writeParcelable(this.clouds, flags);
        dest.writeInt(this.dt);
        dest.writeParcelable(this.sys, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.cod);
        dest.writeString(this.name);
    }

    public WeatherData() {
    }

    protected WeatherData(Parcel in) {
        this.coord = in.readParcelable(WeatherCoord.class.getClassLoader());
        this.subWeather = in.createTypedArrayList(SubWeather.CREATOR);
        this.base = in.readString();
        this.main = in.readParcelable(WeatherMain.class.getClassLoader());
        this.visibility = in.readInt();
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.rain = in.readParcelable(Rain.class.getClassLoader());
        this.clouds = in.readParcelable(Clouds.class.getClassLoader());
        this.dt = in.readInt();
        this.sys = in.readParcelable(WeatherSys.class.getClassLoader());
        this.id = in.readInt();
        this.cod = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel source) {
            return new WeatherData(source);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("longitude : " + coord.lon + " latitude : " + coord.lat);
        if (null != subWeather && null != subWeather.get(0)) {
            buffer.append("\nweather main : " + subWeather.get(0).main +
                " description : " + subWeather.get(0).description);
        }
        buffer.append("\naverage temperature : " + main.temp);
        buffer.append("\nwind speed : " + wind.speed);
        return buffer.toString();
    }

}


