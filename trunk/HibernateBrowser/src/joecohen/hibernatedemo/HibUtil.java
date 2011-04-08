/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package joecohen.hibernatedemo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibUtil {

	private static boolean transaction = false;
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session startTransaction() {
    	
    	while (transaction==true)
    		/*spin*/;
    	transaction = true;
    	try
    	{
    		Session s = sessionFactory.getCurrentSession();
    		s.beginTransaction();
    		return s;
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		transaction = false;
    		return null;
    	}
        
    	
    } 
    
    
    public static void commitTransaction() {
    	
    	try
    	{
    		sessionFactory.getCurrentSession().getTransaction().commit();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		
    	}
    	
    	transaction = false;
    }
    
    public static void shutdown() {
    	
    	try{
    		startTransaction();
    		Query sql = sessionFactory.getCurrentSession().createSQLQuery("SHUTDOWN");
    		sql.executeUpdate();
    		commitTransaction();
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
	public static void openDBEditor()
	{
		String[] s = new String[]{	"--driver","org.hsqldb.jdbcDriver",
				"--url","jdbc:hsqldb:file:db/testdb",
				"--user","sa" };

		org.hsqldb.util.DatabaseManagerSwing.main(s);
	}

}