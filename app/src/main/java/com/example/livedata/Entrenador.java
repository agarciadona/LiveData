package com.example.livedata;

import androidx.lifecycle.LiveData;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Entrenador {

    interface TerryListener {
        void cuandoGolpee(String golpe);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> golpeando;

    LiveData<String> golpesLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarCombo(new TerryListener() {
                @Override
                public void cuandoGolpee(String golpe) {
                    postValue(golpe);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            pararCombo();
        }
    };

    void iniciarCombo(TerryListener terryListener) {
        if (golpeando == null || golpeando.isCancelled()) {

            golpeando = scheduler.scheduleAtFixedRate(new Runnable() {
                int golpe = 1;

                @Override
                public void run() {
                    terryListener.cuandoGolpee("COMBO START"+golpe);
                    golpe++;
                    if(golpe > 4){
                        golpe = 1;
                    }
                }
            }, 0, 2, SECONDS);
        }
    }

    void pararCombo() {
        if (golpeando != null) {
            golpeando.cancel(true);
        }
    }
}