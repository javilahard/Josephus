package gui;

import armazenamento.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe responsável por simular o problema de Josephus.
 * 
 * @author Igor Costa; Joao Avila; Priscilla de Souza; Larissa Hipolito
 * @version 18/06/2024
 */
public class Simulacao extends JFrame
{
    private JPanel geral; // Painel que agrupa todos os objetos da janela
    private JPanel individuos; // Painel que representa os indivíduos
    private JPanel[][] matriz; // Matriz de painéis que representam os indivíduos
    private JLabel[][] lblIgors; // Matriz de labels que representam os indivíduos
    private JPanel botoes; // Painel que agrupa os botões
    private JLabel lblTitulo; // Label que exibe o título da janela
    private JLabel lblMortoT; // Label que exibe o texto "Igor executado:"
    private JLabel lblMortoNum; // Label que exibe o número do indivíduo executado
    private JButton btnControle; // Botão que controla a simulação
    private JButton btnConfigurar; // Botão que configura a simulação
    private JButton btnDados; // Botão que exibe os dados da simulação
    private int qtd; // Quantidade de indivíduos
    private int passo; // Passo da simulação
    private int velocidade; // Velocidade da simulação
    private int controleMortos; // Contador de mortos
    private String listaMortos; // Lista de mortos
    private ListaDuplamenteLigadaCircular l; // Lista duplamente ligada circular que representa a fila de indivíduos
    private volatile boolean pausado = true; // Flag que indica se a simulação está pausada
    private volatile boolean comecou = false; // Flag que indica se a simulação já começou
    
    /**
     * Construtor da classe Simulacao.
     * 
     * @param i quantidade de indivíduos
     * @param p passo da simulação
     * @param v velocidade da simulação
     */
    public Simulacao(int i, int p, int v)
    {
        qtd = i;
        passo = p;
        velocidade = v;
        iniciarLista();
        iniciarComponentes();
        listaMortos = "";
        controleMortos = 0;
        simular();
    }
    
    /**
     * Inicializa os componentes da janela.
     */
    private void iniciarComponentes()
    {
        // Cria o painel geral que agrupa todos os objetos da janela
        geral = new JPanel();
        geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        geral.setBackground(Color.BLACK);
    
        // Cria o label que exibe o título da janela
        lblTitulo = new JLabel("SIMULADOR JOSEPHUS");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("PERPETUA TITLING MT", Font.BOLD, 20));
        geral.add(lblTitulo);
    
        // Cria a matriz de painéis que representam os indivíduos
        setMatriz();
    
        // Cria o painel que agrupa os botões
        botoes = new JPanel();
        botoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botoes.setBackground(Color.BLACK);
        botoes.setLayout(new FlowLayout());
    
        // Cria os labels que exibem o texto "Igor executado:" e o número do indivíduo executado
        lblMortoT = new JLabel();
        lblMortoT.setForeground(Color.WHITE);
        lblMortoT.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        lblMortoT.setVisible(false);
        botoes.add(lblMortoT);
    
        lblMortoNum = new JLabel();
        lblMortoNum.setForeground(Color.WHITE);
        lblMortoNum.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        lblMortoNum.setVisible(false);
        botoes.add(lblMortoNum);
    
        // Cria os botões que controlam a simulação
        btnControle = new JButton("Iniciar");
        btnControle.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnControle.setForeground(Color.BLACK);
        botoes.add(btnControle);
    
