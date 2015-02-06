/**
 * Created by YuGang Yang on January 05, 2015.
 * Copyright 2007-2015 Loopeer.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.laputapp.Laputapp;
import com.laputapp.model.Account;
import java.util.ArrayList;

public class AccountUtils {

  private static AccountUtils singleton = null;

  private static final String ACCOUNT_STORAGE = "account_preference";
  private static final String ACCOUNT_JSON_KEY = "account_json";

  private Account mCurrentAccount;

  private ArrayList<CurrentAccountObserver> mObservers;

  public interface CurrentAccountObserver {
    public void notifyChange();
  }

  public static void registerObserver(CurrentAccountObserver observer) {
    getObservers().add(observer);
  }

  public static void unregisterObserver(CurrentAccountObserver observer) {
    getObservers().remove(observer);
  }

  public static String getAccountToken() {
    if (getCurrentAccount() == null) return null;

    return getCurrentAccount().token;
  }

  private static ArrayList<CurrentAccountObserver> getObservers() {
    if (getInstance().mObservers == null) {
      getInstance().mObservers = new ArrayList<CurrentAccountObserver>();
    }
    return getInstance().mObservers;
  }

  public static void notifyDataChanged() {
    if (getObservers() == null) {
      return;
    }
    for (CurrentAccountObserver observer : getObservers()) {
      observer.notifyChange();
    }
  }

  private static AccountUtils getInstance() {
    if (singleton == null) {
      singleton = new AccountUtils();
    }
    return singleton;
  }

  private AccountUtils() {
    loadAccountData();
  }

  private void loadAccountData() {
    Log.i("AccountUtils", "loadAccountData start");
    SharedPreferences userPreference = Laputapp.getInstance().getSharedPreferences(ACCOUNT_STORAGE,
        Context.MODE_PRIVATE);
    Gson gson = new Gson();
    String userJson = userPreference.getString(ACCOUNT_JSON_KEY, null);
    if (userJson != null) {
      mCurrentAccount = gson.fromJson(userJson, Account.class);
    }
  }

  private void saveUserData() {
    SharedPreferences.Editor editor = Laputapp.getInstance()
        .getSharedPreferences(ACCOUNT_STORAGE, Context.MODE_PRIVATE)
        .edit();
    Gson gson = new Gson();
    if (mCurrentAccount != null) {
      editor.putString(ACCOUNT_JSON_KEY, gson.toJson(mCurrentAccount));
    }
    editor.apply();
    Log.i("AccountUtils", "commit finish");
  }

  private void clearUserData() {
    SharedPreferences.Editor editor = Laputapp.getInstance()
        .getSharedPreferences(ACCOUNT_STORAGE, Context.MODE_PRIVATE)
        .edit();
    editor.clear();
    editor.apply();
  }

  private void clear() {
    mCurrentAccount = null;
    clearUserData();
  }

  public static boolean isLoggedIn() {
    return (AccountUtils.getCurrentAccount() != null);
  }

  public static void logout() {
    getInstance().clear();
    notifyDataChanged();
  }

  public static Account getCurrentAccount() {
    return getInstance().mCurrentAccount;
  }

  public static Integer getCurrentAccountId() {
    if (getCurrentAccount() == null) {
      return null;
    }
    return getCurrentAccount().id;
  }

  public static void setCurrentAccount(Account account) {
    if (getInstance().mCurrentAccount == account) {
      return;
    }
    getInstance().mCurrentAccount = account;
  }

  public static void save() {
    getInstance().saveUserData();
  }

  public static boolean isCurrentAccount(Account account) {
    if (account == null) {
      return false;
    }

    return isCurrentAccountId(account.id);
  }

  public static boolean isCurrentAccountId(Integer id) {
    return !(id == null || getInstance().mCurrentAccount == null)
        && id == getInstance().mCurrentAccount.id;
  }
}
