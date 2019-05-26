package httpClient;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.*;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import okhttp3.*;
import com.google.firebase.storage.FirebaseStorage;
import org.jetbrains.annotations.NonNls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;
import java.util.UUID;

public class HttpClient {
    OkHttpClient okHttpClient;
    String baseUrl = "https://us-central1-omoiro.cloudfunctions.net/app/";
    ObjectMapper mapper = new ObjectMapper();

    public HttpClient() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public void UploadFile(String ext, byte[] bytes, Consumer<String> f) {

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://omoiro.appspot.com/");

        String fileName = UUID.randomUUID().toString() + "." + ext;
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainsRef = storageRef.child("images/" + fileName);

        // Get the data from an ImageView as bytes

        UploadTask uploadTask = mountainsRef.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("UPLOAD ERROR", exception.toString());
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    f.accept("https://firebasestorage.googleapis.com" + downloadUri.getEncodedPath() + "?alt=media");
            }
        });
    }

    public Omoiro[] GetOmoiros() throws Exception {
        Request request = new Request.Builder()
                .url(baseUrl + "omoiros")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();

                return mapper.readValue(body, Omoiro[].class);
            }
        }
        throw new Exception("omoiros error");
    }

    public void CreateOmoiro(ReqOmoiro req, Consumer<String> f) throws Exception {
        Request request = new Request.Builder()
                .url(baseUrl + "omoiros/create")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mapper.writeValueAsString(req)))
                .build();

        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.body() != null) {
                            String body = response.body().string();
                            f.accept(mapper.readValue(body, ResId.class).id);
                        }
                    }
                });
    }

    public String AddEmoPush(ReqEmoPush req) throws Exception {
        Request request = new Request.Builder()
                .url(baseUrl + "emo_pushs/insert")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mapper.writeValueAsString(req)))
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
                return mapper.readValue(body, ResOk.class).ok;
            }
        }
        throw new Exception("emopush error");
    }
}