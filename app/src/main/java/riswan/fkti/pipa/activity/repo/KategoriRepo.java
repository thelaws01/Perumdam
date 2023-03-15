package riswan.fkti.pipa.activity.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import riswan.fkti.pipa.models.Kategori;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class KategoriRepo {

    private  final String TAG = KategoriRepo.class.getSimpleName();
    private ApiService apiService;

    public MutableLiveData<List<Kategori>> ambilLategori(){
        final MutableLiveData<List<Kategori>> mutableLiveData = new MutableLiveData<>();


        apiService = RetroClass.getApiService();
        Observable<List<Kategori>> galer = apiService.getKategoriAll();
        galer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Kategori>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Kategori> kategoriList) {
                        mutableLiveData.setValue(kategoriList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "KelarKategori" + "Kelar");
                    }
                });

        return mutableLiveData;
    }
}
