package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EntrenadorViewModel extends AndroidViewModel {
    Entrenador entrenador;


    LiveData<Integer> golpeLiveData;


    public EntrenadorViewModel(@NonNull Application application) {
        super(application);

        entrenador = new Entrenador();

        golpeLiveData = Transformations.switchMap(entrenador.golpesLiveData, new Function<String, LiveData<Integer>>() {

            @Override
            public LiveData<Integer> apply(String golpe) {
                int imagen;
                switch (golpe) {
                    case "COMBO START1":
                    default:
                        imagen = R.drawable.jab1;
                        break;
                    case "COMBO START2":
                        imagen = R.drawable.jab2;
                        break;
                    case "COMBO START3":
                        imagen = R.drawable.powerdunk1;
                        break;
                    case "COMBO START4":
                        imagen = R.drawable.powerdunk2;
                        break;
                }
                return new MutableLiveData<>(imagen);
            }
        });
    }

    public LiveData<Integer> obtenerEjercicio() {
        return golpeLiveData;
    }
}


