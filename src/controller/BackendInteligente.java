package controller;

public class BackendInteligente implements BoardUpdateListener {
    private int[][] currentBoard;
    private static int currentPlayer = 1;

    public BackendInteligente() {
        // Inicializar el tablero como vacío o con algún estado predeterminado
        this.currentBoard = new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
    }

    public void actualizarTablero(int[][] updatedBoard) {
        if (updatedBoard.length != 3 || updatedBoard[0].length != 3) {
            throw new IllegalArgumentException("La matriz debe tener dimensiones 3x3");
        }

        this.currentBoard = updatedBoard;

        // Realizar lógica adicional si es necesario
        // Por ejemplo, verificar si el juego ha terminado, etc.
        if (isGameOver(currentBoard)) {
            System.out.println("¡Juego terminado!");
        }
    }

    @Override
    public void onBoardUpdate(int[][] updatedBoard) {
        actualizarTablero(updatedBoard);

        int bestMove = ejecutarMinimaxConPodaAlfaBeta(currentBoard);

        // Realizar acciones adicionales según el movimiento calculado
        realizarMovimiento(bestMove);
    }

    private void realizarMovimiento(int column) {
        int[][] newBoard = makeMove(currentBoard, column);

        // Realizar otras acciones según el nuevo tablero
        System.out.println("Mi movimiento es en la columna " + (column + 1));
    }

    private int ejecutarMinimaxConPodaAlfaBeta(int[][] board) {
        int[] result = minimax(board, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return result[0];
    }

    private int[] minimax(int[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        int[] bestMove = new int[]{-1, -1};

        if (depth == 0 || isGameOver(board)) {
            int score = evaluateBoard(board);
            return new int[]{score, -1};
        }

        int[] possibleMoves = getPossibleMoves(board);
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int move : possibleMoves) {
                int[][] newBoard = makeMove(board, move);
                int eval = minimax(newBoard, depth - 1, alpha, beta, false)[0];
                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove[0] = move;
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break; // Poda alfa-beta
                    }
                }
            }
            bestMove[1] = maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int move : possibleMoves) {
                int[][] newBoard = makeMove(board, move);
                int eval = minimax(newBoard, depth - 1, alpha, beta, true)[0];
                if (eval < minEval) {
                    minEval = eval;
                    bestMove[0] = move;
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break; // Poda alfa-beta
                    }
                }
            }
            bestMove[1] = minEval;
        }

        return bestMove;
    }

    // Métodos auxiliares
    private static boolean isGameOver(int[][] board) {
        // Verificar filas y columnas
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] != 0 &&
                board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true; // Alineación horizontal
            }
    
            if (board[0][i] != 0 &&
                board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true; // Alineación vertical
            }
        }
    
        // Verificar diagonales
        if (board[0][0] != 0 &&
            board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true; // Alineación diagonal principal
        }
    
        if (board[0][2] != 0 &&
            board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true; // Alineación diagonal secundaria
        }
    
        // Si no se cumple ninguna condición, el juego no ha terminado
        return false;
    }

    private static int evaluateLine(int cell1, int cell2, int cell3) {
        int score = 0;
    
        // Evaluar la línea formada por tres celdas
        if (cell1 == 1 && cell2 == 1 && cell3 == 1) {
            score += 100; // Alineación de cuadrados
        } else if (cell1 == 2 && cell2 == 2 && cell3 == 2) {
            score -= 100; // Alineación de triángulos
        }
    
        return score;
    }

    private static int evaluateBoard(int[][] board) {
        int score = 0;
    
        // Evaluar filas y columnas
        for (int i = 0; i < board.length; i++) {
            score += evaluateLine(board[i][0], board[i][1], board[i][2]); // Filas
            score += evaluateLine(board[0][i], board[1][i], board[2][i]); // Columnas
        }
    
        return score;
    }
    private static int[] getPossibleMoves(int[][] board) {
        // Contar las columnas vacías
        int emptyColumns = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == 0) {
                emptyColumns++;
            }
        }
    
        // Crear un array con las columnas vacías
        int[] possibleMoves = new int[emptyColumns];
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == 0) {
                possibleMoves[index] = i;
                index++;
            }
        }
    
        return possibleMoves;
    }

    private static int[][] makeMove(int[][] board, int column) {
        // Encuentra la fila vacía en la columna seleccionada
        int row = findEmptyRow(board, column);
    
        // Clona el tablero para no modificar el original
        int[][] newBoard = cloneBoard(board);
    
        // Coloca la ficha en la posición correspondiente
        newBoard[row][column] = getCurrentPlayer(); // getCurrentPlayer() debería devolver el valor de la ficha actual
    
        switchPlayer();
        return newBoard;
    }

    private static int findEmptyRow(int[][] board, int column) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][column] == 0) {
                return i;
            }
        }
        // Si la columna está llena, esto debería manejarse adecuadamente según tus reglas
        throw new IllegalStateException("Columna llena, no se puede realizar el movimiento");
    }

    private static int[][] cloneBoard(int[][] board) {
        int[][] clone = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, clone[i], 0, board[i].length);
        }
        return clone;
    }


    private static int getCurrentPlayer() {
        return currentPlayer;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }
}
