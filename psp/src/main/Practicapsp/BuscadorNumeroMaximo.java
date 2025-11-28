import java.io.*;
import java.util.*;

public class BuscadorNumeroMaximo {
    
    public static String encontrarNumeroMasGrande() {
        Scanner scanner = new Scanner(System.in);
        String directorio;
        
        while (true) {
            System.out.print("Introduce la ruta del directorio: ");
            directorio = scanner.nextLine();
            
            File dir = new File(directorio);
            
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("ERROR: El directorio no existe. Inténtalo de nuevo.");
            } else {
                // Si el directorio existe, realizar la búsqueda
                try {
                    return buscarNumeroMaximoEnDirectorios(dir);
                } catch (IOException e) {
                    System.out.println("Error al leer los archivos: " + e.getMessage());
                    return "Error: " + e.getMessage();
                }
            }
        }
    }
    
    private static String buscarNumeroMaximoEnDirectorios(File directorio) throws IOException {
        int numeroMaximo = Integer.MIN_VALUE;
        String archivoConMaximo = "";
        
        // Buscar recursivamente en todos los archivos
        ResultadoBusqueda resultado = buscarRecursivamente(directorio, numeroMaximo, archivoConMaximo);
        
        if (resultado.archivoConMaximo.isEmpty()) {
            return "No se encontraron archivos .dat";
        }
        
        return "Número más alto: " + resultado.numeroMaximo + " encontrado en: " + resultado.archivoConMaximo;
    }
    
    private static ResultadoBusqueda buscarRecursivamente(File directorio, int numeroMaximoActual, String archivoActual) throws IOException {
        int numeroMaximo = numeroMaximoActual;
        String archivoConMaximo = archivoActual;
        
        // Obtener todos los archivos y directorios
        File[] elementos = directorio.listFiles();
        
        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    // Si es directorio, buscar recursivamente
                    ResultadoBusqueda resultadoSubdir = buscarRecursivamente(elemento, numeroMaximo, archivoConMaximo);
                    if (resultadoSubdir.numeroMaximo > numeroMaximo) {
                        numeroMaximo = resultadoSubdir.numeroMaximo;
                        archivoConMaximo = resultadoSubdir.archivoConMaximo;
                    }
                } else {
                    // Si es archivo, verificar si es .dat
                    String nombreArchivo = elemento.getName().toLowerCase();
                    if (nombreArchivo.endsWith(".dat")) {
                        ResultadoBusqueda resultadoArchivo = procesarArchivo(elemento, numeroMaximo, archivoConMaximo);
                        if (resultadoArchivo.numeroMaximo > numeroMaximo) {
                            numeroMaximo = resultadoArchivo.numeroMaximo;
                            archivoConMaximo = resultadoArchivo.archivoConMaximo;
                        }
                    }
                }
            }
        }
        
        return new ResultadoBusqueda(numeroMaximo, archivoConMaximo);
    }
    
    private static ResultadoBusqueda procesarArchivo(File archivo, int numeroMaximoActual, String archivoActual) throws IOException {
        int numeroMaximo = numeroMaximoActual;
        String archivoConMaximo = archivoActual;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] numeros = linea.trim().split("\\s+");
                
                for (String numStr : numeros) {
                    if (!numStr.isEmpty()) {
                        try {
                            int numero = Integer.parseInt(numStr);
                            if (numero > numeroMaximo) {
                                numeroMaximo = numero;
                                archivoConMaximo = archivo.getName();
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Número inválido en " + archivo.getName() + ": " + numStr);
                        }
                    }
                }
            }
        }
        
        return new ResultadoBusqueda(numeroMaximo, archivoConMaximo);
    }
    
    // Clase auxiliar para almacenar resultados
    private static class ResultadoBusqueda {
        int numeroMaximo;
        String archivoConMaximo;
        
        ResultadoBusqueda(int numeroMaximo, String archivoConMaximo) {
            this.numeroMaximo = numeroMaximo;
            this.archivoConMaximo = archivoConMaximo;
        }
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        // Crear archivos de prueba
        crearArchivosPrueba();
        
        // Probar el método
        System.out.println("=== BUSCADOR DE NÚMERO MÁS GRANDE ===");
        String resultado = encontrarNumeroMasGrande();
        System.out.println(resultado);
    }
    
    private static void crearArchivosPrueba() {
        // Crear directorio de prueba
        new File("test_data/subdir1/subdir2").mkdirs();
        
        // Crear archivos .dat con números
        crearArchivo("test_data/archivo1.dat", "10 25 8 100 45");
        crearArchivo("test_data/archivo2.dat", "5 15 200 30 12");
        crearArchivo("test_data/subdir1/archivo3.dat", "50 75 300 20 5");
        crearArchivo("test_data/subdir1/subdir2/archivo4.dat", "150 80 90 110 120");
        crearArchivo("test_data/archivo5.dat", "500 60 70 250 180");
    }
    
    private static void crearArchivo(String nombre, String contenido) {
        try (PrintWriter pw = new PrintWriter(nombre)) {
            pw.println(contenido);
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear archivo: " + nombre);
        }
    }
}

