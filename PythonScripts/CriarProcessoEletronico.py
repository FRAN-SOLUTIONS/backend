import sys
from playwright.sync_api import sync_playwright

def adicionarProcessoEletronico(browser_executable, pessoasInteressadas, tipoProcesso, assunto, setoresInteressados):
    with sync_playwright() as p:
        # Seleciona o navegador com base no argumento
        if browser_executable == "edge":
            browser = p.chromium.launch(executable_path=r"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe", headless=False)
        elif browser_executable == "opera":
            browser = p.chromium.launch(executable_path=r"C:\Users\rmora\AppData\Local\Programs\OperaGX\opera.exe", headless=False)
        elif browser_executable == "chrome":
            browser = p.chromium.launch(executable_path=r"C:\Users\rmora\AppData\Local\Google\Chrome\Application\chrome.exe", headless=False)
        else:
            browser = p.chromium.launch(headless=False)

        page = browser.new_page()
        page.goto("file:///C:/Users/rmora/Downloads/view-source_https___suap.ifsp.edu.br_admin_processo_eletronico_processo_add_.html")

        print("Preenchendo dados do processo...")

        # Utiliza os argumentos recebidos para preencher os campos
        page.fill("#ajaxmultiselect_input_interessados_add", pessoasInteressadas)
        # page.fill("/html/body/div[1]/main/div[3]/form/div/fieldset/div[2]/div/div[1]/input[2]", tipoProcesso)
        page.fill("#id_assunto", assunto)
        page.select_option("#id_nivel_acesso", value="2")
        page.select_option("#id_hipotese_legal", value="35")
        page.fill("#ajaxmultiselect_input_setores_interessados_add", setoresInteressados)
        page.select_option("#id_setor_criacao", value="2487")

        print("Dados preenchidos")
        input("Pressione Enter para fechar o navegador...")
        browser.close()

if __name__ == "__main__":
    # Captura os argumentos passados pelo Java
    if len(sys.argv) >= 6:
        navegador = sys.argv[1]
        pessoasInteressadas = sys.argv[2]
        tipoProcesso = sys.argv[3]
        assunto = sys.argv[4]
        setoresInteressados = sys.argv[5]
        adicionarProcessoEletronico(navegador, pessoasInteressadas, tipoProcesso, assunto, setoresInteressados)
    else:
        print("Argumentos insuficientes fornecidos.")
