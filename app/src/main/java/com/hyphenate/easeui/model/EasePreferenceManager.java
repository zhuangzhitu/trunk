package com.hyphenate.easeui.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.hyphenate.easeui.controller.EaseUI;

import java.util.Set;

public class EasePreferenceManager {
    private static final String KEY_AT_GROUPS = "AT_GROUPS";
    private static EasePreferenceManager instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;

    @SuppressLint("CommitPrefEdits")
    private EasePreferenceManager() {
        mSharedPreferences = EaseUI.getInstance().getContext().getSharedPreferences("EM_SP_AT_MESSAGE", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public synchronized static EasePreferenceManager getInstance() {
        if (instance == null) {
            instance = new EasePreferenceManager();
        }
        return instance;

    }

    public Set<String> getAtMeGroups() {
        return mSharedPreferences.getStringSet(KEY_AT_GROUPS, null);
    }

    public void setAtMeGroups(Set<String> groups) {
        editor.remove(KEY_AT_GROUPS);
        editor.putStringSet(KEY_AT_GROUPS, groups);
        editor.apply();
    }

}