        // Adiciona um listener ao botão "Iniciar"
        btnControle.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnControleActionPerformed(evt);
            }
        });
    
        // Cria os botões que configuram a simulação e exibem os dados
        btnConfigurar = new JButton("Configurar");
        btnConfigurar.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnConfigurar.setForeground(Color.BLACK);
        botoes.add(btnConfigurar);
    
        btnConfigurar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnConfigurarActionPerformed(evt);
            }
        });
    
        btnDados = new JButton("Dados");
        btnDados.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 15));
        btnDados.setForeground(Color.BLACK);
        botoes.add(btnDados);
    
        btnDados.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDadosActionPerformed(evt);
            }
        });
    
        // Adiciona o painel de botões ao painel geral
        geral.add(botoes);
    
        // Adiciona o painel geral à janela
        add(geral);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Josephus");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Cria a matriz de painéis que representam os indivíduos.
     */
    private void setMatriz()
   {
        individuos = new JPanel();
        individuos.setLayout(new GridLayout(10, 10));
        individuos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        matriz = new JPanel[10][10];
        lblIgors = new JLabel[10][10];
        ImageIcon imageIcon = new ImageIcon(Simulacao.class.getResource("/imagens/vivo.jpg"));  // Carrega a imagem da pasta
        
        int k = qtd;
        int h = 1;
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                matriz[i][j] = new JPanel();
                lblIgors[i][j] = new JLabel();
                if(h <= k)  // Se tiver no intervalo de entrada, coloca a imagem
                {
                    lblIgors[i][j].setIcon(imageIcon);
                    matriz[i][j].add(lblIgors[i][j]);
                    h++;
                }
                matriz[i][j].setBackground(Color.BLACK); // Quadrado preto
                matriz[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adiciona uma borda preta
                individuos.add(matriz[i][j]);   
            }
        }
        individuos.setBackground(Color.BLACK); // Plano de fundo preto para nao aparecer os outro quadrados
        geral.add(individuos);
    }
    
    /**
     * Trata o evento de clique no botão "Iniciar".
     * 
     * @param evt evento de clique
     */
    private void btnControleActionPerformed(java.awt.event.ActionEvent evt)
    {
        if(pausado ||!comecou)  // Programa esta rodando
        {
            comecou = true;
            lblMortoT.setVisible(true);
            lblMortoNum.setVisible(true);
            pausado = false;
            btnControle.setMinimumSize(btnControle.getSize());
            btnControle.setText("Parar");
            btnConfigurar.setEnabled(false);
            btnDados.setEnabled(false);
        }
        else    // Programa pausado
        {
            pausado = true;
            btnControle.setText("Retomar");
            btnConfigurar.setEnabled(true);
            btnDados.setEnabled(true);
        }    
    }
    
    /**
     * Trata o evento de clique no botão "Configurar".
     * 
     * @param evt evento de clique
     */
    private void btnConfigurarActionPerformed(java.awt.event.ActionEvent evt)
    {                   
        Configuracao c = new Configuracao();
        c.setValores(qtd, passo, velocidade);
        dispose();
    }
    
    /**
     * Trata o evento de clique no botão "Dados".
     * 
     * @param evt evento de clique
     */
    private void btnDadosActionPerformed(java.awt.event.ActionEvent evt)
    {                   
        String dados;
        dados = "Nº inicial de indivíduos: " + qtd +
                "\nPasso da simulação: " + passo +
                "\nVelocidade nível " + velocidade;
        String mortos = "\nSequência de mortos: \n" + listaMortos;
        JOptionPane.showMessageDialog(null, dados + mortos, "Josephus", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Inicializa a lista duplamente ligada circular.
     */
    private void iniciarLista()
    {
        l = new ListaDuplamenteLigadaCircular();
        
        for(int i = 0; i < qtd; i++)
        {
            l.inserirFim(i);
        }
    }
    
    /**
     * Simula o problema de Josephus.
     */
    private void simular()
    {
        Thread simulando = new Thread()
        {
            public void run() 
            {
                while(true)
                {
                    if (!pausado &&!fimSim())   // Faz a simulacao so quando o programa nao tiver pausado nem tiver chegado ao fim
                    {
                        josephus();
                    }
        
                    try
                    {
                        Thread.sleep(1100-100*velocidade);  // Espera pelo tempo definido pela velocidade
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        simulando.start();
    }
    
    /**
     * Executa um passo da simulação.
     */
    private void josephus()
    {
        int k = 1;
        
        No aux = l.getInicio();
        
        while(k < passo)    // Pega o proximo elemento da lista a partir do passo
        {
            aux = aux.getProximo();
            k++;
        }
        
        // Atualiza a lista ligada e a matriz com o conteudo
        l.setInicio(aux.getProximo());  
        l.setFim(aux);
        aux = l.removerFim();
        atualizarMatriz(aux.getConteudo());
        indicarMorto(aux.getConteudo());
        atualizarListaMorto(aux.getConteudo());
    }
    
    /**
     * Atualiza a matriz de painéis com o resultado da simulação.
     * 
     * @param valor valor do indivíduo que foi executado
     */
    private void atualizarMatriz(Object valor)
    {
        int i = Integer.parseInt(valor.toString())/10;
        int j = Integer.parseInt(valor.toString())%10;
        
        if(!fimSim())
        {
            matriz[i][j].setBackground(Color.RED);
            ImageIcon imageIcon = new ImageIcon(Simulacao.class.getResource("/imagens/morto.jpg"));
            lblIgors[i][j].setIcon(imageIcon);
        }
        else
        {
            matriz[i][j].setBackground(Color.CYAN);
            ImageIcon imageIcon = new ImageIcon(Simulacao.class.getResource("/imagens/sobrevivente.jpg"));
            lblIgors[i][j].setIcon(imageIcon);
        }
    }
    
    /**
     * Indica o indivíduo que foi executado.
     * 
     * @param morto valor do indivíduo que foi executado
     */
    private void indicarMorto(Object morto)
    {
        if(!fimSim())
        {
            lblMortoT.setText("Igor executado:");
            lblMortoNum.setForeground(Color.RED);
        }
        else
        {
            lblMortoT.setText("Igor sobrevivente:");
            lblMortoNum.setForeground(Color.CYAN);
        }
        lblMortoNum.setText(String.valueOf(Integer.parseInt(morto.toString())+1));
    }

    /**
     * Atualiza a lista de mortos.
     * 
     * @param morto valor do indivíduo que foi executado
     */
    private void atualizarListaMorto(Object morto)
    {
        controleMortos++;
        
        if(controleMortos!= qtd)
        {
            if(controleMortos%10 == 0 ) // Quebra de linha na string quando tiver 10 elementos nesta
            {
                listaMortos = listaMortos + "\n";
            }
            listaMortos = listaMortos + " " + String.valueOf(Integer.parseInt(morto.toString())+1) + ";";
        }
    }
    
    /**
     * Verifica se a simulação terminou.
     * 
     * @return true se a simulação terminou, false caso contrário
     */
    private boolean fimSim()
    {
        if(l.getQtdNos()!= 0)
        {
            return false;
        }
        btnControle.setEnabled(false);
        btnConfigurar.setEnabled(true);
        btnDados.setEnabled(true);
        return true;
    }
}