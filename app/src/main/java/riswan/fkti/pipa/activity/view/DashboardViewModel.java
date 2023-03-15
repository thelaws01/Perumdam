package riswan.fkti.pipa.activity.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import riswan.fkti.pipa.activity.repo.DashboardRepo;
import riswan.fkti.pipa.models.BeritaDepan;
import riswan.fkti.pipa.models.Pengaduan;

public class DashboardViewModel extends ViewModel {

    private DashboardRepo repo;
    private MutableLiveData<List<BeritaDepan>> mutableLiveData;

    public DashboardViewModel(){
        repo = new DashboardRepo();
    }

    public LiveData<List<BeritaDepan>> getBeritaDepan(){
        if(mutableLiveData == null){
            mutableLiveData = repo.getBerita();
        }
        return mutableLiveData;
    }
}
