package maquina.PatronObserver;
import maquina.Dulce;

public interface ObservadorVenta {
    // Método que se ejecutará automáticamente cuando ocurra una venta
    void onVentaRealizada(Dulce dulce);
}