public interface EstadoMaquina {
    void seleccionarProducto(String codigo);
    void ingresarDinero(double cantidad);
    void confirmarCompra();
    void cancelarTransaccion();
}
