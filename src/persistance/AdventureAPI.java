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

public class AdventureAPI {

    private final HttpClient client;

    /**
     * Default constructor, where the client used for HTTPS communication is set up
     *
     * @throws IOException If your computer doesn't support SSL at all. If you get this exception when calling the
     *                     constructor, contact the OOPD teachers.
     */
    public AdventureAPI() throws IOException {
        // We set up the HTTPClient we will (re)use across requests, with a custom *INSECURE* SSL context
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new IOException(e);
        }
    }


    /**
     * Method that reads the contents from a URL using the HTTPS protocol. Specifically, a GET request is sent.
     * Any parameters should be included in the URL.
     *
     * @param url A String representation of the URL to read from, which will be assumed to use HTTP/HTTPS.
     * @return The contents of the URL represented as text.
     * @throws IOException If the URL is malformed or the server can't be reached.
     */
    public ArrayList<Adventure> getFromUrl(String url) throws IOException {
        Adventure[] adventures = null;
        try {
            // Define the request
            // The default method is GET, so we don't need to specify it (but we could do so by calling .GET() before .build()
            // The HttpRequest.Builder pattern offers a ton of customization for the request (headers, body, HTTP version...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

            // We use the default BodyHandler for Strings (so we can get the body of the response as a String)
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson g = new Gson();
            adventures = g.fromJson(response.body(), Adventure[].class);
            return new ArrayList<Adventure>(Arrays.asList(adventures));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new IOException(e);
        }
    }


    /**
     * Method that posts contents to a URL using the HTTPS protocol. Specifically, a POST request is sent.
     * The request body is set to the corresponding parameter, and the response body is returned just in case.
     *
     * @param url  A String representation of the URL to post to, which will be assumed to use HTTP/HTTPS.
     * @param adventure The content to post, which will be sent to the server in the request body.
     * @return The contents of the response, in case the server sends anything back after posting the content.
     * @throws IOException If the URL is malformed or the server can't be reached.
     */
    public Boolean postToUrl(String url, Adventure adventure) throws IOException {
        Gson g = new Gson();
        String adventureString = g.toJson(adventure);

        try {
            // Define the request
            // In this case, we have to use the .POST() and .headers() methods to define what we want (to send a string containing JSON data)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).headers("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(adventureString)).build();

            // We could use a BodyHandler that discards the response body, but here we return the API's response
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
            return true;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new IOException(e);
        }
    }

    /**
     * Method that removes the contents from a URL using the HTTPS protocol. Specifically, a DELETE request is sent.
     * Any parameters should be included in the URL.
     *
     * @param url A String representation of the URL to delete from, which will be assumed to use HTTP/HTTPS.
     * @return The contents of the response, in case the server sends anything back after deleting the content.
     * @throws IOException If the URL is malformed or the server can't be reached.
     */
    public String deleteFromUrl(String url) throws IOException {
        try {
            // Define the request
            // The default method is GET, so we don't need to specify it (but we could do so by calling .GET() before .build()
            // The HttpRequest.Builder pattern offers a ton of customization for the request (headers, body, HTTP version...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).DELETE().build();

            // We use the default BodyHandler for Strings (so we can get the body of the response as a String)
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new IOException(e);
        }
    }



    /**
     * Helper function that sets up a SSLContext designed to ignore certificates, accepting anything by default
     * NOT TO BE USED IN REAL PRODUCTION ENVIRONMENTS
     *
     * @return An instance of the SSLContext class, which manages SSL verifications, configured to accept even misconfigured certificates
     */
    private SSLContext insecureContext() throws NoSuchAlgorithmException, KeyManagementException {
        // We set up a TrustManager that accepts every certificate by default
        TrustManager[] insecureTrustManager = new TrustManager[]{new X509TrustManager() {
            // By not throwing any exceptions in these methods we're accepting everything
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            // This doesn't affect our use case, so we just return an empty array
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // We set up the SSLContext with the over-accepting TrustManager
        SSLContext sc = SSLContext.getInstance("ssl");
        sc.init(null, insecureTrustManager, null);
        return sc;
    }
}
