from playwright.sync_api import sync_playwright

def adicionarProcessoEletronico(browser_executable):
    

    with sync_playwright() as p: 
        browser = p.chromium.launch(headless=False)
        
        if browser_executable:
            browser = p.chromium.launch(executable_path=browser_executable, headless=False)
        else:
            browser = p.chromium.launch(headless=False)  # Chromium padrão
        
        page = browser.new_page()
        # Acessa a página desejada
        page.goto("file:///C:/Users/rmora/Downloads/view-source_https___suap.ifsp.edu.br_admin_processo_eletronico_processo_add_.html")
        
        # Preenche os campos do formulário
        Pessoasinteressadas = "Evandro Lourenco, Igor Sampaio, Leonardo Motta, Marcos Verdasca, Paulo Abreu, Wendel Santos, =ALUNO EM QUESTÃO="
        tipo_processo = "Ensino: Estágio Obrigatório / Estágio Não Obrigatório / Monitoria"
        assunto = "Estágio Supervisionado Obrigatório - Aluno(a): Preencher dados do aluno - Matrícula: Preencher dados do aluno - Orientado por: André Evandro Lourenço"
        Setoresinteressados = "SCI-SPO"
        
        print("Preenchendo dados do processo... ")
        
        page.fill("#ajaxmultiselect_input_interessados_add", Pessoasinteressadas)
        #page.fill("/html/body/div[1]/main/div[3]/form/div/fieldset/div[2]/div/div[1]/input[2]", tipo_processo)
        page.fill("#id_assunto", assunto)
        # Seleciona a privacidade do processo
        
        page.select_option("#id_nivel_acesso", value="2")  #SELECIONANDO O RESTRITO
        page.select_option("#id_hipotese_legal", value="35")  #SELECIONANDO AS INFORMAÇÕES PESSOAIS....
        
        page.fill("#ajaxmultiselect_input_setores_interessados_add", Setoresinteressados)


        page.select_option("#id_setor_criacao", value="2487") #SELECIONANDO SCI-SPO

        # Aguarda para que você possa observar a ação antes de fechar o navegador
        print("Dados preenchidos")
        input("Pressione Enter para fechar o navegador...")
        browser.close()

# Executa a função

def escolherNavegador():
    edge_path = r"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"
    opera_path = r"C:\Users\rmora\AppData\Local\Programs\OperaGX\opera.exe"
    chrome_path = r"C:\Users\rmora\AppData\Local\Google\Chrome\Application\chrome.exe"

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
            
adicionarProcessoEletronico(escolherNavegador())
