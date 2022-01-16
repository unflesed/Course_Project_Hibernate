package helpers;

import web_service.Incident;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import web_service.User;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IncidentHelper {
    private final SessionFactory sessionFactory;


    public IncidentHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addIncident(Incident incident){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(incident);
        session.getTransaction().commit();

        session.close();
    }

    public List<Incident> getAllIncidents(){
        Session session = sessionFactory.openSession();

        CriteriaQuery<Incident> criteriaQuery = session.getCriteriaBuilder().createQuery(Incident.class);
        criteriaQuery.from(Incident.class);

        List<Incident> incidentList = session.createQuery(criteriaQuery).getResultList();

        session.close();

        return incidentList;
    }

    public List<Incident> getAllActiveIncidents(){
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Incident> criteriaQuery = cb.createQuery(Incident.class);
        Root<Incident> root = criteriaQuery.from(Incident.class);

        criteriaQuery.select(root)
                .where(cb.equal(root.get("isActive"), true));

        List<Incident> incidentList = session.createQuery(criteriaQuery).getResultList();

        session.close();

        return incidentList;
    }

    public void closeIncident(long id){
        Session session = sessionFactory.openSession();

        Incident incident = session.get(Incident.class, id);
        incident.setActive(false);

        session.beginTransaction();
        session.save(incident);
        session.getTransaction().commit();

        session.close();
    }

    public void removeIncident(User user){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaDelete<Incident> cd = cb.createCriteriaDelete(Incident.class);
        Root<Incident> root = cd.from(Incident.class);

        cd.where(cb.equal(root.get("user"), user));

        Query query = session.createQuery(cd);

        query.executeUpdate();

        session.getTransaction().commit();

        session.close();
    }
}
