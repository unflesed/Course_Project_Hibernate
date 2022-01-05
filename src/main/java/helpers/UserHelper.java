package helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.Service;
import web_service.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserHelper {
    private final SessionFactory sessionFactory;

    EntityManagerFactory emf;
    EntityManager em;

    public UserHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
        emf = Persistence.createEntityManagerFactory("base");
        em = emf.createEntityManager();
    }

    public void addUser(User user){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        session.close();
    }

    public void updateUser(User user){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();

        session.close();
    }

    public User getUserById(long idUser){
        Session session = sessionFactory.openSession();

        return session.get(User.class, idUser);
    }

    public void remove(User user) {
        em.getTransaction().begin();
        em.remove(em.contains(user) ? user : em.merge(user));
        em.getTransaction().commit();
    }

    public void getAllUsers(){
        Session session = sessionFactory.openSession();

        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);

        List<User> userList = session.createQuery(criteriaQuery).getResultList();

        for (User user: userList) {
            System.out.println(user);
        }

    }

    public User checkUserPassword(String user, String password){
        Session session = sessionFactory.openSession();

        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);

        List<User> list = session.createQuery(criteriaQuery).getResultList();

        for (User u: list) {
            if (u.getUser_name().equalsIgnoreCase(user) && u.getPassword().equalsIgnoreCase(password)) {
                return u;
            }
        }
        return null;
    }
    public void subscribeService(long idService, long idUser){
        Session session = sessionFactory.openSession();

        User user = session.get(User.class, idUser);
        Service service = session.get(Service.class, idService);

        user.addService(service);

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        session.close();
    }

    public void unSubscribeService(long idService, long idUser){
        Session session = sessionFactory.openSession();

        User user = session.get(User.class, idUser);
        Service service = session.get(Service.class, idService);

        user.deleteService(service);

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        session.close();
    }
}
