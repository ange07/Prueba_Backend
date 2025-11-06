package org.generation.muebleria.repository;

import org.generation.muebleria.model.EstadoPedido;
import org.generation.muebleria.model.Pedidos;
import org.generation.muebleria.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos,Long> {
    List<Pedidos> findByUsuario(Usuarios usuario);

    List<Pedidos> findByEstadoPedido(EstadoPedido estado);
}
