package controller;

public class BackendInteligente implements BoardUpdateListener {
    private int[][] currentBoard;

    public BackendInteligente() {
        // Inicializar el tablero como vacío o con algún estado predeterminado
        this.currentBoard = new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
    }

    public void actualizarTablero(int[][] updatedBoard) {
        // Actualizar el tablero actual con la nueva información
        this.currentBoard = updatedBoard;

        // Realizar lógica adicional si es necesario
        // Por ejemplo, verificar si el juego ha terminado, etc.
    }

    @Override
    public void onBoardUpdate(int[][] updatedBoard) {
        // Implementar la lógica específica cuando se recibe una actualización del tablero
        actualizarTablero(updatedBoard);

        // Ejecutar el algoritmo Minimax con poda alfa-beta para determinar el próximo movimiento
        int bestMove = ejecutarMinimaxConPodaAlfaBeta(currentBoard);

        // Realizar acciones adicionales según el movimiento calculado (puede ser enviar el movimiento a otras partes del sistema)
        System.out.println("El próximo movimiento es en la columna " + (bestMove + 1));
    }

    private int ejecutarMinimaxConPodaAlfaBeta(int[][] board) {
        // Implementar el algoritmo Minimax con poda alfa-beta
        // ...
        // Devolver la mejor columna para el próximo movimiento
        return 0;  // Ajustar según la implementación real
    }
}
