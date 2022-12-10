package projeto;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Bingo_Amarelo {

    public static int tamanho_Cartela = 5;
    public static int num_rodadas = 60;

    public static void main(String[] args) {

        Random random = new Random();

        int num_jogadores = intro();
        String[] jogadores = Array_jogadores(num_jogadores);
        int manualOuRandom = escolhaModoCartela();

        int[][] Cartelas_Bingo = new int[num_jogadores][tamanho_Cartela];
        int[] Pontos = new int[num_jogadores];
        int[][] Pontos_marcados = new int[1][num_jogadores];
        int[] tabela = new int[num_rodadas];
        int[] tabela_sorteados = new int[num_rodadas];

        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = i + 1;
        }

        for (int i=0; i < (tabela.length - 1); i++) {
            int j = random.nextInt(tabela.length);
            int b = tabela[i];
            tabela[i] = tabela[j];
            tabela[j] = b;
        }

        for (int i = 0; i < jogadores.length; i++) {
            switch (manualOuRandom) {
                case 1:
                    Cartelas_Bingo[i] = modoRandom();
                    break;
                case 2:
                    System.out.printf("Digite os números da cartela de %s :\n", jogadores[i]);
                    Cartelas_Bingo[i] = modoManual();
                    break;
                default:
                    manualOuRandom = escolhaModoCartela();
                    i--;
                    break;
            }
        }

        for (int i = 0; i < num_rodadas; i++) {
            int turnos = proximoTurno();
            int rodada = i + 1;
            switch (turnos) {
                case 1:
                    int num_escolhido = sorteio(tabela, i);

                    tabela_sorteados[rodada] = num_escolhido;
                    System.out.printf("\n%d° rodada - Numero sorteado: %d\n", rodada, num_escolhido);
                    Pontos_marcados[0] = tabelaPontosMarcados(num_escolhido, jogadores, Cartelas_Bingo, Pontos);
                    boolean bingo = fazerBingo(Pontos_marcados);
                    if (bingo) {
                        for (int j = 0; j < Pontos_marcados[0].length; j++) {
                            if (Pontos_marcados[0][j] == tamanho_Cartela) {
                                System.out.printf("\nBINGO!!!!!, Parabéns %s.", jogadores[j]);
                                String vencedor = jogadores[j];
                                ranking(rodada, vencedor, tabela_sorteados, Cartelas_Bingo, num_jogadores,
                                        jogadores);
                                exit(0);
                            }
                        }
                    }
                    break;
                case 2:
                    exit(0);
                    break;
                case 3:
                    nomes_Cartelas(jogadores, Cartelas_Bingo);
                    i--;
                    break;
                case 4:
                    Instrucoes();
                    i--;
                    break;
                default:
                    i--;
                    break;
            }
        }

    }

    public static int intro() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "$************$ Bem Vindo(a) ao Bingo Amarelo $************$" + "\n");
        System.out.println("Modo Multiplayer. Digite o número de jogadores: ");
        int num_jogadores = sc.nextInt();
        return num_jogadores;
    }

    public static String[] Array_jogadores(int qtd_jogadores) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite os nomes dos participantes, separando - os por hifen: ");
        String entrada = sc.nextLine();
        String[] participantes = entrada.split("-");

        System.out.printf("\nParticipantes do Bingo: %s\n", Arrays.toString(participantes));
        return participantes;
    }

    public static int escolhaModoCartela() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nA cartela será escolhida de forma manual ou random?");
        System.out.println("1- RANDOM");
        System.out.println("2- MANUAL");
        int escolha = sc.nextInt();
        return escolha;
    }

    public static int[] modoRandom() {
        Random random = new Random();
        int[] cartelaR = new int[tamanho_Cartela];
        int[] tabela = new int[num_rodadas];

        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = i + 1;
        }

        for (int i = 0; i < (tabela.length - 1); i++) {
            int j = random.nextInt(tabela.length);
            int a = tabela[i];
            tabela[i] = tabela[j];
            tabela[j] = a;
        }
        for (int i = 0; i < tamanho_Cartela;i++) {
            cartelaR[i] = sorteio(tabela,i);
        }
        return cartelaR;
    }

    public static int[] modoManual() {
        Scanner sc = new Scanner(System.in);
        String num1 = sc.nextLine();
        String[] numeros = num1.split(",");
        int[] Cartela1 = new int[tamanho_Cartela];
        for (int i = 0; i < tamanho_Cartela; i++) {
            Cartela1[i] = Integer.parseInt(numeros[i]);
        }
        return Cartela1;
    }

    public static void nomes_Cartelas(String[] participantes, int[][] cartelasDoBingo) {
        String jog = "jogadores";
        String cart = "cartela";
        System.out.printf("\n%-15S%S", jog, cart);
        for (int i = 0; i < participantes.length; i++) {
            System.out.printf("\n%-15s", participantes[i]);
            for (int j = 0; j < cartelasDoBingo[i].length; j++) {
                System.out.printf("%d\t", cartelasDoBingo[i][j]);
            }
        }
    }

    private static void Instrucoes() {
        System.out.println("\n" + "******INSTRUÇÕES******" + "\n");
        System.out.println("\n");
        System.out.println("Pressione 1 para continuar o Bingo.");
        System.out.println("Pressione 2 para finalizar o jogo. ");
        System.out.println("Pressione 3 para visualizar as cartelas.");
        System.out.println("Pressione 4 para visualizar as instruções.");
    }

    public static int sorteio(int[] sorteioAleatorio, int indice) {
        return sorteioAleatorio[indice];
    }

    public static int proximoTurno() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n1- Sortear os números. ");
        System.out.println("2- Fim.");
        System.out.println("3- Visualizar cartela.");
        System.out.println("4- Instruções do Jogo.");
        int escolha2 = sc.nextInt();
        return escolha2;
    }

    public static int[] tabelaPontosMarcados(int esc_num, String[] part, int[][] num_cartela, int[] pontosPreenchidos) {
        for (int i = 0; i < num_cartela.length; i++) {
            for (int j = 0; j < num_cartela[i].length; j++) {
                if (esc_num == num_cartela[i][j]) {
                    pontosPreenchidos[i] += 1;
                }
            }
            if (pontosPreenchidos[i] > 0) {
                System.out.printf("\nJogador(a) %s marcou %d ponto(s).", part[i], pontosPreenchidos[i]);
            }
        }
        return pontosPreenchidos;
    }

    public static boolean fazerBingo(int[][] pontos_marcados) {
        for (int i = 0; i < pontos_marcados.length; i++) {
            for (int j = 0; j < pontos_marcados[i].length; j++) {
                if (pontos_marcados[i][j] == tamanho_Cartela) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void ranking(int qtd_rodadas, String ganhador, int[] tabela_sorteados, int[][] cartela,
                               int num_jogadores, String[] jogadores) {
        System.out.println("\n" + "*****Ranking Geral*****" + "\n");
        System.out.printf("\nQuantidade de rodadas: %d.", (qtd_rodadas));
        System.out.printf("\nJogador(a) vencedor(a): %s.", ganhador);

        System.out.println("\nNúmeros da Cartela Premiada:");
        int num_vencedor = 0;

        for (int j = 0; j < num_jogadores; j++){
            if (ganhador == jogadores[j]){
                num_vencedor = j;
            }
        }

        for (int j1 =0; j1 < 5; j1++){
            System.out.println(cartela[num_vencedor][j1]);
        }

        System.out.println("\nNúmeros sorteados: ");
        for (int i = 0; i < tabela_sorteados.length; i++){
            if (tabela_sorteados[i] > 0){
                System.out.println(tabela_sorteados[i]);
            }
        }





    }

}
