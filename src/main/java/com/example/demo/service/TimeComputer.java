package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.TimeHolder;
import com.example.demo.utility.Util;
import com.hs.gpxparser.modal.Waypoint;


public class TimeComputer {
    public static TimeHolder generateTimes(List<Waypoint> points){

        double totaldist =0.0;
        double activeTime = 0.0;
        double totaltime = 0.0;
        double totaldescTime = 0.0;
        double totalascTime = 0.0;
        double totalflatTime =0.0;
        Waypoint last = null;
        for(Waypoint point:points){
            double time =0.0;
            double distXy =0.0;
            double distEle=0.0;
            double m;
            if(last != null){
                distEle = point.getElevation()-last.getElevation();
                time = (point.getTime().getTime()-last.getTime().getTime())/1000;
                distXy = Util.distVincentY(point.getLongitude(), point.getLatitude(), last.getLongitude(), last.getLatitude());
                double dist3d=Math.sqrt(Math.pow(distXy,2)+Math.pow(distEle,2));
                m = ((totaldist+dist3d)-totaldist)/((totaltime+time)-totaltime);
            }
            else {
                time =0.0;
                m=0.0;
            }
            totaltime+=time;
            if(m>0.1){

                if(distEle==0.0){
                    totalflatTime+=time;
                }
                else if(distEle>0.0){
                    totalascTime+=time;
                }
                else if(distEle<0.0){
                    totaldescTime+=time;
                }
                activeTime+=time;
            }
            last = point;
        }

        TimeHolder timeHolder= new TimeHolder();
        timeHolder.setTotalTime(totaltime);
        timeHolder.setAscTime(totalascTime);
        timeHolder.setDescTime(totaldescTime);
        timeHolder.setFlatTime(totalflatTime);
        timeHolder.setActiveTime(activeTime);
        return timeHolder;
    }

}
