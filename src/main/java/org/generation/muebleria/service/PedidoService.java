package org.generation.muebleria.service;
import org.generation.muebleria.model.EstadoPedido;
import org.generation.muebleria.model.Pedidos;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.repository.PedidosRepository;
import org.generation.muebleria.service.interfaces.IPedidosService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Importante para las fechas
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class PedidoService implements IPedidosService{
    private final PedidosRepository pedidoRepository;


    @Override
    public Pedidos getProductsById(Long id) {
        return null;
    }

    @Override
    public Optional<Pedidos> getPedidosById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Pedidos crearPedidos(Pedidos pedido) {
        if (pedido.getEstadoPedido() == null) {
            pedido.setEstadoPedido(EstadoPedido.PREPARANDO);
        }
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedidos> updateEstadoPedidos(Long id, EstadoPedido nuevoEstado) {
        Optional<Pedidos> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedidos pedidoExistente = pedidoOpt.get();
            pedidoExistente.setEstadoPedido(nuevoEstado);
            return Optional.of(pedidoRepository.save(pedidoExistente));
        }
        return Optional.empty();
    }

    @Override
    public List<Pedidos> getPedidossByUsuario(Usuarios usuario) {
        return List.of();
    }

    @Override
    public List<Pedidos> getPedidossByEstado(EstadoPedido estado) {
        return List.of();
    }

    @Override
    public void deletePedidoById(Long id) {
        pedidoRepository.deleteById(id);
    }

}
