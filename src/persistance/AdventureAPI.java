package persistance;

import business.entities.Adventure;
import business.entities.Character;
import com.google.gson.Gson;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase AdventureAPI, la cual leerá toda la info de la API de adventure
 */
public class AdventureAPI {

    private final HttpClient client;

    /**
     * Constructor predeterminado, donde se configura el cliente utilizado para la comunicación HTTPS
     *
     * @throws IOException si su computadora no admite SSL en absoluto. Si obtiene esta excepción al llamar al
     * constructor, contactar con los profesores de la OOPD.
     */
    public AdventureAPI() throws IOException {
        // Configuramos el HTTPClient que (re)utilizaremos en todas las solicitudes, con un contexto *INSEGURE* SSL personalizado
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            // Las excepciones se simplifican para cualquier clase que necesite atraparlas
            throw new IOException(e);
        }
    }


    /**
     * Método que lee el contenido de una URL utilizando el protocolo HTTPS. Específicamente, se envía una solicitud GET.
     * Todos los parámetros deben incluirse en la URL.
     *
     * @param url Una representación de cadena de la URL para leer, que se supondrá que utiliza HTTP/HTTPS.
     * @return El contenido de la URL representada como texto.
     */
    public ArrayList<Adventure> getFromUrl(String url){
        Adventure[] adventures;
        ArrayList<Adventure> adventureList;

        try {
            // Definir la solicitud
            // El método predeterminado es GET, por lo que no necesitamos especificarlo (pero podemos hacerlo llamando a .GET() antes de .build()
            // El patrón HttpRequest.Builder ofrece mucha personalización para la solicitud (encabezados, cuerpo, versión HTTP...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

            // Usamos el BodyHandler predeterminado para cadenas (para que podamos obtener el cuerpo de la respuesta como una cadena)
            // Tenga en cuenta que también podríamos enviar la solicitud de forma asíncrona, pero las cosas aumentarían en términos de complejidad de codificación
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson g = new Gson();
            adventures = g.fromJson(response.body(), Adventure[].class);
            adventureList =  new ArrayList<>(Arrays.asList(adventures));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Las excepciones se simplifican para cualquier clase que necesite atraparlas
            adventureList = null;
        }
        return adventureList;
    }


    /**
     * Método que publica contenidos en una URL utilizando el protocolo HTTPS. Específicamente, se envía una solicitud POST.
     * El cuerpo de la solicitud se establece en el parámetro correspondiente y el cuerpo de la respuesta se devuelve por si acaso.
     *
     * @param url Una representación de cadena de la URL para publicar, que se supondrá que utiliza HTTP/HTTPS.
     * @param adventure El contenido a publicar, que se enviará al servidor en el cuerpo de la solicitud.
     * @return El contenido de la respuesta, en caso de que el servidor devuelva algo después de publicar el contenido.
     * @throws IOException si la URL tiene un formato incorrecto o no se puede acceder al servidor.
     */
    public Boolean postToUrl(String url, Adventure adventure) throws IOException {
        Gson g = new Gson();
        String adventureString = g.toJson(adventure);

        try {
            // Definir la solicitud
            // En este caso, tenemos que usar los métodos .POST() y .headers() para definir lo que queremos (enviar una cadena que contenga datos JSON)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).headers("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(adventureString)).build();

            // Podríamos usar un BodyHandler que descarta el cuerpo de la respuesta, pero aquí devolvemos la respuesta de la API
            // Tenga en cuenta que también podríamos enviar la solicitud de forma asíncrona, pero las cosas aumentarían en términos de complejidad de codificación
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
            return true;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Las excepciones se simplifican para cualquier clase que necesite atraparlas
            throw new IOException(e);
        }
    }


    /**
     * Función de ayuda que configura un SSLContext diseñado para ignorar certificados, aceptando cualquier cosa por defecto
     * NO UTILIZARSE EN ENTORNOS REALES DE PRODUCCIÓN
     *
     * @return Una instancia de la clase SSLContext, que gestiona las verificaciones SSL, configurada para aceptar incluso certificados mal configurados
     */
    private SSLContext insecureContext() throws NoSuchAlgorithmException, KeyManagementException {
        // Configuramos un TrustManager que acepta todos los certificados por defecto
        TrustManager[] insecureTrustManager = new TrustManager[]{new X509TrustManager() {
            // Al no lanzar ninguna excepción en estos métodos, estamos aceptando todo
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            // Esto no afecta nuestro caso de uso, por lo que solo devolvemos una matriz vacía
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // Configuramos el SSLContext con el TrustManager que acepta en exceso
        SSLContext sc = SSLContext.getInstance("ssl");
        sc.init(null, insecureTrustManager, null);
        return sc;
    }
}
