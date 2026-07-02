public class Dulce {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    private int cantidadVendida;

    public Dulce(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.cantidadVendida = 0;
    }

    public boolean hayStock() {
        return stock > 0;
    }

    public void vender() {
        if (hayStock()) {
            stock--;
            cantidadVendida++;
        }
    }

    public void reponerStock(int cantidad) {
        this.stock += cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f (Stock: %d)", 
            codigo, nombre, precio, stock);
    }
}
