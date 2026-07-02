public class SistemaPago {
    private double dineroIngresado;
    
    // Denominaciones válidas
    private static final double[] MONEDAS = {0.05, 0.10, 0.25, 0.50, 1.00};
    private static final double[] BILLETES = {1.00, 5.00, 10.00, 20.00, 50.00, 100.00};

    public SistemaPago() {
        this.dineroIngresado = 0.0;
    }

    public void ingresarDinero(double cantidad) {
        if (!esValorValido(cantidad)) {
            System.out.println("Denominacion no valida.");
            System.out.println("Monedas aceptadas: $0.05, $0.10, $0.25, $0.50, $1.00");
            System.out.println("Billetes aceptados: $1, $5, $10, $20, $50, $100");
            return;
        }
        
        if (cantidad > 0) {
            dineroIngresado += cantidad;
            String tipo = cantidad < 1.00 ? "Moneda" : "Billete";
            System.out.printf("%s de $%.2f aceptado\n", tipo, cantidad);
            System.out.printf("Total acumulado: $%.2f\n", dineroIngresado);
        }
    }
    
    private boolean esValorValido(double cantidad) {
        // Verificar si es una moneda válida
        for (double moneda : MONEDAS) {
            if (Math.abs(cantidad - moneda) < 0.001) { // Comparación con tolerancia para decimales
                return true;
            }
        }
        
        // Verificar si es un billete válido
        for (double billete : BILLETES) {
            if (Math.abs(cantidad - billete) < 0.001) {
                return true;
            }
        }
        
        return false;
    }
    
    public void mostrarDenominacionesAceptadas() {
        System.out.println("\n--- DENOMINACIONES ACEPTADAS ---");
        System.out.println("Monedas: $0.05, $0.10, $0.25, $0.50, $1.00");
        System.out.println("Billetes: $1, $5, $10, $20, $50, $100");
    }

    public boolean tieneFondosSuficientes(double precio) {
        return dineroIngresado >= precio;
    }

    public double calcularCambio(double precio) {
        return dineroIngresado - precio;
    }

    public void procesarPago(double precio) {
        dineroIngresado -= precio;
    }

    public void devolverDinero() {
        if (dineroIngresado > 0) {
            System.out.printf("Devolviendo: $%.2f\n", dineroIngresado);
            dineroIngresado = 0.0;
        }
    }

    public void reiniciar() {
        dineroIngresado = 0.0;
    }

    public double getDineroIngresado() {
        return dineroIngresado;
    }
}