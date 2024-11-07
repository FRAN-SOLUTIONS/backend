from playwright.sync_api import sync_playwright

# Caminhos dos navegadores
edge_path = r"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"
opera_path = r"C:\Users\rmora\AppData\Local\Programs\OperaGX\opera.exe"
chrome_path = r"C:\Users\rmora\AppData\Local\Google\Chrome\Application\chrome.exe"

# Função para abrir a página e esperar pelo login manual
def abrirSUAP(browser_executable):
    prontuario = 'SP'  # Você pode preencher o campo de prontuário, mas a senha fica com o usuário
    
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
        print("Login detectado!")
        print("Indo para página de adicionar processos...")

        page.goto("https://suap.ifsp.edu.br/admin/processo_eletronico/processo/add/")

        # Verifica se o usuário é coordenador
        if verificarCoordenador(page):
            print("Você tem acesso para adicionar processos.")
            input("Pressione enter para criar um processo eletrônico aleatório")
            criarProcessoEletronico(page)  # Chama a função para adicionar o processo eletrônico
        else:
            print("A página identificada não possui as funções de coordenador - Provavelmente entrou no sistema como aluno.")

        # Aguarda indefinidamente para visualização
        input("Pressione Enter para fechar o navegador...")

        # Fecha o navegador
        browser.close()


def criarProcessoEletronico(page):
    pessoasInteressadas = "Evandro Lourenco, Igor Sampaio, Leonardo Motta, Marcos Verdasca, Paulo Abreu, Wendel Santos, =ALUNO EM QUESTÃO="
    tipoProcesso = "Ensino: Estágio Obrigatório / Estágio Não Obrigatório / Monitoria"
    assunto = "Estágio Supervisionado Obrigatório - Aluno(a): Preencher dados do aluno - Matrícula: Preencher dados do aluno - Orientado por: André Evandro Lourenço"
    setoresInteressados = "SCI-SPO"

    print("Preenchendo dados do processo...")

    # Utiliza os argumentos recebidos para preencher os campos
    page.fill("#ajaxmultiselect_input_interessados_add", pessoasInteressadas)
    page.fill("#id_assunto", assunto)
    page.select_option("#id_nivel_acesso", value="2")
    page.select_option("#id_hipotese_legal", value="35")
    page.fill("#ajaxmultiselect_input_setores_interessados_add", setoresInteressados)
    page.select_option("#id_setor_criacao", value="2487")

    print("Dados preenchidos")


def verificarCoordenador(page):
    print("Verificando se o usuário é coordenador...")

    # Verifica se os campos de entrada para criar um processo estão presentes
    botao_interessados = page.locator("#ajaxmultiselect_input_interessados_add")
    campo_assunto = page.locator("#id_assunto")

    # Retorna True se ambos os campos estiverem presentes na página
    return botao_interessados.count() > 0 and campo_assunto.count() > 0


def escolherNavegador():
    # Solicita ao usuário qual navegador usar
    opcao = int(input("Qual dos navegadores você quer usar?\n 1 - Edge\n 2 - Opera\n 3 - Chrome\n 4 - Chromium (recomendado)\n "))

    # Seleciona o navegador com base na opção do usuário
    while True:
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
