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
import riswan.fkti.pipa.models.History;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class HistoryRepo {

    private final String TAG = HistoryRepo.class.getSimpleName();
    private ApiService apiService;

    public MutableLiveData<List<History>> ambilHistory(){
        final MutableLiveData<List<History>> mutableLiveData = new MutableLiveData<>();


        apiService = RetroClass.getApiService();
        Observable<List<History>> galer = apiService.getHistory();
        galer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<History>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<History> histories) {
                        mutableLiveData.setValue(histories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "kelar History" + "kelar");
                    }
                });

        return mutableLiveData;
    }
}
