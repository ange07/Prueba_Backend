package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.FacturaRequest;
import org.generation.muebleria.model.Facturas;

import java.util.List;

/**
 * Interface para el servicio de gestión de Facturas.
 * Define los métodos de negocio para el módulo de facturación.
 */
public interface IFacturasService {

    /**
     * Crea una nueva factura a partir de un pedido.
     * El cliente solicita factura para un pedido.
     * Calcula automáticamente: subtotal, IVA (16%) y total.
     *
     * @param request DTO con idPedido, RFC y Razón Social
     * @return Factura creada
     * @throws IllegalArgumentException si el pedido no existe o ya tiene factura
     */
    Facturas crearFactura(FacturaRequest request);

    /**
     * Obtiene todas las facturas de un usuario específico.
     * Permite al cliente ver sus propias facturas en su perfil.
     *
     * @param idUsuario ID del usuario
     * @return Lista de facturas del usuario
     */
    List<Facturas> obtenerFacturasPorUsuario(Long idUsuario);

    /**
     * Obtiene una factura asociada a un pedido específico.
     *
     * @param idPedido ID del pedido
     * @return Factura del pedido
     * @throws IllegalArgumentException si no existe factura para ese pedido
     */
    Facturas obtenerFacturaPorPedido(Long idPedido);

    /**
     * Obtiene todas las facturas del sistema.
     * Para uso administrativo.
     *
     * @return Lista de todas las facturas
     */
    List<Facturas> obtenerTodasLasFacturas();
}
