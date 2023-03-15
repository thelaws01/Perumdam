package riswan.fkti.pipa.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {


    private static Retrofit getRetroinstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).baseUrl("https://masa-depan.website/api/").build();
    }

    public static ApiService getApiService(){
        return getRetroinstance().create(ApiService.class);
    }


}
