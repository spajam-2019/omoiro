import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class HttpClient{
    OkHttpClient okHttpClient;
    String baseUrl="https://us-central1-omoiro.cloudfunctions.net/app/";
    ObjectMapper mapper=new ObjectMapper();

    public HttpClient(){
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public Omoiro[] GetOmoiros() throws Exception{
        Request request = new Request.Builder()
                .url(baseUrl+"omoiros")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();

                return mapper.readValue(body,Omoiro[].class);
            }
        }
        throw new Exception("omoiros error");
    }

    public  String CreateOmoiro(ReqOmoiro req) throws Exception{
        Request request = new Request.Builder()
                .url(baseUrl+"omoiros/create")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),mapper.writeValueAsString(req)))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();

                return mapper.readValue(body,ResId.class).id;
            }
        }
        throw new Exception("omoiros error");
    }

    public  String AddEmoPush(ReqEmoPush req) throws Exception{
        Request request = new Request.Builder()
                .url(baseUrl+"emo_pushs/insert")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),mapper.writeValueAsString(req)))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();
                System.out.println(body);
                return mapper.readValue(body,ResOk.class).ok;
            }
        }
        throw new Exception("emopush error");
    }
}