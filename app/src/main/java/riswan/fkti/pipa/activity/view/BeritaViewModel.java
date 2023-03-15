package riswan.fkti.pipa.activity.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import riswan.fkti.pipa.activity.repo.BeritaRepo;
import riswan.fkti.pipa.models.BeritaDepan;

public class BeritaViewModel extends ViewModel {

    private BeritaRepo repo;
    private MutableLiveData<List<BeritaDepan>> mutableLiveData;

    public BeritaViewModel(){

        this.repo = new BeritaRepo();
    }

    public LiveData<List<BeritaDepan>> ambilBerita(){
        if(mutableLiveData == null){
            mutableLiveData = repo.ambilBerita();
        }
        return mutableLiveData;
    }
}
