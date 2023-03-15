package riswan.fkti.pipa.activity.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;


import riswan.fkti.pipa.activity.repo.HistoryRepo;
import riswan.fkti.pipa.models.History;

public class HistoryViewModel extends ViewModel {

    private HistoryRepo repo;
    private MutableLiveData<List<History>> mutableLiveData;

    public HistoryViewModel(){
        this.repo = new HistoryRepo();
    }

    public LiveData<List<History>> ambilHistory(){
        if(mutableLiveData == null){
            mutableLiveData = repo.ambilHistory();
        }
        return mutableLiveData;
    }
}
