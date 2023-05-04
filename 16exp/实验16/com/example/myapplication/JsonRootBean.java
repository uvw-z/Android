/**
  * Copyright 2022 bejson.com 
  */
package com.example.myapplication;
import java.util.Date;

/**
 * Auto-generated: 2022-06-03 17:47:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private String message;
    private int status;
    private String date;
    private Date time;
    private CityInfo cityInfo;
    private Data data;
    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setDate(String date) {
         this.date = date;
     }
     public String getDate() {
         return date;
     }

    public void setTime(Date time) {
         this.time = time;
     }
     public Date getTime() {
         return time;
     }

    public void setCityInfo(CityInfo cityInfo) {
         this.cityInfo = cityInfo;
     }
     public CityInfo getCityInfo() {
         return cityInfo;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}