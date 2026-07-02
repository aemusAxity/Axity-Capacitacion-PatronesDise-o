import java.util.ArrayList;
import java.util.List;

public class RegistroVentas {
    private List<Venta> ventas;
    private double totalRecaudado;

    public RegistroVentas() {
        this.ventas = new ArrayList<>();
        this.totalRecaudado = 0.0;
    }

    public void registrarVenta(Dulce dulce) {
        Venta venta = new Venta(dulce.getCodigo(), dulce.getNombre(), dulce.getPrecio());
        ventas.add(venta);
        totalRecaudado += dulce.getPrecio();
    }

    public void mostrarResumenVentas(Inventario inventario) {
        System.out.println("\n==========================================");
        System.out.println("        REPORTE DE VENTAS");
        System.out.println("==========================================");
        System.out.printf(" Total de ventas: %d\n", ventas.size());
        System.out.printf(" Total recaudado: $%.2f\n", totalRecaudado);
        System.out.println("------------------------------------------");
        System.out.println(" Detalle por producto:");
        
        inventario.obtenerTodosDulces().values().stream()
            .filter(dulce -> dulce.getCantidadVendida() > 0)
            .sorted((d1, d2) -> Integer.compare(d2.getCantidadVendida(), d1.getCantidadVendida()))
            .forEach(dulce -> {
                double totalProducto = dulce.getCantidadVendida() * dulce.getPrecio();
                System.out.printf(" %s: %d unidades - $%.2f\n", 
                    dulce.getNombre(), 
                    dulce.getCantidadVendida(), 
                    totalProducto);
            });
        
        System.out.println("==========================================");
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public int getCantidadVentas() {
        return ventas.size();
    }

    private static class Venta {
        private String codigoDulce;
        private String nombreDulce;
        private double precio;
        private long timestamp;

        public Venta(String codigoDulce, String nombreDulce, double precio) {
            this.codigoDulce = codigoDulce;
            this.nombreDulce = nombreDulce;
            this.precio = precio;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
