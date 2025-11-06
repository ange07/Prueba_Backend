package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.EstadoPedido;
import org.generation.muebleria.model.Pedidos;

import org.generation.muebleria.model.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IPedidosService {

    Pedidos getProductsById(Long id);
    Optional<Pedidos> getPedidosById(Long id);
    Pedidos crearPedidos(Pedidos pedido);
    Optional<Pedidos> updateEstadoPedidos(Long id, EstadoPedido nuevoEstado);
    List<Pedidos> getPedidossByUsuario(Usuarios usuario);
    List<Pedidos> getPedidossByEstado(EstadoPedido estado);
    void deletePedidoById(Long id);
}
