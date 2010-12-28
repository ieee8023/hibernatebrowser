package joecohen.hibernatebrowser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.metadata.ClassMetadata;


public class HibernateUtil {

    private SessionFactory sessionFactory;
    public static Map<String,DebugConfig> debugConfigs = initconfigs();
    DebugConfig config;
    public static String targetDatabase; 

    public HibernateUtil(DebugConfig config){
    	
    	this.config = config;
        try {
        	 
        	AnnotationConfiguration a = new AnnotationConfiguration();
        	a.configure(config.hbmcfg);
        	a.setProperty("hibernate.current_session_context_class", "thread");
        	a.setProperty("hbm2ddl.auto", "create");
        	a.setProperty("hibernate.connection.url", config.jdbcURL);
        	a.setProperty("hibernate.connection.username", config.jdbcUsername);
        	a.setProperty("hibernate.connection.password", config.jdbcPassword); 
        	a.setProperty("hibernate.connection.driver_class", config.driver_class); 
        	
        	config.installEncryptors();
        	
        	sessionFactory = a.buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public List<ClassMetadata> getMappedClasses(){
    	List<ClassMetadata> results = new ArrayList<ClassMetadata>();
		@SuppressWarnings("unchecked")
		Map<String,ClassMetadata> m = sessionFactory.getAllClassMetadata();
		
		String[] classNames = {};
		classNames = m.keySet().toArray(classNames);
		Arrays.sort(classNames);
		
		for (String s : classNames){
			if (s.contains(config.namespace))
				results.add(m.get(s));
		}
		return results;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static Map<String,DebugConfig> initconfigs(){
    	Properties props = new Properties();
    	try {
			props.load(HibernateUtil.class.getResourceAsStream("HibernateBrowser.properties"));
		} catch (IOException e) {
			System.out.println("HibernateBrowser.properties not found");
		}
    	
    	Map<String,DebugConfig> debugConfigs = new HashMap<String,DebugConfig>();
    	
    	final String hbmcfg = props.getProperty("hbmcfg", "");
    	final String jdbcURL = props.getProperty("jdbcURL", "");
    	final String jdbcDriver = props.getProperty("jdbcDriver", "");
    	final String userName = props.getProperty("userName", "");
    	final String password = props.getProperty("password", "");
    	final String namespace = props.getProperty("namespace", "");
    	
    	{
			DebugConfig config = new DebugConfig(){
		
				@Override
				public void installEncryptors() {
					//put Encrypter code here
				}
			};
			config.hbmcfg = hbmcfg;
			config.jdbcURL = jdbcURL;
			config.jdbcUsername = userName;
			config.jdbcPassword = password;
			config.driver_class = jdbcDriver;
			config.namespace = namespace;
			debugConfigs.put("default",config);
    	}
		return debugConfigs;
	}	
    
}

abstract class DebugConfig{
	
	public String hbmcfg = "";
	public String jdbcURL = "";
	public String jdbcUsername = "";
	public String jdbcPassword = "";
	public String driver_class = "";
	public String namespace = "";
	
	public void installEncryptors(){};
}
