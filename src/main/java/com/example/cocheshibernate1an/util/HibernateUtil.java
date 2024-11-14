package com.example.cocheshibernate1an.util;

import com.example.cocheshibernate1an.Model.Coche;
import com.example.cocheshibernate1an.Model.Multa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");

        cfg.addAnnotatedClass(Coche.class);
        cfg.addAnnotatedClass(Multa.class);
        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}
