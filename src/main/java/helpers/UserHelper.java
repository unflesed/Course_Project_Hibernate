package helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.Service;
import web_service.User;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserHelper {
    private final SessionFactory sessionFactory;

    public UserHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
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

        User user = session.get(User.class, idUser);

        session.close();

        return user;
    }

    public void remove(User user) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();

        session.close();

    }

    public List<User> getAllUsers(){
        Session session = sessionFactory.openSession();

        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);

        List<User> userList = session.createQuery(criteriaQuery).getResultList();

        return userList;
    }

        public User checkUserPassword(String user, String password){
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root)
                .where(cb.and(cb.equal(root.get("user_name"), user.toUpperCase()),
                        cb.equal(root.get("password"), password.toUpperCase())));

        Query query = session.createQuery(criteriaQuery);

        List<User> list = query.getResultList();

        session.close();

        for (User u : list) {
            return u;
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
