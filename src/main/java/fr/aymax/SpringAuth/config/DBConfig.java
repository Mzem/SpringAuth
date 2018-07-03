package fr.aymax.SpringAuth.config;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource; //To create a data source, we are using third party library commons-dbcp2
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.logging.Logger;

@Configuration 
@EnableTransactionManagement //Enables annotation-driven transaction management capability in spring
@PropertySource("classpath:db.properties")
public class DBConfig 
{
	//Contains propreties from property source
	@Autowired
	private Environment env;	
	
	//A logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * A helper class used to interact with database, it automatically converts hibernate exceptions to data access exceptions, used by DAO class.
	 */
	@Bean
	public HibernateTemplate hibernateTemplate() {
	      return new HibernateTemplate(sessionFactory());
	}
	
	/**
	 * Configure data source.
	 */
 	@Bean
	public SessionFactory sessionFactory() {
	      LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
	      lsfb.setDataSource(getDataSource());
	      lsfb.setPackagesToScan("fr.aymax.SpringAuth.entity");
	      lsfb.setHibernateProperties(hibernateProperties());
	      try {
		     lsfb.afterPropertiesSet();
	      } catch (IOException e) {
		     e.printStackTrace();
	      }
	      return lsfb.getObject();
	}
    
    /** 
     * Instaciate data source.
     * For basic implementation we are using BasicDataSource class of commons-dbcp2.
     */
    @Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
	     
	    logger.info(">>> URL=" + env.getProperty("database.url"));
		logger.info(">>> USER=" + env.getProperty("database.username"));
	     
	    dataSource.setUrl(env.getProperty("database.url"));
	    dataSource.setUsername(env.getProperty("database.username"));
	    dataSource.setPassword(env.getProperty("database.password"));
	    return dataSource;
	}
	
	/**
	 * To enable HibernateTransactionManager we have to create a bean of it.
	 */
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(){
		return new HibernateTransactionManager(sessionFactory());
	}
	
	/**
	 * Configure hibernate properties by created a method that returns java Properties loaded with hibernate properties.
	 */
	private Properties hibernateProperties() {
		 Properties properties = new Properties();
		 properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		 properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		 properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		 return properties;        
	}	
}
