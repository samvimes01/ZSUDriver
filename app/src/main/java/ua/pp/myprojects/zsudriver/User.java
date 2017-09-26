/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

import java.util.Map;

class User implements FirebaseSnapshotMapSetter{
    private static final User ourInstance = new User();
    private static String displayName;
    private static String userId;

    private static String milUnitAccess;
    private static String subUnitAccess;
    private static String role;

    static User getInstance(String name, String uId) {
        displayName = name;
        userId = uId;
        return ourInstance;
    }

    private User() {
    }

    public static String getDisplayName() {
        return displayName;
    }


    public static String getUserId() {
        return userId;
    }

    public static String getMilUnitAccess() {
        return milUnitAccess;
    }

    public static void setMilUnitAccess(String milUnitAccess) {
        User.milUnitAccess = milUnitAccess;
    }

    public static String getSubUnitAccess() {
        return subUnitAccess;
    }

    public static void setSubUnitAccess(String subUnitAccess) {
        User.subUnitAccess = subUnitAccess;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User.role = role;
    }


    public void setFbsSnapshotData(Map<String, Object> fbsUserData) {
        setMilUnitAccess((String) fbsUserData.get("milUnit"));
        setSubUnitAccess((String) fbsUserData.get("subUnit"));
        setRole((String) fbsUserData.get("role"));
    }


}
