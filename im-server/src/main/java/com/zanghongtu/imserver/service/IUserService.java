package com.zanghongtu.imserver.service;


import com.zanghongtu.imserver.model.User;

public interface IUserService {
    User login(String name, String password);
}
