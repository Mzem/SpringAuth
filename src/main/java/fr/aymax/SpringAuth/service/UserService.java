package fr.aymax.SpringAuth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.aymax.SpringAuth.dao.UserDAO;
import fr.aymax.SpringAuth.entity.MyUser;

@Service
public class UserService implements IUserService 
{
	@Autowired
	private UserDAO userDAO;
	
	public MyUser getDataByUserName(String username) {
		return userDAO.getActiveUser(username);
	}
} 
