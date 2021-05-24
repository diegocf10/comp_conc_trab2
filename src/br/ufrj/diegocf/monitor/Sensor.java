package br.ufrj.diegocf.monitor;

/**
 * Classe que representa os sensores
 */
public class Sensor implements Runnable {
    private final int id;
    private final CircularBuffer<SensorValue> buffer;
    private final ReaderWriter rw;

    public Sensor(int id, ReaderWriter rw, CircularBuffer<SensorValue> buffer) {
        this.id = id;
        this.rw = rw;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                rw.acquireWriteLock(id);

                int temperature = UtilRandom.randomBetween(25, 40);

                for (SensorValue value : buffer) {
                    if (value.sensor == this.id) {
                        ++value.readings;
                    }
                }

                if (temperature > 30) {
                    buffer.add(new SensorValue(temperature, this.id));
                }

                if (Logger.showLog)
                    System.out.printf("app.sensor_id_temperatura(%d, %d)\n", id, temperature);
                else
                    System.out.printf("Sensor %d: Temperatura: %d\n", id, temperature);


                rw.releaseWriteLock(id);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}