package com.playground;


import com.playground.domain.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class App {
    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = createSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Message message = new Message();
            message.setText("Hello World from Hibernate!");
            session.persist(message);
            session.getTransaction().commit();
            // INSERT into MESSAGE (ID, TEXT)
            // values (1, 'Hello World from Hibernate!')

            session.beginTransaction();
            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
            criteriaQuery.from(Message.class);
            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            // SELECT * from MESSAGE
            session.getTransaction().commit();
            for (Message m : messages) {
                System.out.println(m);
            }
        }
    }
}
