import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class PythonScriptExecutor {
    public String executeScript(String scriptPath, String navegador, String pessoasInteressadas, String tipoProcesso, String assunto, String setoresInteressados) {
        StringBuilder output = new StringBuilder();
        try {
            System.out.println("Rodando script em " + scriptPath + " com argumentos.");

            // Lista com o caminho do script e os argumentos
            List<String> command = Arrays.asList(
                "python", scriptPath,
                navegador,
                pessoasInteressadas,
                tipoProcesso,
                assunto,
                setoresInteressados
            );

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            // Lê a saída do script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Espera a execução do processo terminar
            process.waitFor();

            // Exibe erros, caso existam
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errors = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errors.append(line).append("\n");
            }

            if (errors.length() > 0) {
                System.out.println("Erros durante a execução do script Python:\n" + errors);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
