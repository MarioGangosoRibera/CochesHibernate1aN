package com.example.cocheshibernate1an.DAO;

import com.example.cocheshibernate1an.Model.Multa;
import org.hibernate.Session;
import java.util.List;

public class MultaDAOImpl implements MultaDAO{
    @Override
    public boolean crearMulta(Session session, Multa multa){
        boolean crearCoche=true;
        try {
            session.beginTransaction();
            session.save(multa);
            session.getTransaction().commit();
        }catch (Exception e){
            crearCoche=false;
            if (session.getTransaction()!=null){
                session.getTransaction().rollback();
            }
        }
        return crearCoche;
    }

    @Override
    public boolean eliminarMulta(Session session, Multa multa) {
        boolean eliminarcoche=true;
        try {
            session.beginTransaction();
            session.delete(multa);
            session.getTransaction().commit();
        }catch (Exception e) {
            eliminarcoche=false;
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return eliminarcoche;
    }

    @Override
    public boolean actualizarMulta(Session session, Multa multa) {
        boolean actualizarcoche=true;
        try {
            session.beginTransaction();
            session.update(multa);
            session.getTransaction().commit();
        }catch (Exception e) {
            actualizarcoche=false;
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return actualizarcoche;
    }

    @Override
    public List<Multa> listarMultaCoche(Session session, String matricula) {
        List<Multa> listaMultas = null;
        try {
            session.beginTransaction();
            listaMultas = session.createQuery("from Multa where coche.matricula = :matricula", Multa.class)
                    .setParameter("matricula", matricula)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        }
        return listaMultas;
    }
}
