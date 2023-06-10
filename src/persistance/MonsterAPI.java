package persistance;

import business.entities.Monster;
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

public class MonsterAPI {
    private final HttpClient client;

    /**
     * Constructor predeterminado, donde se configura el cliente utilizado para la comunicación HTTPS
     *
     * @throws IOException si su computadora no admite SSL en absoluto. Si obtiene esta excepción al llamar al
     * constructor, contactar con los profesores de la OOPD.
     */
    public MonsterAPI() throws IOException {
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
     * @throws IOException si la URL tiene un formato incorrecto o no se puede acceder al servidor.
     */
    public ArrayList<Monster> getFromUrl(String url) throws IOException {

        Monster[] monsters;
        ArrayList<Monster> monstersList;
        try {
            // Definir la solicitud
            // El método predeterminado es GET, por lo que no necesitamos especificarlo (pero podemos hacerlo llamando a .GET() antes de .build()
            // El patrón HttpRequest.Builder ofrece mucha personalización para la solicitud (encabezados, cuerpo, versión HTTP...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

            // Usamos el BodyHandler predeterminado para cadenas (para que podamos obtener el cuerpo de la respuesta como una cadena)
            // Tenga en cuenta que también podríamos enviar la solicitud de forma asíncrona, pero las cosas aumentarían en términos de complejidad de codificación
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Solo devuelve el cuerpo
            Gson g = new Gson();
            monsters = g.fromJson(response.body(), Monster[].class);
            monstersList = new ArrayList<>(Arrays.asList(monsters));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            monstersList = null;
            // Las excepciones se simplifican para cualquier clase que necesite atraparlas
            //throw new IOException(e);
        }

        return monstersList;

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
