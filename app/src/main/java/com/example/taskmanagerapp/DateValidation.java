package com.example.taskmanagerapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {

    public static boolean isDateValid(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date d = sdf.parse(date);

            // Check if the date is not today
            Date today = new Date();
            if (sdf.format(today).equals(sdf.format(d))) {
                return false;
            }

            // Check if the date is before today
            if (d.before(today)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isInputValid(String title, String description, String dueDate) {
        return !(title == null || title.isEmpty() || description == null || description.isEmpty() || dueDate == null || dueDate.isEmpty());
    }
}


