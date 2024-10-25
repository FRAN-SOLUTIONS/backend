from playwright.sync_api import sync_playwright

# Caminhos dos navegadores
edge_path = r"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"
opera_path = r"C:\Users\rmora\AppData\Local\Programs\OperaGX\opera.exe"
chrome_path = r"C:\Users\rmora\AppData\Local\Google\Chrome\Application\chrome.exe"

# Função para abrir a página e esperar pelo login manual
def abrirSUAP(browser_executable):
    prontuario = 'SP3072037'  # Você pode preencher o campo de prontuário, mas a senha fica com o usuário
    
    with sync_playwright() as p:
        # Lança o navegador correspondente
        if browser_executable:
            browser = p.chromium.launch(executable_path=browser_executable, headless=False)
        else:
            browser = p.chromium.launch(headless=False)  # Chromium padrão
        
        page = browser.new_page()

        # Acessa a página de login
        page.goto("https://suap.ifsp.edu.br/accounts/login/?next=/")

        # Preenche apenas o campo de prontuário
        page.fill("#id_username", prontuario)
        page.fill("#id_password", "")
        
        # Aguarda o usuário digitar a senha e pressionar "Acessar" manualmente
        print("Por favor, digite a sua senha e faça login manualmente.")
        
        # Espera a URL mudar para o dashboard após o login
        page.wait_for_url("https://suap.ifsp.edu.br/")  # Altere conforme o redirecionamento do SUAP

        # Continuação da automação após o login
        print("Login detectado! Continuando com a automação...")
        page.goto("https://suap.ifsp.edu.br/admin/processo_eletronico/processo/?opcao=1")

        

        # Aguarda indefinidamente para visualização
        input("Pressione Enter para fechar o navegador...")

        # Fecha o navegador
        browser.close()

def escolherNavegador():
# Solicita ao usuário qual navegador usar
    opcao = int(input("Qual dos navegadores você quer usar?\n 1 - Edge\n 2 - Opera\n 3 - Chrome\n 4 - Chromium (recomendado)\n "))

    # Seleciona o navegador com base na opção do usuário
    while(True):
        if opcao == 1:
            return edge_path
        elif opcao == 2:
            return opera_path
        elif opcao == 3:
            return chrome_path
        elif opcao == 4:
            return 
        else:
            print("Opção inválida. Por favor, escolha uma opção válida.")

abrirSUAP(escolherNavegador())