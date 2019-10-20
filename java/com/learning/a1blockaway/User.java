package com.learning.a1blockaway;

public class User {

    private static final String TAG = "User";

    private static String name;
    private static long age;
    private static String gender;
    private static long height;
    private static String address;
    private static boolean isRespondent;
    private static String userID;

    public static Request getRequestor() {
        return requestor;
    }

    public static void setRequestor(Request requestor) {
        User.requestor = requestor;
    }

    private static Request requestor;

    public static boolean getAccepted() {
        return accepted;
    }

    public static void setAccepted(boolean accepted) {
        User.accepted = accepted;
    }

    private static boolean accepted;

    public static boolean isActiveRequest() {
        return activeRequest;
    }

    public static void setActiveRequest(boolean activeRequest) {
        User.activeRequest = activeRequest;
    }

    private static boolean activeRequest;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static long getAge() {
        return age;
    }

    public static void setAge(long age) {
        User.age = age;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        User.gender = gender;
    }

    public static long getHeight() {
        return height;
    }

    public static void setHeight(long height) {
        User.height = height;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static boolean isIsRespondent() {
        return isRespondent;
    }

    public static void setIsRespondent(boolean isRespondent) {
        User.isRespondent = isRespondent;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        User.userID = userID;
    }
}
