package helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.UserRole;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserRoleHelper {
    private final SessionFactory sessionFactory;


    public UserRoleHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addUserRole(UserRole userRole){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(userRole);
        session.getTransaction().commit();

        session.close();
    }

    public UserRole getUserRole(String userRole){
        Session session = sessionFactory.openSession();

        CriteriaQuery<UserRole> criteriaQuery = session.getCriteriaBuilder().createQuery(UserRole.class);
        criteriaQuery.from(UserRole.class);

        List<UserRole> roleList = session.createQuery(criteriaQuery).getResultList();

        for (UserRole role: roleList) {
            if (role.getRole_name().equalsIgnoreCase(userRole)) {
                return role;
            }
        }
        return null;
    }
}
