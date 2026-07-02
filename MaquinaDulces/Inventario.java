import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<String, Dulce> dulces;

    public Inventario() {
        this.dulces = new HashMap<>();
        inicializarInventario();
    }

    private void inicializarInventario() {
        agregarDulce(new Dulce("A1", "Chocolate", 1.50, 10));
        agregarDulce(new Dulce("A2", "Gomitas", 1.00, 15));
        agregarDulce(new Dulce("A3", "Chicles Trident", 0.75, 20));
        agregarDulce(new Dulce("B1", "Galletas Oreo", 2.00, 8));
        agregarDulce(new Dulce("B2", "M&M's", 1.25, 12));
        agregarDulce(new Dulce("B3", "Snickers", 1.75, 10));
        agregarDulce(new Dulce("C1", "Sabritas Naturales", 2.50, 2));
        agregarDulce(new Dulce("C2", "Doritos", 2.25, 9));
    }

    public void agregarDulce(Dulce dulce) {
        dulces.put(dulce.getCodigo(), dulce);
    }

    public Dulce obtenerDulce(String codigo) {
        return dulces.get(codigo.toUpperCase());
    }

    public Map<String, Dulce> obtenerTodosDulces() {
        return new HashMap<>(dulces);
    }

    public boolean existeDulce(String codigo) {
        return dulces.containsKey(codigo.toUpperCase());
    }

    public void mostrarInventario() {
        System.out.println("\n==========================================");
        System.out.println("        DULCES DISPONIBLES");
        System.out.println("==========================================");
        
        dulces.values().stream()
            .sorted((d1, d2) -> d1.getCodigo().compareTo(d2.getCodigo()))
            .forEach(dulce -> System.out.println(" " + dulce));
        
        System.out.println("==========================================");
    }
}
