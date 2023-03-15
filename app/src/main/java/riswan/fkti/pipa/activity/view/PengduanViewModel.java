package riswan.fkti.pipa.activity.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import riswan.fkti.pipa.activity.repo.PengaduanRepo;
import riswan.fkti.pipa.models.Pengaduan;

public class PengduanViewModel extends ViewModel {

    public PengaduanRepo repo;
    public MutableLiveData<List<Pengaduan>> mutableLiveData;

    public PengduanViewModel(){
        repo = new PengaduanRepo();
    }

    public LiveData<List<Pengaduan>> getPengaduanAll(){
        if (mutableLiveData == null){
            mutableLiveData = repo.getPengaduan();
        }
        return mutableLiveData;
    }
}
