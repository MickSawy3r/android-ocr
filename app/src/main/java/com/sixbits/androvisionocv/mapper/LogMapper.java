package com.sixbits.androvisionocv.mapper;

import android.util.Log;

import com.sixbits.androvisionocv.entity.AttendanceLog;

public class LogMapper {
    private static final String TAG = "ML_toLog";

    // region Keys
    private static final String TEXT_KEY_NAME = "Name";
    private static final String TEXT_KEY_GENDER = "Gender";
    private static final String TEXT_KEY_NATIONALITY = "Nationality";
    private static final String TEXT_KEY_AGE = "Age";
    private static final String TEXT_KEY_MOBILE = "Mobile";
    private static final String TEXT_KEY_CONTRACT_TYPE = "Contract";
    private static final String TEXT_KEY_SECTION = "Section";
    private static final String TEXT_KEY_DIVISION = "Division";
    private static final String TEXT_KEY_POSITION = "Position";
    private static final String TEXT_KEY_START_DATE = "Start date";
    private static final String TEXT_KEY_END_DATE = "End Date";
    private static final String TEXT_KEY_DEPARTMENT = "Department";
    private static final String TEXT_KEY_ROW = "row";
    // endregion

    public AttendanceLog toAttendanceLog(String txt) {
        Log.d(TAG, "showText: " + txt);
        String[] words = txt.split("\\s+");
        String activeField = "";
        String currentQuery = "";
        AttendanceLog log = new AttendanceLog();

        log.setRaw(txt);

        for (String word : words) {
            // Check if Field Name
            if (isActiveField(word) != null) {
                if (activeField != null)
                    commitChanges(log, activeField, currentQuery);
                currentQuery = "";
                activeField = isActiveField(word);
                Log.d(TAG, "Current Log: " + log.toString());
                Log.d(TAG, "Active Field: " + activeField);
                continue;
            }
            currentQuery = currentQuery.concat(" " + word);
            Log.d(TAG, "showText: " + currentQuery);
        }
        Log.d(TAG, "showText: Committing final Query " + currentQuery + " to field " + activeField);
        if (activeField != null)
            commitChanges(log, activeField, currentQuery);

        Log.d(TAG, "showText: " + log.toString());

        return log;
    }

    private String isActiveField(String key) {
        switch (key) {
            case TEXT_KEY_NAME:
                return TEXT_KEY_NAME;
            case TEXT_KEY_GENDER:
                return TEXT_KEY_GENDER;
            case TEXT_KEY_NATIONALITY:
                return TEXT_KEY_NATIONALITY;
            case TEXT_KEY_AGE:
                return TEXT_KEY_AGE;
            case TEXT_KEY_DEPARTMENT:
                return TEXT_KEY_DEPARTMENT;
            case TEXT_KEY_MOBILE:
                return TEXT_KEY_MOBILE;
            case TEXT_KEY_CONTRACT_TYPE:
                return TEXT_KEY_CONTRACT_TYPE;
            case TEXT_KEY_SECTION:
                return TEXT_KEY_SECTION;
            case TEXT_KEY_DIVISION:
                return TEXT_KEY_DIVISION;
            case TEXT_KEY_POSITION:
                return TEXT_KEY_POSITION;
            case TEXT_KEY_START_DATE:
                return TEXT_KEY_START_DATE;
            case TEXT_KEY_END_DATE:
                return TEXT_KEY_END_DATE;
            default:
                return null;
        }
    }

    private AttendanceLog commitChanges(AttendanceLog currentLog, String activeFieldKey, String query) {
        switch (activeFieldKey) {
            case TEXT_KEY_NAME:
                currentLog.setName(query);
                return currentLog;
            case TEXT_KEY_GENDER:
                currentLog.setGender(query);
                return currentLog;
            case TEXT_KEY_NATIONALITY:
                currentLog.setNationality(query);
                return currentLog;
            case TEXT_KEY_DEPARTMENT:
                currentLog.setDepartment(query);
                return currentLog;
            case TEXT_KEY_AGE:
                currentLog.setAge(Integer.parseInt(query.replaceAll("[^0-9]", "")));
                Log.d(TAG, "commitChanges: Age is " + query);
                return currentLog;
            case TEXT_KEY_MOBILE:
                currentLog.setMobile(query);
                return currentLog;
            case TEXT_KEY_CONTRACT_TYPE:
                currentLog.setContractType(query);
                return currentLog;
            case TEXT_KEY_SECTION:
                currentLog.setSection(query);
                return currentLog;
            case TEXT_KEY_DIVISION:
                currentLog.setDivision(query);
                return currentLog;
            case TEXT_KEY_POSITION:
                currentLog.setPosition(query);
                return currentLog;
            case TEXT_KEY_START_DATE:
                currentLog.setStartDate(query);
                return currentLog;
            case TEXT_KEY_END_DATE:
                currentLog.setEndDate(query);
                return currentLog;
            default:
                return currentLog;
        }
    }
}
