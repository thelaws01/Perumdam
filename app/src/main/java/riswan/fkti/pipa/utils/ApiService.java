package riswan.fkti.pipa.utils;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import riswan.fkti.pipa.models.Berita;
import riswan.fkti.pipa.models.BeritaDepan;
import riswan.fkti.pipa.models.History;
import riswan.fkti.pipa.models.Kategori;
import riswan.fkti.pipa.models.Pengaduan;
import riswan.fkti.pipa.models.auth.Login;
import riswan.fkti.pipa.models.auth.Register;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("loginRiswan")
    Call<ResponseBody> toLogin(@Body Login body);

    @GET("berita")
    Observable<List<BeritaDepan>> getDepanberita();

    @GET("tampilSlider")
    Observable<List<Berita>> getBeritaList();

    @GET("tampilPengaduan")
    Observable<List<Pengaduan>> getPengaduanAll();

    @GET("kategori")
    Observable<List<Kategori>> getKategoriAll();

    @GET("historyRiswan")
    Observable<List<History>> getHistory();

    @POST("registerBaru")
    Call<Register> daftarBaru(@Body Register register);


    @FormUrlEncoded
    @POST("updaeFotoProfil")
    Call<ResponseBody> getImagePro(
            @Field("username") String username,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("postPostingRiswan")
    Call<ResponseBody> addPost(
            @Field("username") String username,
            @Field("alamat") String alamat,
            @Field("info") String info,
            @Field("kategori_id") String id_kategori,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("updaeStringProfil")
    Call<ResponseBody> getProf(
            @Field("username") String username,
            @Field("nama") String name,
            @Field("nik") String nik,
            @Field("alamat") String alamat,
            @Field("phone") String phone,
            @Field("kecamatan") String kecamatan
    );

    @FormUrlEncoded
    @POST("updatePasswordRiswan")
    Call<ResponseBody> posPassword(
            @Field("username") String username,
            @Field("password") String password
    );


    
}
