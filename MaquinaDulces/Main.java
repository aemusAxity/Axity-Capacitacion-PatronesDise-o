import java.util.Scanner;

public class Main {
    private static final String CLAVE_ADMIN = "admin123";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        MaquinaExpendedora maquina = new MaquinaExpendedora();
        
        mostrarBienvenida();
        
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion("Selecciona una opcion: ", 1, 4);
            
            switch (opcion) {
                case 1:
                    comprarDulce(maquina);
                    break;
                case 2:
                    maquina.mostrarDulcesDisponibles();
                    break;
                case 3:
                    menuAdministrador(maquina);
                    break;
                case 4:
                    continuar = false;
                    System.out.println("\nGracias por usar la maquina expendedora!");
                    break;
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarBienvenida() {
        System.out.println("\n==========================================");
        System.out.println("  BIENVENIDO A LA MAQUINA EXPENDEDORA");
        System.out.println("==========================================");
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n------------------------------------------");
        System.out.println(" MENU PRINCIPAL");
        System.out.println("------------------------------------------");
        System.out.println(" 1. Comprar dulce");
        System.out.println(" 2. Ver productos disponibles");
        System.out.println(" 3. Menu administrador");
        System.out.println(" 4. Salir");
        System.out.println("------------------------------------------");
    }
    
    private static void comprarDulce(MaquinaExpendedora maquina) {
        maquina.mostrarDulcesDisponibles();
        
        System.out.print("\nIngresa el codigo del producto: ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        
        // VALIDACIÓN DEL CÓDIGO ANTES DE INICIAR EL PAGO
        if (!maquina.validarCodigo(codigo)) {
            System.out.println("Codigo invalido. Producto no encontrado.");
            return;
        }
        
        System.out.println("\n--- SISTEMA DE PAGO ---");
        maquina.mostrarDenominacionesAceptadas();
        
        boolean continuarPago = true;
        
        while (continuarPago) {
            System.out.println("\n1. Ingresar moneda/billete");
            System.out.println("2. Confirmar compra");
            System.out.println("3. Cancelar");
            
            int opcion = leerOpcion("Opcion: ", 1, 3);
            
            switch (opcion) {
                case 1:
                    double cantidad = leerDouble("Ingresa la denominacion (ej: 0.25, 1, 5, 10): $");
                    maquina.ingresarDinero(cantidad);
                    break;
                case 2:
                    if (maquina.comprarDulce(codigo)) {
                        continuarPago = false;
                    } else {
                        System.out.print("\nDeseas seguir intentando? (S/N): ");
                        String respuesta = scanner.nextLine().trim().toUpperCase();
                        if (!respuesta.equals("S")) {
                            maquina.cancelarTransaccion();
                            continuarPago = false;
                        }
                    }
                    break;
                case 3:
                    maquina.cancelarTransaccion();
                    System.out.println("Compra cancelada.");
                    continuarPago = false;
                    break;
            }
        }
    }
    
    private static void menuAdministrador(MaquinaExpendedora maquina) {
        System.out.print("\nIngresa la clave de administrador: ");
        String clave = scanner.nextLine().trim();
        
        if (!clave.equals(CLAVE_ADMIN)) {
            System.out.println("Clave incorrecta.");
            return;
        }
        
        System.out.println("Acceso concedido");
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n------------------------------------------");
            System.out.println(" MENU ADMINISTRADOR");
            System.out.println("------------------------------------------");
            System.out.println(" 1. Ver reporte de ventas");
            System.out.println(" 2. Ver inventario");
            System.out.println(" 3. Volver al menu principal");
            System.out.println("------------------------------------------");
            
            int opcion = leerOpcion("Opcion: ", 1, 3);
            
            switch (opcion) {
                case 1:
                    maquina.mostrarReporteVentas();
                    break;
                case 2:
                    maquina.mostrarDulcesDisponibles();
                    break;
                case 3:
                    continuar = false;
                    break;
            }
        }
    }
    
    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingresa un numero valido.");
            }
        }
    }

    private static int leerOpcion(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                int opcion = Integer.parseInt(input);
                
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.printf("Opcion invalida. Elige entre %d y %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingresa un numero.");
            }
        }
    }
}