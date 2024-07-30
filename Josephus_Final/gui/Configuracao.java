package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe responsável por configurar a janela de configuração do simulador de Josephus.
 * 
 * @author Igor Costa; Joao Avila; Priscilla de Souza; Larissa Hipolito
 * @version 18/06/2024
 */
public class Configuracao extends JFrame
{
    private JPanel geral; // Painel que agrupa todos os objetos da janela
    private JPanel sliders; // Painel que agrupa o conjunto dos valores definidos pelo usuário
    private JPanel quantidade; // Painel que agrupa os elementos da definição de quantidade
    private JPanel passo; // Painel que agrupa os elementos da definição de passo
    private JPanel velocidade; // Painel que agrupa os elementos da definição de velocidade da simulação
    private JPanel botoes; // Painel que agrupa os botões
    private JLabel lblTitulo; // Título da janela
    private JLabel lblQtd; // Título da quantidade
    private JLabel lblNumQtd; // Valor correspondente do slider de quantidade
    private JLabel lblPasso; // Título do passo
    private JLabel lblNumPasso; // Valor correspondente do slider de passo
    private JLabel lblVel; // Título da velocidade
    private JLabel lblNumVel; // Valor correspondente do slider de velocidade
    private JButton btnSimular; // Botão para iniciar a simulação
    private JButton btnSobre; // Botão para explicar o problema de Josephus
    private JButton btnSair; // Botão para encerrar o programa
    private JSlider sldQtd; // Slider para armazenar o valor de quantidade
    private JSlider sldPasso; // Slider para armazenar o valor de passo
    private JSlider sldVel; // Slider para armazenar o valor de velocidade  
    
    /**
     * Construtor da classe Configuracao.
     */
    public Configuracao()
    {
        iniciarComponentes();
    }

    /**
     * Método que inicializa os componentes da janela.
     */
    private void iniciarComponentes()
    {
        geral = new JPanel();
        geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        geral.setBackground(Color.BLACK);
        
        // Título da janela
        lblTitulo = new JLabel("SIMULADOR JOSEPHUS");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("PERPETUA TITLING MT", Font.BOLD, 20));
        geral.add(lblTitulo);
        
        // Painel que agrupa os sliders
        sliders = new JPanel();
        sliders.setLayout(new FlowLayout());
        sliders.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliders.setBackground(Color.BLACK);
        
        // Painel que agrupa os elementos da definição de quantidade
        quantidade = new JPanel();
        quantidade.setLayout(new BoxLayout(quantidade, BoxLayout.Y_AXIS));
        quantidade.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        quantidade.setBackground(Color.BLACK);
        
        // Título da quantidade
        lblQtd = new JLabel("Quantidade de Igors");
        lblQtd.setForeground(Color.WHITE);
        lblQtd.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        lblQtd.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantidade.add(lblQtd);
        
        // Slider de quantidade
        sldQtd = new JSlider(2, 100, 50);
        sldQtd.setBackground(Color.BLACK);
        quantidade.add(sldQtd); 
        
        // Valor correspondente do slider de quantidade
        lblNumQtd = new JLabel(Integer.toString(sldQtd.getValue()));
        lblNumQtd.setForeground(Color.WHITE);
        lblNumQtd.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        quantidade.add(lblNumQtd);
        
        // Adiciona o painel de quantidade ao painel de sliders
        sliders.add(quantidade);
        
        // Painel que agrupa os elementos da definição de passo
        passo = new JPanel();
        passo.setLayout(new BoxLayout(passo, BoxLayout.Y_AXIS));
        passo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passo.setBackground(Color.BLACK);
        
        // Título do passo
        lblPasso = new JLabel("Passo");
        lblPasso.setForeground(Color.WHITE);
        lblPasso.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        lblPasso.setAlignmentX(Component.CENTER_ALIGNMENT);
        passo.add(lblPasso);
        
        // Slider de passo
        sldPasso = new JSlider(1, sldQtd.getValue());
        sldPasso.setBackground(Color.BLACK);
        passo.add(sldPasso);
        
