package fr.aymax.SpringAuth.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.aymax.SpringAuth.entity.MyUser;

/**
 * When user enters the user name and password to login into the application then getActiveUser() method is used by MyAppUserDetailsService to load the user for authentication and authorization. 
 * @Transactional annotation is used make DAO methods transactional. 
 * This annotation can be used at class level as well as method level. It has attributes to define propagation, transaction isolation level etc.
 */
@Repository
@Transactional
public class UserDAO 
{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * Returns user profile for a given user name.
	 */
	public MyUser getActiveUser(String username) 
	{
		MyUser activeUser = new MyUser();
		short enabled = 1;
		
		List<?> list = hibernateTemplate.find("FROM MyUser WHERE username=? and enabled=?", username, enabled);
		
		if(!list.isEmpty())
			activeUser = (MyUser)list.get(0);
		System.out.println("\n>>>>> "+activeUser.getUsername()+"\n");
		return activeUser;
	}
} 
