package com.bee.sample.ch9.service;

public interface CreditSystemService {

    int getUserCredit(int userId);

    boolean addCedit(int userId, int score);
}
