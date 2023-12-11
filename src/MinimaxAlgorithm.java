public class MinimaxAlgorithm {

    private static int currentPlayer = 1;

    public static int minimaxAlfaBeta(int[][] board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || isGameOver(board)) {
            return evaluateBoard(board);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int move : getPossibleMoves(board)) {
                int[][] newBoard = makeMove(board, move);
                int eval = minimaxAlfaBeta(newBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Poda alfa-beta
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int move : getPossibleMoves(board)) {
                int[][] newBoard = makeMove(board, move);
                int eval = minimaxAlfaBeta(newBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Poda alfa-beta
                }
            }
            return minEval;
        }
    }

    // Implementa las funciones auxiliares según tus necesidades
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
    

    private static int evaluateBoard(int[][] board) {
        int score = 0;
    
        // Evaluar filas y columnas
        for (int i = 0; i < board.length; i++) {
            score += evaluateLine(board[i][0], board[i][1], board[i][2]); // Filas
            score += evaluateLine(board[0][i], board[1][i], board[2][i]); // Columnas
        }
    
        return score;
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

