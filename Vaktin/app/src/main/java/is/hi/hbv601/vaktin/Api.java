package is.hi.hbv601.vaktin;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    AppDatabase db;

    OkHttpClient client = new OkHttpClient();

    public void addEmployee(String url, Employee employee, String tok) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", employee.getName());
            jsonObject.put("role", employee.getRole());
            jsonObject.put("tFrom", employee.gettFrom());
            jsonObject.put("tTo", employee.gettTo());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace()[0].getLineNumber());
        }
        if (tok != null) {
            RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + tok)
                    .build();
            try (Response res = client.newCall(request).execute()) {
                // Nothing
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public boolean getLastModified(String url, String tok) throws IOException, JSONException {
        db = AppDatabase.getInstance();
        if (tok != null) {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + tok)
                    .build();
            try (Response res = client.newCall(request).execute()) {
                String result = null;
                LocalDateTime tmpDate = null;
                LocalDateTime lastFetched = null;
                Boolean needToFetch = false;

                result = res.body().string();
                JSONObject jsonObject = new JSONObject(result);
                lastFetched = LocalDateTimeConverter.toDate(db.tokenDao().findById(1).getLastFetched());
                tmpDate = LocalDateTimeConverter.toDate(jsonObject.getString("date"));

                if (tmpDate.compareTo(lastFetched) > 0) {
                    needToFetch = true;
                }

                return needToFetch;
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace()[0].getLineNumber());
            }

        }

        return false;
    }

    //public String setFooter(String url, JS)



    public String removeComment(String url, String comment, String tok) throws IOException, JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("comment", comment);
        if (tok != null) {
            RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + tok)
                    .build();
            try (Response res = client.newCall(request).execute()) {
                return res.body().string();
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return null;

    }

    public String deleteWorkstation(String url, String name, String tok) throws IOException, JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("workstationName", name);
        if (tok != null) {
            RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + tok)
                    .build();
            try (Response res = client.newCall(request).execute()) {
                return res.body().string();
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return null;

    }

    /***
     * Bæta við nýrri vinnustöð
     * Þurfum kannski að skila einhverju öðru en null
     */
    public String postNewWorkstation(String url, String workstationName, String tok) throws IOException, JSONException {
        // Búa til json object til að senda á rest controller
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("workstationName", workstationName);

        RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + tok)
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
     * Bæta við nýju commenti
     */

    public String postNewComment(String url, String description, String tok) throws IOException, JSONException {
        // Búa til json object til að senda á rest controller
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("description", description);

        RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + tok)
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
     * Bæta við nýjum footer
     */
    public String postNewFooter(String url, String date, String shiftManager, String shiftManagerNumber, String headDoctor, String headDoctorNumber, String tok) throws IOException, JSONException {
        // Búa til json object til að senda á rest controller
        JSONObject jsonObj = new JSONObject();
        System.out.println(date);
        jsonObj.put("date", date);
        jsonObj.put("shiftManager", shiftManager);
        jsonObj.put("shiftManagerNumber", shiftManagerNumber);
        jsonObj.put("headDoctor", headDoctor);
        jsonObj.put("headDoctorNumber", headDoctorNumber);


        RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + tok)
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
