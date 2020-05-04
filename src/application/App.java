package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch= new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Origin: ");
                ChessPosition origin = UI.readChessPosition(input);

                boolean[][] possibleMoves = chessMatch.possibleMoves(origin);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(input);

                ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);

                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter type of piece for promotion (B/N/R/Q):");
                    String pieceType = input.nextLine().toLowerCase();
                    while (!pieceType.equals("b") && !pieceType.equals("n") && !pieceType.equals("r") && !pieceType.equals("q")) {
                        System.out.print("Enter type of piece for promotion (B/N/R/Q):");
                        pieceType = input.nextLine().toLowerCase();
                    }
                    chessMatch.replacePromotedPiece(pieceType);
                }
            } catch (ChessException exception) {
                System.out.print(exception.getMessage());
                input.nextLine();
            } catch (InputMismatchException exception) {
                System.out.print(exception.getMessage());
                input.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, capturedPieces);
    }
}
