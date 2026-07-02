public class EstadoEsperandoSeleccion implements EstadoMaquina {
    private MaquinaExpendedora maquina;

    public EstadoEsperandoSeleccion(MaquinaExpendedora maquina) {
        this.maquina = maquina;
    }

    @Override
    public void seleccionarProducto(String codigo) {
        if (!maquina.getInventario().existeDulce(codigo)) {
            System.out.println("Codigo invalido. Producto no encontrado.");
            return;
        }
        
        Dulce dulce = maquina.getInventario().obtenerDulce(codigo);
        if (!dulce.hayStock()) {
            System.out.println("Lo sentimos, " + dulce.getNombre() + " esta agotado.");
            return;
        }

        // Si todo está bien, guardamos el dulce y cambiamos de estado
        maquina.setProductoSeleccionado(dulce);
        System.out.println("Has seleccionado: " + dulce.getNombre() + " - Precio: $" + dulce.getPrecio());
        maquina.setEstado(maquina.getEstadoEsperandoDinero());
    }

    @Override
    public void ingresarDinero(double cantidad) {
        System.out.println("Por favor, selecciona un producto primero.");
    }

    @Override
    public void confirmarCompra() {
        System.out.println("Por favor, selecciona un producto primero.");
    }

    @Override
    public void cancelarTransaccion() {
        System.out.println("No hay ninguna transaccion en curso.");
    }
}