        // Adiciona um listener ao slider de quantidade para atualizar o valor máximo do slider de passo
        sldQtd.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                lblNumQtd.setText(Integer.toString(source.getValue())); // Altera o label
                sldPasso.setMaximum(source.getValue()); // O valor maximo do passo nao podera ser maior que o da quantidade
            }
        });

        // Valor correspondente do slider de passo
        lblNumPasso = new JLabel(Integer.toString(sldPasso.getValue()));
        lblNumPasso.setForeground(Color.WHITE);
        lblNumPasso.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        passo.add(lblNumPasso);
        
        // Adiciona um listener ao slider de passo para atualizar o valor do label
        sldPasso.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                lblNumPasso.setText(Integer.toString(source.getValue()));   // Altera o label
            }
        });
        
        // Adiciona o painel de passo ao painel de sliders
        sliders.add(passo);
        
        // Painel que agrupa os elementos da definição de velocidade
        velocidade = new JPanel();
        velocidade.setLayout(new BoxLayout(velocidade, BoxLayout.Y_AXIS));
        velocidade.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        velocidade.setBackground(Color.BLACK);
        
        // Título da velocidade
        lblVel = new JLabel("Velocidade");
        lblVel.setForeground(Color.WHITE);
        lblVel.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        lblVel.setAlignmentX(Component.CENTER_ALIGNMENT);
        velocidade.add(lblVel);
        
        // Slider de velocidade
        sldVel = new JSlider(1, 10);
        sldVel.setBackground(Color.BLACK);
        velocidade.add(sldVel);

        // Valor correspondente do slider de velocidade
        lblNumVel = new JLabel(Integer.toString(sldVel.getValue()));
        lblNumVel.setForeground(Color.WHITE);
        lblNumVel.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        velocidade.add(lblNumVel);
        
        // Adiciona um listener ao slider de velocidade para atualizar o valor do label
        sldVel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                lblNumVel.setText(Integer.toString(source.getValue())); // Altera o label
            }
        });
        
        // Adiciona o painel de velocidade ao painel de sliders
        sliders.add(velocidade);
        
        // Adiciona o painel de sliders ao painel geral
        geral.add(sliders);
        
        // Painel que agrupa os botões
        botoes = new JPanel();
        botoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botoes.setBackground(Color.BLACK);
        botoes.setLayout(new FlowLayout());
        
        // Botão para iniciar a simulação
        btnSimular = new JButton("Simular");
        btnSimular.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnSimular.setForeground(Color.BLACK);
        botoes.add(btnSimular);
        
        // Adiciona um listener ao botão de simulação
        btnSimular.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSimularActionPerformed(evt);
            }
        });
        
        // Botão para explicar o problema de Josephus
        btnSobre = new JButton("Sobre");
        btnSobre.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnSobre.setForeground(Color.BLACK);
        botoes.add(btnSobre);
        
        // Adiciona um listener ao botão de sobre
        btnSobre.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSobreActionPerformed(evt);
            }
        });
        
        // Botão para encerrar o programa
        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnSair.setForeground(Color.BLACK);
        botoes.add(btnSair);
        
        // Adiciona um listener ao botão de sair
        btnSair.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                dispose();
            }
        });
        
        // Adiciona o painel de botões ao painel geral
        geral.add(botoes);
        
        // Configura o JOptionPane
        configJOptionPane();   
        
        // Adiciona o painel geral à janela
        add(geral);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Josephus");
        pack();
        setLocationRelativeTo(null); // Centraliza a janela     
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Método que define os valores iniciais dos sliders.
     * 
     * @param q Valor inicial do slider de quantidade
     * @param p Valor inicial do slider de passo
     * @param v Valor inicial do slider de velocidade
     */
    public void setValores(int q, int p, int v)
    {
        sldQtd.setValue(q);
        sldPasso.setValue(p);
        sldVel.setValue(v);
    }
    
    /**
     * Método que é chamado quando o botão de sobre é clicado.
     * 
     * @param evt Evento de clique do botão
     */
    private void btnSobreActionPerformed(java.awt.event.ActionEvent evt)
    {                   
        JOptionPane.showMessageDialog(null, "  A lenda conta que, durante uma guerra, um grupo de rebeldes foi\n  encurralado e executado; no entanto, a ordem das execuções\n  teria uma particularidade. Fizeram um círculo com os homens\n  e determinaram que iriam matá-los de k em k até que só restasse um.\n  Josephus, um dos rebeldes muito inteligente, posicionou-se exatamente\n  no lugar onde seria o sobrevivente. A escolha não foi aleatória,\n  e para ser o último, fez uso de dois parâmetros essenciais: o número total  \n  de participantes (indivíduos) e a distância entre as\n  eliminações (passo).\n\n  No jogo, é possível alterar tanto a quantidade de indivíduos quanto\n  o passo, que influencia diretamente no sobrevivente. Outra alteração,\n  que no caso não interfere no resultado, é a velocidade de execução.", "Josephus", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Método que é chamado quando o botão de simulação é clicado.
     * 
     * @param evt Evento de clique do botão
     */
    private void btnSimularActionPerformed(java.awt.event.ActionEvent evt)
    {                   
        Simulacao josephus = new Simulacao(sldQtd.getValue(), sldPasso.getValue(), sldVel.getValue());
        dispose();
    }

    /**
     * Método que configura o JOptionPane.
     */
    private void configJOptionPane()
    {
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.border", BorderFactory.createLineBorder(Color.WHITE));
        UIManager.put("OptionPane.messageFont", new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.font", new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
    }
}