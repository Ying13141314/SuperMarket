package Servidor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static synchronized void buildSessionFactory() {
        if (sessionFactory == null) {
            Configuration conf = new Configuration().configure("hibernate.cfg.xml");
            conf.setProperty("hibernate.current_session_context_class", "thread");
            sessionFactory = conf.buildSessionFactory();
            //ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
            //sessionFactory = conf.buildSessionFactory(sr);
        }
    }

    public static void openSession() {
        Session session = sessionFactory.openSession();
        ThreadLocalSessionContext.bind(session);
    }

    public static Session getSession() {
        if (sessionFactory==null)  {
            buildSessionFactory();
        }
        return sessionFactory.getCurrentSession();
    }

    public static void closeSession() {
        Session session = ThreadLocalSessionContext.unbind(sessionFactory);
        if (session!=null) {
            session.close();
        }
    }

    public static void closeSessionFactory() {
        if ((sessionFactory!=null) && (sessionFactory.isClosed()==false)) {
            sessionFactory.close();
        }
    }
}
