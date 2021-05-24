package br.ufrj.diegocf.monitor;

import java.util.Locale;

/**
 * Classe que roda a aplicação principal de monitoramento de temperatura
 */
public class App {
    private static final int NUM_SENSORS = 4;
    private static final int NUM_ACTUATOR = 4;

    public static void main(String[] args) {
        Thread[] sensorThreads = new Thread[NUM_SENSORS];
        Thread[] actuatorThreads = new Thread[NUM_ACTUATOR];
        Locale.setDefault(Locale.US);
        ReaderWriter rw = new ReaderWriter();
        CircularBuffer<SensorValue> buffer = new CircularBuffer<>(60);

        if (args.length >= 1 && args[0].equals("--log")) {
            Logger.showLog = true;
            rw.showLog(true);
        }

        if (Logger.showLog)
            System.out.println("from verificaApp import TestaApp, Estado\napp = TestaApp()");


        for (int i = 0; i < NUM_SENSORS; ++i) {
            sensorThreads[i] = new Thread(new Sensor(i, rw, buffer));
        }

        for (int i = 0; i < NUM_ACTUATOR; ++i) {
            actuatorThreads[i] = new Thread(new Actuator(i, rw, buffer));
        }

        for (int i = 0; i < NUM_SENSORS; ++i) {
            sensorThreads[i].start();
        }

        for (int i = 0; i < NUM_ACTUATOR; ++i) {
            actuatorThreads[i].start();
        }

    }

}