import java.io.*;
import java.util.*;

public class BuscadorNumeroMaximo {
    
    public static String encontrarNumeroMasGrande() {
        Scanner scanner = new Scanner(System.in);
        String directorio;
        
        while (true) {
            System.out.print("Introduce la ruta del directorio: ");
            directorio = scanner.nextLine();
            
            File dir = new File(directorio);
            
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("ERROR: El directorio no existe. Inténtalo de nuevo.");
            } else {
                // Si el directorio existe, realizar la búsqueda
                try {
                    return buscarNumeroMaximoEnDirectorios(dir);
                } catch (IOException e) {
                    System.out.println("Error al leer los archivos: " + e.getMessage());
                    return "Error: " + e.getMessage();
                }
            }
        }
    }
    
    private static String buscarNumeroMaximoEnDirectorios(File directorio) throws IOException {
        int numeroMaximo = Integer.MIN_VALUE;
        String archivoConMaximo = "";
        
        // Buscar recursivamente en todos los archivos
        ResultadoBusqueda resultado = buscarRecursivamente(directorio, numeroMaximo, archivoConMaximo);
        
        if (resultado.archivoConMaximo.isEmpty()) {
            return "No se encontraron archivos .dat";
        }
        
        return "Número más alto: " + resultado.numeroMaximo + " encontrado en: " + resultado.archivoConMaximo;
    }
    
    private static ResultadoBusqueda buscarRecursivamente(File directorio, int numeroMaximoActual, String archivoActual) throws IOException {
        int numeroMaximo = numeroMaximoActual;
        String archivoConMaximo = archivoActual;
        
        // Obtener todos los archivos y directorios
        File[] elementos = directorio.listFiles();
        
        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    // Si es directorio, buscar recursivamente
                    ResultadoBusqueda resultadoSubdir = buscarRecursivamente(elemento, numeroMaximo, archivoConMaximo);
                    if (resultadoSubdir.numeroMaximo > numeroMaximo) {
                        numeroMaximo = resultadoSubdir.numeroMaximo;
                        archivoConMaximo = resultadoSubdir.archivoConMaximo;
                    }
                } else {
                    // Si es archivo, verificar si es .dat
                    String nombreArchivo = elemento.getName().toLowerCase();
                    if (nombreArchivo.endsWith(".dat")) {
                        ResultadoBusqueda resultadoArchivo = procesarArchivo(elemento, numeroMaximo, archivoConMaximo);
                        if (resultadoArchivo.numeroMaximo > numeroMaximo) {
                            numeroMaximo = resultadoArchivo.numeroMaximo;
                            archivoConMaximo = resultadoArchivo.archivoConMaximo;
                        }
                    }
                }
            }
        }
        
        return new ResultadoBusqueda(numeroMaximo, archivoConMaximo);
    }
    
    private static ResultadoBusqueda procesarArchivo(File archivo, int numeroMaximoActual, String archivoActual) throws IOException {
        int numeroMaximo = numeroMaximoActual;
        String archivoConMaximo = archivoActual;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] numeros = linea.trim().split("\\s+");
                
                for (String numStr : numeros) {
                    if (!numStr.isEmpty()) {
                        try {
                            int numero = Integer.parseInt(numStr);
                            if (numero > numeroMaximo) {
                                numeroMaximo = numero;
                                archivoConMaximo = archivo.getName();
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Número inválido en " + archivo.getName() + ": " + numStr);
                        }
                    }
                }
            }
        }
        
        return new ResultadoBusqueda(numeroMaximo, archivoConMaximo);
    }
    
    // Clase auxiliar para almacenar resultados
    private static class ResultadoBusqueda {
        int numeroMaximo;
        String archivoConMaximo;
        
        ResultadoBusqueda(int numeroMaximo, String archivoConMaximo) {
            this.numeroMaximo = numeroMaximo;
            this.archivoConMaximo = archivoConMaximo;
        }
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        // Crear archivos de prueba
        crearArchivosPrueba();
        
        // Probar el método
        System.out.println("=== BUSCADOR DE NÚMERO MÁS GRANDE ===");
        String resultado = encontrarNumeroMasGrande();
        System.out.println(resultado);
    }
    
    private static void crearArchivosPrueba() {
        // Crear directorio de prueba
        new File("test_data/subdir1/subdir2").mkdirs();
        
        // Crear archivos .dat con números
        crearArchivo("test_data/archivo1.dat", "10 25 8 100 45");
        crearArchivo("test_data/archivo2.dat", "5 15 200 30 12");
        crearArchivo("test_data/subdir1/archivo3.dat", "50 75 300 20 5");
        crearArchivo("test_data/subdir1/subdir2/archivo4.dat", "150 80 90 110 120");
        crearArchivo("test_data/archivo5.dat", "500 60 70 250 180");
    }
    
    private static void crearArchivo(String nombre, String contenido) {
        try (PrintWriter pw = new PrintWriter(nombre)) {
            pw.println(contenido);
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear archivo: " + nombre);
        }
    }
}


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

