package com.sixbits.androvisionocv.network;

import com.google.gson.JsonObject;
import com.sixbits.androvisionocv.entity.AttendanceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface attendanceAPI {

    @POST("echo")
    Call <AttendanceResponse> postAttendance(@Body JsonObject attendanceRequest);
}
