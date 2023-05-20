package org.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NoughtsCrosses {
    private JFrame frame;
    private JPanel gamePanel;
    private JButton[] buttons;
    private int[][] board;
    private String currentPlayer;
    private int moveCount;

    public NoughtsCrosses() {
        frame = new JFrame("Noughts and Crosses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        frame.add(gamePanel, BorderLayout.CENTER);

        buttons = new JButton[9];
        board = new int[3][3];
        currentPlayer = "X";
        moveCount = 0;

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Tahoma", Font.BOLD, 60));
            buttons[i].setHorizontalAlignment(SwingConstants.CENTER);
            buttons[i].setName(Integer.toString(i)); // Set the name property

            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    int index = Integer.parseInt(button.getName()); // Use the name property

                    int row = index / 3; // Compute row and column based on index
                    int col = index % 3;

                    if (board[row][col] != 0) {
                        return; // Cell already occupied
                    }

                    moveCount++;
                    button.setText(currentPlayer);
                    button.setEnabled(false);
                    board[row][col] = (currentPlayer.equals("X")) ? 1 : 2;

                    winningGame();

                    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
                }
            });
            gamePanel.add(buttons[i]);
        }

        frame.setVisible(true);
    }

    private void winningGame() {
        if (moveCount < 5) {
            return; // No winner possible before 5 moves
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                declareWinner(board[i][0]);
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                declareWinner(board[0][i]);
                return;
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            declareWinner(board[0][0]);
            return;
        }

        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            declareWinner(board[0][2]);
            return;
        }

        if (moveCount == 9) {
            declareDraw();
        }
    }

    private void declareWinner(int player) {
        String message = (player == 1) ? "Player X wins!" : "Player O wins!";
        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void declareDraw() {
        JOptionPane.showMessageDialog(frame, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setName(Integer.toString(i)); // Reset the name property
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }

        currentPlayer = "X";
        moveCount = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NoughtsCrosses();
            }
        });
    }
}
