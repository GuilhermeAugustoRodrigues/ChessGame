package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch= new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Origin: ");
                ChessPosition origin = UI.readChessPosition(input);

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(input);

                ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);
            } catch (ChessException exception) {
                System.out.print(exception.getMessage());
                input.nextLine();
            } catch (InputMismatchException exception) {
                System.out.print(exception.getMessage());
                input.nextLine();
            }
        }
    }
}
