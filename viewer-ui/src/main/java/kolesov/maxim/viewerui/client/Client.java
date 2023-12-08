package kolesov.maxim.viewerui.client;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.form.FormData;

import java.io.File;
import java.util.List;

public interface Client {

//    @RequestLine("POST /")
//    @Headers(value = {
//            "Content-Type: multipart/form-data; boundary=Asrf456BGe4h",
//            "Authorization: {token}"
//    })
//    @Body("""
//            --Asrf456BGe4h
//            Content-Disposition: form-data; name="file"; filename="image.jpg"
//            Content-Type: application/octet-stream
//
//            {file}
//            --Asrf456BGe4h--
//            """)
//    void sendFile(@Param(value = "file") byte[] file, @Param("token") String token);
    @RequestLine("POST /")
    @Headers(value = {
            "Content-Type: multipart/form-data",
            "Authorization: {token}"
    })
    void sendFile(@Param("name") String name, @Param("file") File file, @Param("token") String token);

    @RequestLine("POST /")
    @Body("{data}")
    @Headers(value = {
            "Content-Type: text/plain",
            "Authorization: {token}"
    })
    void sendText(@Param("data") String text, @Param("token") String token);

    @RequestLine("GET /images/{image}")
    @Headers("Accept: application/octet-stream")
    byte[] getImage(@Param("image") String image);

    @RequestLine("GET /all")
    String getAll();

}
