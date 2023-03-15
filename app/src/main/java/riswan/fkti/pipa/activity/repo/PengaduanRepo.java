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
import riswan.fkti.pipa.models.Pengaduan;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class PengaduanRepo{

    private static final String TAG = PengaduanRepo.class.getSimpleName();
    ApiService apiService;

    public MutableLiveData<List<Pengaduan>> getPengaduan(){

        final MutableLiveData<List<Pengaduan>> peler = new MutableLiveData<>();

        apiService = RetroClass.getApiService();

        Observable<List<Pengaduan>> galer = apiService.getPengaduanAll();
        galer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Pengaduan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Pengaduan> pengaduanList) {
                        peler.setValue(pengaduanList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Kelar" + "aduan kelar");
                    }
                });

        return peler;
    }
}
