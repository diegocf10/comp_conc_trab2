package br.ufrj.diegocf.monitor;

/**
 * Classe utilitária para gerar número aleatórios
 *
 */
public final class UtilRandom {
    private UtilRandom() {
    }

    /**
     * Retorna o valor aleatório entre start e end, inclusive
     *
     * @param start limite inferior, inclusive
     * @param end   limite superior, inclusive
     * @return
     */
    public static int randomBetween(int start, int end) {
        return (int) (Math.random() * (end - start + 1)) + start;
    }
}