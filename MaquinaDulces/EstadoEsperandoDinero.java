public class EstadoEsperandoDinero implements EstadoMaquina {
    private MaquinaExpendedora maquina;

    public EstadoEsperandoDinero(MaquinaExpendedora maquina) {
        this.maquina = maquina;
    }

    @Override
    public void seleccionarProducto(String codigo) {
        System.out.println("Ya tienes un producto seleccionado. Completa el pago o cancela.");
    }

    @Override
    public void ingresarDinero(double cantidad) {
        // Delegamos al sistema de pago que ya tienes
        maquina.getSistemaPago().ingresarDinero(cantidad);
    }

    @Override
    public void confirmarCompra() {
        Dulce dulce = maquina.getProductoSeleccionado();
        SistemaPago pago = maquina.getSistemaPago();

        if (pago.tieneFondosSuficientes(dulce.getPrecio())) {
            maquina.entregarProducto(); // La máquina hace el proceso final
            maquina.setEstado(maquina.getEstadoEsperandoSeleccion()); // Volvemos al inicio
        } else {
            System.out.printf("Fondos insuficientes. Faltan $%.2f\n", dulce.getPrecio() - pago.getDineroIngresado());
        }
    }

    @Override
    public void cancelarTransaccion() {
        System.out.println("Compra cancelada.");
        maquina.getSistemaPago().devolverDinero();
        maquina.getSistemaPago().reiniciar();
        maquina.setProductoSeleccionado(null);
        maquina.setEstado(maquina.getEstadoEsperandoSeleccion()); // Volvemos al inicio
    }
}
