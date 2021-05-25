package br.ufrj.diegocf.monitor;

/**
 * Classe que representa os atuadores
 */
public class Actuator implements Runnable {
    private enum ActuatorState {
        RED_WARNING, YELLOW_WARNING, NORMAL,
    }

    private final int id;
    private final CircularBuffer<SensorValue> buffer;
    private final ReaderWriter rw;

    public Actuator(int id, ReaderWriter rw, CircularBuffer<SensorValue> buffer) {
        this.id = id;
        this.rw = rw;
        this.buffer = buffer;

    }

    @Override
    public void run() {
        try {
            while (true) {
                rw.acquireReadLock(id);
                int numReadings = 0; // numéro total de leituras registradas
                int above35 = 0;
                int totalTemperature = 0;
                ActuatorState state = ActuatorState.NORMAL;
                // guarda nª total de leituras realizadas,
                // incluindo as com temperatura menor ou igual 30
                int numTotalReadings = 0;

                for (SensorValue register : buffer.reverse()) {
                    if (register.sensor == this.id) {
                        if (numTotalReadings != register.readings) {
                            numTotalReadings = register.readings;
                        }

                        totalTemperature += register.temperature;
                        ++numReadings;
                        ++numTotalReadings;

                        if (register.temperature > 35) {
                            ++above35;
                        }

                        if (state == ActuatorState.NORMAL) {
                            if (numTotalReadings == 5 && above35 == 5) {
                                state = ActuatorState.RED_WARNING;
                            } else if (numTotalReadings > 5 && numTotalReadings <= 15 && above35 >= 5) {
                                state = ActuatorState.YELLOW_WARNING;
                            }
                        }
                    }
                }

                double avg = numReadings == 0 ? 0.0 : (double) (totalTemperature) / numReadings;
                String message = null;
                String messageFull = null;

                switch (state) {
                    case YELLOW_WARNING:
                        message = "Estado.ALERTA_AMARELO";
                        messageFull = "Alerta amarelo";
                        break;
                    case RED_WARNING:
                        message = "Estado.ALERTA_VERMELHO";
                        messageFull = "Alerta vermelho";
                        break;
                    default:
                        message = "Estado.CONDICAO_NORMAL";
                        messageFull = "Condição normal";
                }

                if (Logger.showLog)
                    System.out.printf("app.atuador_id_aviso_media(%d, %s, %f)\n", id, message, avg);
                else
                    System.out.printf("Atuador %d: %s. Média das temperaturas: %.2f\n", id, messageFull, avg);

                rw.releaseReadLock(id);
                Thread.sleep(2000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}