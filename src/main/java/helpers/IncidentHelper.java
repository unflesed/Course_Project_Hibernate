package helpers;

import web_service.Incident;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
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

    public  void getAllIncidents(){
        Session session = sessionFactory.openSession();

        CriteriaQuery<Incident> criteriaQuery = session.getCriteriaBuilder().createQuery(Incident.class);
        criteriaQuery.from(Incident.class);

        List<Incident> incidentList = session.createQuery(criteriaQuery).getResultList();

        for (Incident i: incidentList) {
            System.out.println(i);
        }
    }

    public  void getAllActiveIncidents(){
        Session session = sessionFactory.openSession();

        CriteriaQuery<Incident> criteriaQuery = session.getCriteriaBuilder().createQuery(Incident.class);
        criteriaQuery.from(Incident.class);

        List<Incident> incidentList = session.createQuery(criteriaQuery).getResultList();

        for (Incident i: incidentList) {
            if (i.isActive()) {
                System.out.println(i);
            }
        }
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
}
