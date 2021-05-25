package br.ufrj.diegocf.monitor;

import java.util.Locale;

/**
 * Classe que roda a aplicação principal de monitoramento de temperatura
 */
public class App {
    // cria 4 atuadores e 4 sensores
    private static final int NUM_ACTUATOR_SENSOR = 4;

    public static void main(String[] args) {
        Thread[] sensorThreads = new Thread[NUM_ACTUATOR_SENSOR];
        Thread[] actuatorThreads = new Thread[NUM_ACTUATOR_SENSOR];
        Locale.setDefault(Locale.US);
        ReaderWriter rw = new ReaderWriter();
        CircularBuffer<SensorValue> buffer = new CircularBuffer<>(60);

        if (args.length >= 1 && args[0].equals("--log")) {
            Logger.showLog = true;
            rw.showLog(true);
        }

        if (Logger.showLog)
            System.out.println("from verificaApp import TestaApp, Estado\napp = TestaApp()");


        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            sensorThreads[i] = new Thread(new Sensor(i, rw, buffer));
        }

        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            actuatorThreads[i] = new Thread(new Actuator(i, rw, buffer));
        }

        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            sensorThreads[i].start();
        }

        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            actuatorThreads[i].start();
        }

    }

}
