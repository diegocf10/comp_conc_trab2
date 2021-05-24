package br.ufrj.diegocf.monitor;

/**
 * Classe que representa uma tripla: a temperatura, identificador do sensor,
 * identificador de leitura
 *
 */
public class SensorValue {
    /**
     * temperatura registrada
     */
    public int temperature;
    /**
     * Identificador do sensor
     */
    public int sensor;
    /**
     * Identificador de leitura
     */
    public int readings;

    public SensorValue(int temperature, int sensor) {
        this.temperature = temperature;
        this.sensor = sensor;
        this.readings = 0;
    }

    @Override
    public String toString() {
        return String.format("[[ %d %d %d ]]", sensor, temperature, readings);
    }
}