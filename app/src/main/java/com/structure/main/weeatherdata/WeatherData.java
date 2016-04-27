package com.structure.main.weeatherdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * Created by yuchao on 2/16/16.
 * for example
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
    private WeatherCoord coord;
    private int cod;
    private String base;
    private String name;
    private WeatherMain main;
    private List<Weather> weather;
    private WeatherSys sys;


    protected WeatherData(Parcel in) {
        cod     = in.readInt();
        base    = in.readString();
        name    = in.readString();
        weather = in.createTypedArrayList(Weather.CREATOR);
        coord   = in.readParcelable(WeatherCoord.class.getClassLoader());
        sys     = in.readParcelable(WeatherSys.class.getClassLoader());
        main    = in.readParcelable(WeatherMain.class.getClassLoader());

    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public int getCod() {
        return cod;
    }

    public void setCod(int code) {
        this.cod = code;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherCoord getCoord() {
        return coord;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherMain getMain() {
        return main;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cod);
        dest.writeString(base);
        dest.writeString(name);
        dest.writeTypedList(weather);
        dest.writeParcelable(coord, 0);
        dest.writeParcelable(sys, 1);
        dest.writeParcelable(main, 2);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" sys = " + sys.toString());
        buffer.append(" weather = " + weather.toString());
        buffer.append(" base = " + base);
        buffer.append(" main = " + main.toString());
        buffer.append(" wind = " );
        return buffer.toString();
    }



}


