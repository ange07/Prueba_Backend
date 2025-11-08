package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.FacturaRequest;
import org.generation.muebleria.model.Facturas;
import org.generation.muebleria.service.interfaces.IFacturasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de facturas.
 * Provee endpoints para clientes y administradores.
 */
@RestController
@RequestMapping("/api/facturas")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class FacturasController {

    private final IFacturasService facturasService;

    // ========================================
    // ENDPOINTS PARA CLIENTES
    // ========================================

    /**
     * Solicitar factura para un pedido.
     * POST /api/facturas/solicitar
     *
     * @param request DTO con idPedido, RFC y Razón Social
     * @return Factura creada
     */
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarFactura(@RequestBody FacturaRequest request) {
        try {
            Facturas factura = facturasService.crearFactura(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtener facturas de un usuario específico.
     * GET /api/facturas/usuario/{idUsuario}
     *
     * @param idUsuario ID del usuario
     * @return Lista de facturas del usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Facturas>> obtenerFacturasPorUsuario(@PathVariable Long idUsuario) {
        List<Facturas> facturas = facturasService.obtenerFacturasPorUsuario(idUsuario);
        return ResponseEntity.ok(facturas);
    }

    /**
     * Obtener factura de un pedido específico.
     * GET /api/facturas/pedido/{idPedido}
     *
     * @param idPedido ID del pedido
     * @return Factura asociada al pedido
     */
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<?> obtenerFacturaPorPedido(@PathVariable Long idPedido) {
        try {
            Facturas factura = facturasService.obtenerFacturaPorPedido(idPedido);
            return ResponseEntity.ok(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ========================================
    // ENDPOINTS PARA ADMINISTRADORES
    // ========================================

    /**
     * Obtener todas las facturas del sistema.
     * GET /api/facturas/todas
     *
     * @return Lista de todas las facturas
     */
    @GetMapping("/todas")
    public ResponseEntity<List<Facturas>> obtenerTodasLasFacturas() {
        List<Facturas> facturas = facturasService.obtenerTodasLasFacturas();
        return ResponseEntity.ok(facturas);
    }
}
