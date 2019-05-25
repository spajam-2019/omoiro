package httpClient;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import okhttp3.*;
import com.google.firebase.storage.FirebaseStorage;
import org.jetbrains.annotations.NonNls;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.function.Consumer;

public class HttpClient{
    OkHttpClient okHttpClient;
    String baseUrl="https://us-central1-omoiro.cloudfunctions.net/app/";
    ObjectMapper mapper=new ObjectMapper();

    public HttpClient(){
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public void UploadFile(String fileName, byte[] bytes, Consumer<Uri> f){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://omoiro.appspot.com/");


        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainsRef = storageRef.child("images/"+fileName);

        // Get the data from an ImageView as bytes

        UploadTask uploadTask = mountainsRef.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("UPLOAD ERROR", exception.toString());
            }
        }).addOnSuccessListener(new SuccessListener(f));
    }

    class SuccessListener implements OnSuccessListener<UploadTask.TaskSnapshot>{

        Consumer<Uri> f;
        public SuccessListener(Consumer<Uri> f){
            this.f=f;
        }

        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            f.accept(taskSnapshot.getStorage().getDownloadUrl().getResult());
        }

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