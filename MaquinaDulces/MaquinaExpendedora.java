import java.util.ArrayList;
import java.util.List;

public class MaquinaExpendedora {
    private Inventario inventario;
    private SistemaPago sistemaPago;
    private RegistroVentas registroVentas;
    
    // 1. Crea la lista de suscriptores (observadores)
    private List<ObservadorVenta> observadores;

    public MaquinaExpendedora() {
        this.inventario = new Inventario();
        this.sistemaPago = new SistemaPago();
        this.registroVentas = new RegistroVentas();

        // 2. Inicializamos la lista y suscribimos al registro de ventas
        this.observadores = new ArrayList<>();
        this.agregarObservador(this.registroVentas);
    }

    // 3. Método para que cualquiera pueda suscribirse
    public void agregarObservador(ObservadorVenta observador) {
        observadores.add(observador);
    }

    // 4. Método privado que "grita" a todos los suscriptores
    private void notificarVenta(Dulce dulce) {
        for (ObservadorVenta obs : observadores) {
            obs.onVentaRealizada(dulce); // Se ejecuta el método del Paso 2
        }
    }

    public void mostrarDulcesDisponibles() {
        inventario.mostrarInventario();
    }

    public void ingresarDinero(double cantidad) {
        sistemaPago.ingresarDinero(cantidad);
    }

    public boolean validarCodigo(String codigo) {
        return inventario.existeDulce(codigo);
    }

    public void mostrarDenominacionesAceptadas() {
        sistemaPago.mostrarDenominacionesAceptadas();
    }

    public boolean comprarDulce(String codigo) {
        // Ya no necesitamos validar el código aquí porque se validó en Main
        Dulce dulce = inventario.obtenerDulce(codigo);

        // Verificar stock
        if (!dulce.hayStock()) {
            System.out.println("Lo sentimos, " + dulce.getNombre() + " esta agotado.");
            return false;
        }

        // Verificar fondos
        if (!sistemaPago.tieneFondosSuficientes(dulce.getPrecio())) {
            System.out.printf("Fondos insuficientes. Necesitas $%.2f mas.\n", 
                dulce.getPrecio() - sistemaPago.getDineroIngresado());
            return false;
        }

       // Procesar compra
        sistemaPago.procesarPago(dulce.getPrecio());
        dulce.vender();
        
        // 5. EL CAMBIO FINAL: Quitamos "registroVentas.registrarVenta(dulce);"
        // Y en su lugar, simplemente notificamos:
        notificarVenta(dulce);

        // Calcular cambio
        double cambio = sistemaPago.getDineroIngresado();
        
        System.out.println("\nCompra exitosa!");
        System.out.println("Producto: " + dulce.getNombre());
        System.out.printf("Precio: $%.2f\n", dulce.getPrecio());
        
        if (cambio > 0) {
            System.out.printf("Cambio: $%.2f\n", cambio);
        }
        
        System.out.println("Disfruta tu " + dulce.getNombre() + "!");
        
        sistemaPago.reiniciar();
        return true;
    }

    public void cancelarTransaccion() {
        sistemaPago.devolverDinero();
        sistemaPago.reiniciar();
    }

    public void mostrarReporteVentas() {
        registroVentas.mostrarResumenVentas(inventario);
    }

    public double consultarDineroIngresado() {
        return sistemaPago.getDineroIngresado();
    }
}