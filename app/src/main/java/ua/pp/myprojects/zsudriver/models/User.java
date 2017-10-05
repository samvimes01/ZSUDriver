/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.models;

import java.util.Map;

import ua.pp.myprojects.zsudriver.interfaces.FirebaseSnapshotMapSetter;

public class User implements FirebaseSnapshotMapSetter {
    private static final User ourInstance = new User();
    private static String displayName;
    private static String userId;

    private static String milUnit;
    private static String role;

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    public static void setDisplayName(String displayName) {
        User.displayName = displayName;
    }

    public static void setUserId(String userId) {
        User.userId = userId;
    }

    public static String getDisplayName() {
        return displayName;
    }


    public static String getUserId() {
        return userId;
    }

    public static String getMilUnit() {
        return milUnit;
    }

    private static void setMilUnit(String milUnit) {
        User.milUnit = milUnit;
    }

    public static String getRole() {
        return role;
    }

    private static void setRole(String role) {
        User.role = role;
    }


    public void setFbsSnapshotData(Map<String, Object> fbsUserData) {
        setMilUnit((String) fbsUserData.get("milUnit"));
        setRole((String) fbsUserData.get("role"));
    }


}
