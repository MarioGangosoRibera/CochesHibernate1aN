package com.example.cocheshibernate1an.DAO;

import com.example.cocheshibernate1an.Model.Multa;
import org.hibernate.Session;

import java.util.List;

public interface MultaDAO {
    boolean crearMulta(Session session, Multa multa);
    boolean eliminarMulta(Session session,Multa multa);
    boolean actualizarMulta(Session session,Multa multa);
    List<Multa> listarMultaCoche(Session session, String matricula);
}