public class GestorPeliculas {
    private Document doc;
    private String archivoXML;
    
    public GestorPeliculas(String archivoXML) throws Exception {
        this.archivoXML = archivoXML;
        cargarDocumento();
    }
    
    private void cargarDocumento() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new File(archivoXML));
    }
    
    private void guardarDocumento() throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(archivoXML));
        transformer.transform(source, result);
    }
    
    // 2.1 Aumentar presupuesto de películas de un director
    public void aumentarRecaudacionDirector(String director, double porcentaje) throws Exception {
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            Element directorElem = (Element) pelicula.getElementsByTagName("director").item(0);
            
            if (directorElem.getTextContent().equals(director)) {
                Element recaudacionElem = (Element) pelicula.getElementsByTagName("recaudacion").item(0);
                long recaudacionActual = Long.parseLong(recaudacionElem.getTextContent());
                long nuevaRecaudacion = (long) (recaudacionActual * (1 + porcentaje / 100));
                recaudacionElem.setTextContent(String.valueOf(nuevaRecaudacion));
            }
        }
        guardarDocumento();
    }
    
    // 2.2 Añadir nueva película
    public void añadirPelicula(int id, String titulo, String director, int anno, 
                              String genero, long recaudacion) throws Exception {
        Element peliculas = (Element) doc.getElementsByTagName("peliculas").item(0);
        
        Element nuevaPelicula = doc.createElement("pelicula");
        nuevaPelicula.setAttribute("id", String.valueOf(id));
        
        añadirElemento(nuevaPelicula, "titulo", titulo);
        añadirElemento(nuevaPelicula, "director", director);
        añadirElemento(nuevaPelicula, "anno_estreno", String.valueOf(anno));
        añadirElemento(nuevaPelicula, "genero", genero);
        añadirElemento(nuevaPelicula, "recaudacion", String.valueOf(recaudacion));
        
        peliculas.appendChild(nuevaPelicula);
        guardarDocumento();
    }
    
    private void añadirElemento(Element padre, String nombre, String valor) {
        Element elemento = doc.createElement(nombre);
        elemento.setTextContent(valor);
        padre.appendChild(elemento);
    }
    
    // 2.3.a Películas estrenadas antes de un año (XPath)
    public void mostrarPeliculasAntesDe(int añoLimite) throws Exception {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        
        String expression = String.format(
            "//pelicula[anno_estreno < %d]", añoLimite);
        
        NodeList peliculas = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("Películas estrenadas antes de " + añoLimite + ":");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            String titulo = pelicula.getElementsByTagName("titulo").item(0).getTextContent();
            String director = pelicula.getElementsByTagName("director").item(0).getTextContent();
            String anno = pelicula.getElementsByTagName("anno_estreno").item(0).getTextContent();
            
            System.out.printf("Título: %s, Director: %s, Año: %s%n", titulo, director, anno);
        }
    }
    
    // 2.3.b Películas con recaudación superior a un valor (XPath)
    public void mostrarPeliculasRecaudacionSuperior(long recaudacionMinima) throws Exception {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        
        String expression = String.format(
            "//pelicula[recaudacion > %d]", recaudacionMinima);
        
        NodeList peliculas = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("Películas con recaudación superior a " + recaudacionMinima + ":");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            String titulo = pelicula.getElementsByTagName("titulo").item(0).getTextContent();
            String recaudacion = pelicula.getElementsByTagName("recaudacion").item(0).getTextContent();
            
            System.out.printf("Título: %s, Recaudación: %s%n", titulo, recaudacion);
        }
    }
}

public class PruebaGestorPeliculas {
    public static void main(String[] args) {
        try {
            GestorPeliculas gestor = new GestorPeliculas("coleccion_peliculas.xml");
            
            // Probar 2.1 - Aumentar recaudación de un director
            System.out.println("=== Aumentando recaudación de Steven Spielberg en 10% ===");
            gestor.aumentarRecaudacionDirector("Steven Spielberg", 10);
            
            // Probar 2.2 - Añadir nueva película
            System.out.println("=== Añadiendo nueva película ===");
            gestor.añadirPelicula(9, "El Señor de los Anillos", "Peter Jackson", 2001, 
                                "Fantasía", 871000000L);
            
            // Probar 2.3.a - Películas antes de 1970
            System.out.println("=== Películas antes de 1970 ===");
            gestor.mostrarPeliculasAntesDe(1970);
            
            // Probar 2.3.b - Películas con recaudación > 100,000,000
            System.out.println("=== Películas con recaudación > 100,000,000 ===");
            gestor.mostrarPeliculasRecaudacionSuperior(100000000);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}