package fr.aymax.SpringAuth.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.aymax.SpringAuth.dao.UserDAO;
import fr.aymax.SpringAuth.entity.MyUser;

/**
 * Spring has UserDetailsService interface that loads user specific data. 
 * We need to create a class and implement UserDetailsService for user authentication. 
 * It has a method loadUserByUsername() that accepts user name and returns UserDetails.
 */
@Service
public class AppUserDetailsService implements UserDetailsService 
{
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * Accepts user name and returns UserDetails.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{	
		MyUser activeUser = userDAO.getActiveUser(username);
		//If the user does not exist
		if (activeUser.getRole() == null) {
			activeUser.setUsername("guest");
			activeUser.setPassword("guest");
			activeUser.setRole("ROLE_GUEST");
			activeUser.setEnabled((short)0);
		}
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUser.getRole());
		UserDetails userDetails = (UserDetails) new User (activeUser.getUsername(), activeUser.getPassword(), Arrays.asList(authority));
		
		return userDetails;
	}
} 
