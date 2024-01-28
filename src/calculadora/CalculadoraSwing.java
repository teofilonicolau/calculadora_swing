package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing {
    private JFrame frame;
    private JTextField display;
    private JTextArea resultados;

    private double primeiroNumero;
    private String operacaoPendente;

    public CalculadoraSwing() {
        frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configura o tamanho da janela para 30% da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.3);
        int height = (int) (screenSize.getHeight() * 0.3);
        frame.setSize(width, height);

        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        resultados = new JTextArea();
        resultados.setEditable(false);
        frame.add(new JScrollPane(resultados), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));  // Aumentei o número de linhas para incluir o botão "CE"

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "CE"  // Adicionado o botão "CE"
        };

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            switch (buttonText) {
                case "=":
                    calcularResultado();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case ".":
                    definirOperacao(buttonText);
                    break;
                case "CE":
                    limparTela();
                    break;
                default:
                    atualizarDisplay(buttonText);
            }
        }
    }

    private void calcularResultado() {
        String textoDisplay = display.getText().trim(); // Remove espaços em branco no início e no final

        if (!textoDisplay.isEmpty()) {
            double segundoNumero = Double.parseDouble(textoDisplay);

            switch (operacaoPendente) {
                case "+":
                    display.setText(String.valueOf(primeiroNumero + segundoNumero));
                    break;
                case "-":
                    display.setText(String.valueOf(primeiroNumero - segundoNumero));
                    break;
                case "*":
                    display.setText(String.valueOf(primeiroNumero * segundoNumero));
                    break;
                case "/":
                    if (segundoNumero != 0) {
                        display.setText(String.valueOf(primeiroNumero / segundoNumero));
                    } else {
                        display.setText("Erro");
                    }
                    break;
                case "%":
                    display.setText(String.valueOf(primeiroNumero * segundoNumero / 100));
                    break;
                case "√":
                    display.setText(String.valueOf(Math.sqrt(primeiroNumero)));
                    break;
            }

            // Adiciona o resultado ao JTextArea
            resultados.append(display.getText() + "\n");

            operacaoPendente = null;
        } else {
            // Lidar com a situação em que o display está vazio
            // Por exemplo, exibir uma mensagem de erro ou não fazer nada
        }
    }

    private void definirOperacao(String operacao) {
        if (operacao.equals("=")) {
            operacao = operacaoPendente;
        }

        if (operacao.equals("√")) {
            primeiroNumero = Double.parseDouble(display.getText());
            calcularResultado();
        } else {
            primeiroNumero = Double.parseDouble(display.getText());
            display.setText("");
            operacaoPendente = operacao;
        }
    }

    private void limparTela() {
        display.setText("");
        operacaoPendente = null;
    }

    private void atualizarDisplay(String texto) {
        String displayText = display.getText();
        displayText += texto;
        display.setText(displayText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraSwing::new);
    }
}
