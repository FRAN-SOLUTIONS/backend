import java.util.Scanner;

public class PythonScriptRunner {
    public static void main(String[] args) {
        PythonScriptExecutor executor = new PythonScriptExecutor();

        // Caminho do script Python
        String scriptPath = "CriarProcessoEletronico.py";

        // Solicita ao usuário qual navegador usar
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual dos navegadores você quer usar?");
        System.out.println("1 - Edge");
        System.out.println("2 - Opera");
        System.out.println("3 - Chrome");
        System.out.println("4 - Chromium (recomendado)");
        int opcao = scanner.nextInt();

        String navegador = "";
        switch (opcao) {
            case 1:
                navegador = "edge";
                break;
            case 2:
                navegador = "opera";
                break;
            case 3:
                navegador = "chrome";
                break;
            case 4:
                navegador = "chromium";
                break;
            default:
                System.out.println("Opção inválida. Usando Chromium por padrão.");
                navegador = "chromium";
        }

        // Valores a serem passados para o script Python
        String pessoasInteressadas = "Evandro Lourenco, Igor Sampaio, Leonardo Motta, Marcos Verdasca, Paulo Abreu, Wendel Santos, =ALUNO EM QUESTÃO=";
        String tipoProcesso = "Ensino: Estágio Obrigatório / Estágio Não Obrigatório / Monitoria";
        String assunto = "Estágio Supervisionado Obrigatório - Aluno(a): Preencher dados do aluno - Matrícula: Preencher dados do aluno - Orientado por: André Evandro Lourenço";
        String setoresInteressados = "SCI-SPO";

        // Executa o script com os argumentos e imprime a saída
        String result = executor.executeScript(scriptPath, navegador, pessoasInteressadas, tipoProcesso, assunto, setoresInteressados);
        System.out.println("Saída do script Python:\n" + result);

        scanner.close();
    }
}
