package com.zanghongtu.imserver.service.impl;

import com.zanghongtu.imserver.model.User;
import com.zanghongtu.imserver.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public User login(String name, String password) {
        return null;
    }
}
