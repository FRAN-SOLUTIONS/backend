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
        
        
        
        print("Procurando botão de adicionar processo eletrônico...")
        #se a página tiver este botão, abrir e dar um print falando que está entrando no sistema como coordenador
        page.click('a[href="/admin/processo_eletronico/processo/add/"]')
        #se não tiver, dar print falando que entrou como aluno

        if(verificarCoordenador(page)):
            print("a")
            #adicionarProcessoEletronico(page)
        else:
            print("Não possui botão de criar processo - Provavelmente não entrou na página como coordenador")#Verificando se tem o botão de clicar o botão
        
        # Aguarda indefinidamente para visualização
        input("Pressione Enter para fechar o navegador...")

        # Fecha o navegador
        browser.close()
        


def verificarCoordenador(page):
    with sync_playwright() as p: 

        print("Procurando botão de adicionar processo eletrônico...")

        # Verifica se o botão está presente na página
        botao_adicionar = page.locator('a[href="/admin/processo_eletronico/processo/add/"]')
            
        if botao_adicionar.count() > 0:
           botao_adicionar.click()
           return (True)
        else:
            # Caso contrário, mostre a mensagem alternativa
            return (False)
            # Executa a função
        
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


