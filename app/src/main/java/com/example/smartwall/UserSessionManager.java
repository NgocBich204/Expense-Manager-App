package com.example.smartwall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class UserSessionManager {

    // Chìa khóa SharedPreferences
    private static final String PREFER_NAME = "UserSessionPref";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Chìa khóa dữ liệu người dùng
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";

    // SharedPreferences và Editor
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Constructor
    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Tạo phiên đăng nhập cho người dùng
    public void createUserLoginSession(String userId, String userName) {
        // Lưu thông tin đăng nhập
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        // Lưu thay đổi
        editor.apply();
    }

    // Kiểm tra trạng thái đăng nhập của người dùng
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    // Lấy ID người dùng
    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    // Lấy tên người dùng
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, null);
    }

    // Xóa phiên đăng nhập của người dùng
    public void logoutUser() {
        // Xóa tất cả dữ liệu từ SharedPreferences
        editor.clear();
        editor.apply();

        // Chuyển hướng người dùng đến màn hình đăng nhập
        Intent intent = new Intent(context, Login.class);
        // Đóng tất cả các activity khác và bắt đầu activity đăng nhập mới
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
