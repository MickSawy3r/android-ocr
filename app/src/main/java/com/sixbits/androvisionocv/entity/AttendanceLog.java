package com.sixbits.androvisionocv.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "log_db")
public class AttendanceLog {
    public static final String TABLE_NAME = "log_db";

    // region KEYS
    private static final String KEY_NAME = "name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_NATIONALITY = "nationality";
    private static final String KEY_AGE = "age";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_CONTRACT_TYPE = "contractType";
    private static final String KEY_SECTION = "section";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_POSITION = "position";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_DATE = "date";
    private static final String KEY_ROW = "raw";
    // endregion

    // region Variables
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = KEY_ROW)
    @SerializedName(KEY_NAME)
    @Expose
    private String raw;

    @ColumnInfo(name = KEY_DEPARTMENT)
    @SerializedName(KEY_DEPARTMENT)
    @Expose
    private String department;

    @ColumnInfo(name = KEY_NAME)
    @SerializedName(KEY_NAME)
    @Expose
    private String name;

    @ColumnInfo(name = KEY_START_DATE)
    @SerializedName(KEY_START_DATE)
    @Expose
    private String startDate;

    @ColumnInfo(name = KEY_END_DATE)
    @SerializedName(KEY_END_DATE)
    @Expose
    private String endDate;

    @ColumnInfo(name = KEY_DATE)
    @SerializedName(KEY_NAME)
    @Expose
    private String date;

    @ColumnInfo(name = KEY_GENDER)
    @SerializedName(KEY_GENDER)
    @Expose
    private String gender;

    @ColumnInfo(name = KEY_NATIONALITY)
    @SerializedName(KEY_NATIONALITY)
    @Expose
    private String nationality;

    @ColumnInfo(name = KEY_AGE)
    @SerializedName(KEY_AGE)
    @Expose
    private Integer age;

    @ColumnInfo(name = KEY_MOBILE)
    @SerializedName(KEY_MOBILE)
    @Expose
    private String mobile;

    @ColumnInfo(name = KEY_CONTRACT_TYPE)
    @SerializedName(KEY_CONTRACT_TYPE)
    @Expose
    private String contractType;

    @ColumnInfo(name = KEY_SECTION)
    @SerializedName(KEY_SECTION)
    @Expose
    private String section;

    @ColumnInfo(name = KEY_DIVISION)
    @SerializedName(KEY_DIVISION)
    @Expose
    private String division;

    @ColumnInfo(name = KEY_POSITION)
    @SerializedName(KEY_POSITION)
    @Expose
    private String position;


// endregion

    public JsonObject toJSON() {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty(KEY_NAME, name);
        object.addProperty(KEY_GENDER, gender);
        object.addProperty(KEY_NATIONALITY, nationality);
        object.addProperty(KEY_AGE, age);
        object.addProperty(KEY_MOBILE, mobile);
        object.addProperty(KEY_CONTRACT_TYPE, contractType);
        object.addProperty(KEY_SECTION, section);
        object.addProperty(KEY_DIVISION, division);
        object.addProperty(KEY_POSITION, position);
        object.addProperty(KEY_DATE, date);
        object.addProperty(KEY_DEPARTMENT, department);
        object.addProperty(KEY_START_DATE, startDate);
        object.addProperty(KEY_END_DATE, endDate);
        return object;
    }

    public AttendanceLog() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        this.date = dateFormat.format(currentDate);
    }

    @NonNull
    @Override
    public String toString() {
        return toJSON().toString();
    }

    // region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    // endregion
}

