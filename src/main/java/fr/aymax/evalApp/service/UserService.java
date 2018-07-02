package fr.aymax.evalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.aymax.evalApp.dao.UserDAO;
import fr.aymax.evalApp.entity.MyUser;

@Service
public class UserService implements IUserService 
{
	@Autowired
	private UserDAO userDAO;
	
	public MyUser getDataByUserName(String username) {
		return userDAO.getActiveUser(username);
	}
} 
