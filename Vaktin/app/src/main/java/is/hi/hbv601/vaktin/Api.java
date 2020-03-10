package is.hi.hbv601.vaktin;

import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    /***
     * Til auðkenningar
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postAuth(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response res = client.newCall(request).execute()) {
            return res.body().string();
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }


        return null;
    }

    /***
     * Sækir allt sem þarf fyrir MainActivity þegar app er opnað
     * @param url
     * @param tok
     * @return
     * @throws IOException
     */
    public String getFrontPage(String url, String tok) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tok)
                .build();
        try (Response res = client.newCall(request).execute()){
            return res.body().string();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
