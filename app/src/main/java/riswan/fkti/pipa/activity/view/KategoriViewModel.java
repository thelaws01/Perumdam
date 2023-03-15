package riswan.fkti.pipa.activity.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import riswan.fkti.pipa.activity.repo.KategoriRepo;
import riswan.fkti.pipa.models.Kategori;

public class KategoriViewModel extends ViewModel {

    private KategoriRepo repo;
    private MutableLiveData<List<Kategori>> mutableLiveData;

    public KategoriViewModel(){
        this.repo = new KategoriRepo();
    }

    public LiveData<List<Kategori>> ambilKategori(){
        if(mutableLiveData == null){
            mutableLiveData = repo.ambilLategori();
        }
        return mutableLiveData;
    }
}
