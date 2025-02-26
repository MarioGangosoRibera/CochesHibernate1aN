package com.example.cocheshibernate1an.DAO;

import com.example.cocheshibernate1an.Model.Coche;
import org.hibernate.Session;
import java.util.List;

public interface CocheDAO {
    boolean crearCoche(Session session,Coche coche);
    boolean eliminarCoche(Session session,Coche coche);
    boolean actualizarCoche(Session session,Coche coche);

    List<Coche> listarCoches(Session session);
}
