package fr.aymax.SpringAuth.service;

import org.springframework.security.access.annotation.Secured;

import fr.aymax.SpringAuth.entity.MyUser;

public interface IUserService 
{
	//Secrue method, the service layer method getDataByUserName() can be accessed by an authenticated user with ROLE_ADMIN
	@Secured ({"ROLE_ADMIN"})
	MyUser getDataByUserName(String username);
} 
