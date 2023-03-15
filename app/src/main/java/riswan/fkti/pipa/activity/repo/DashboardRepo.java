package riswan.fkti.pipa.activity.repo;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import riswan.fkti.pipa.models.Berita;
import riswan.fkti.pipa.models.BeritaDepan;
import riswan.fkti.pipa.models.Pengaduan;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class DashboardRepo {

    private final String TAG = getClass().getSimpleName();
    ApiService apiService;

    public MutableLiveData<List<BeritaDepan>> getBerita(){

        final MutableLiveData<List<BeritaDepan>> imutkan = new MutableLiveData<>();

        apiService = RetroClass.getApiService();

        Observable<List<BeritaDepan>> beritas = apiService.getDepanberita();
        beritas.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<BeritaDepan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<BeritaDepan> beritaDepans) {
                        imutkan.setValue(beritaDepans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG BERITa" , "kelar");
                    }
                });

        return imutkan;
    }

}
