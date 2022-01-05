package helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.Profile;
import web_service.User;

public class ProfileHelper {
    private final SessionFactory sessionFactory;


    public ProfileHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addProfile(Profile profile){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(profile);
        session.getTransaction().commit();

        session.close();
    }
    public Profile getProfileById(long idProfile){
        Session session = sessionFactory.openSession();

        return session.get(Profile.class, idProfile);
    }

    public void updateProfile(Profile profile){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.update(profile);
        session.getTransaction().commit();

        session.close();
    }
}
