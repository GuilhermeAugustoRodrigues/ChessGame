package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch= new ChessMatch();

        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Origin: ");
            ChessPosition origin = UI.readChessPosition(input);

            System.out.print("Target: ");
            ChessPosition target = UI.readChessPosition(input);
            System.out.println();

            ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);
        }
    }
}
