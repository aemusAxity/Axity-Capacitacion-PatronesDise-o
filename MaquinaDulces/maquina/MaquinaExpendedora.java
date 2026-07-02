package maquina;
import java.util.ArrayList;
import java.util.List;

import maquina.PatronObserver.*;
import maquina.PatronState.*;

public class MaquinaExpendedora {
    private Inventario inventario;
    private SistemaPago sistemaPago;
    private RegistroVentas registroVentas;
    
    // VARIABLES DEL PATRÓN OBSERVER
    private List<ObservadorVenta> observadores;

    // VARIABLES DEL PATRÓN STATE
    private EstadoMaquina estadoEsperandoSeleccion;
    private EstadoMaquina estadoEsperandoDinero;
    private EstadoMaquina estadoActual;
    private Dulce productoSeleccionado; // Guarda temporalmente lo que queremos comprar

    public MaquinaExpendedora() {
        this.inventario = new Inventario();
        this.sistemaPago = new SistemaPago();
        this.registroVentas = new RegistroVentas();

        // Inicialización Observer
        this.observadores = new ArrayList<>();
        this.agregarObservador(this.registroVentas);

        // Inicialización State
        this.estadoEsperandoSeleccion = new EstadoEsperandoSeleccion(this);
        this.estadoEsperandoDinero = new EstadoEsperandoDinero(this);
        this.estadoActual = estadoEsperandoSeleccion; // Estado inicial
    }

    // --- MÉTODOS DEL PATRÓN OBSERVER (Intactos) ---
    public void agregarObservador(ObservadorVenta observador) {
        observadores.add(observador);
    }

    private void notificarVenta(Dulce dulce) {
        for (ObservadorVenta obs : observadores) {
            obs.onVentaRealizada(dulce); 
        }
    }

    // --- MÉTODOS DEL PATRÓN STATE (Delegación) ---
    public void seleccionarProducto(String codigo) {
        estadoActual.seleccionarProducto(codigo);
    }

    public void ingresarDinero(double cantidad) {
        estadoActual.ingresarDinero(cantidad);
    }

    public void confirmarCompra() {
        estadoActual.confirmarCompra();
    }

    public void cancelarTransaccion() {
        estadoActual.cancelarTransaccion();
    }

    // --- LÓGICA INTERNA DE LA MÁQUINA ---
    // Este método lo llama el EstadoEsperandoDinero cuando el pago es exitoso
    public void entregarProducto() {
        sistemaPago.procesarPago(productoSeleccionado.getPrecio());
        productoSeleccionado.vender();
        
        // ¡Notificamos al observer!
        notificarVenta(productoSeleccionado);

        double cambio = sistemaPago.getDineroIngresado();
        System.out.println("\nCompra exitosa!");
        System.out.println("Producto: " + productoSeleccionado.getNombre());
        System.out.printf("Precio: $%.2f\n", productoSeleccionado.getPrecio());
        
        if (cambio > 0) {
            System.out.printf("Cambio: $%.2f\n", cambio);
        }
        System.out.println("Disfruta tu " + productoSeleccionado.getNombre() + "!");
        
        sistemaPago.reiniciar();
        productoSeleccionado = null; // Limpiamos la memoria
    }

    // --- GETTERS Y SETTERS PARA LOS ESTADOS ---
    public void setEstado(EstadoMaquina estado) { this.estadoActual = estado; }
    public EstadoMaquina getEstadoEsperandoSeleccion() { return estadoEsperandoSeleccion; }
    public EstadoMaquina getEstadoEsperandoDinero() { return estadoEsperandoDinero; }
    
    public Inventario getInventario() { return inventario; }
    public SistemaPago getSistemaPago() { return sistemaPago; }
    
    public void setProductoSeleccionado(Dulce dulce) { this.productoSeleccionado = dulce; }
    public Dulce getProductoSeleccionado() { return productoSeleccionado; }
    
    // Método de ayuda para la interfaz del usuario en el Main
    public boolean estaEsperandoDinero() { return estadoActual == estadoEsperandoDinero; }

    // --- MÉTODOS GENERALES RESTANTES ---
    public void mostrarDulcesDisponibles() { inventario.mostrarInventario(); }
    public void mostrarDenominacionesAceptadas() { sistemaPago.mostrarDenominacionesAceptadas(); }
    public void mostrarReporteVentas() { registroVentas.mostrarResumenVentas(inventario); }
    public double consultarDineroIngresado() { return sistemaPago.getDineroIngresado(); }
}