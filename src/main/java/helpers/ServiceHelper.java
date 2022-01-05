package helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.Service;


public class ServiceHelper {
    private final SessionFactory sessionFactory;


    public ServiceHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addService(Service service){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(service);
        session.getTransaction().commit();

        session.close();
    }
}
