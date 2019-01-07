package com.ministere.cooperation.service;

import com.ministere.cooperation.model.International.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user, String rolee);
}
