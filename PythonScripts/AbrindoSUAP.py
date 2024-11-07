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
            criarProcessoEletronico(page)  # Chama a função para adicionar o processo eletrônico
        else:
            print("A página identificada não possui as funções de coordenador - Provavelmente entrou no sistema como aluno.")

        # Aguarda indefinidamente para visualização
        input("Pressione Enter para fechar o navegador...")

        # Fecha o navegador
        browser.close()


def criarProcessoEletronico(page):


    tipoProcesso = "Ensino: Estágio Obrigatório / Estágio Não Obrigatório / Monitoria"

    assunto = "Estágio Supervisionado Obrigatório - Aluno(a): Preencher dados do aluno - Matrícula: Preencher dados do aluno - Orientado por: André Evandro Lourenço"

    print("Preenchendo dados do processo...")

    # Utiliza os argumentos recebidos para preencher os campos
    pessoasInteressadas = ["SP3072037", "1323349", "1753846", "1284708", "1515722", "2297124", "1190702"]  

    for coordenador in pessoasInteressadas:
        # Ação para buscar e selecionar o coordenador pelo ID
        campo_interessados = page.locator("#ajaxmultiselect_input_interessados_add")  # Ajuste o seletor conforme necessário
        # Preenche o campo de pesquisa com o ID do coordenador

        campo_interessados.fill(coordenador)
        page.wait_for_timeout(500)  # Aguarda o tempo suficiente para as opções aparecerem
        campo_interessados.click()  
        campo_interessados.click()  

        # Aguarda o carregamento das opções (ajuste o tempo se necessário)
        page.wait_for_timeout(500)  # Aguarda o tempo suficiente para as opções aparecerem
        # Seleciona o coordenador na lista (ajuste conforme o seletor real)
        option_locator = page.locator(f'li:has-text("{coordenador}")')  # Ajuste o seletor conforme o conteúdo
        page.wait_for_timeout(500)  # Aguarda o tempo suficiente para as opções aparecerem

        option_locator.click()  # Clica na opção correspondente

        print(f"Coordenador com ID {coordenador} selecionado.")


    # Preencher o campo Tipo de Processo
    page.click("#__tipo_processo__")    
    # Espera o popup carregar
    page.wait_for_selector(".popupChoiceFieldWidget", timeout=5000)  # Ajuste o seletor se necessário
    page.click("#opcao_desejada_565")
    print(f"Tipo de Processo '{tipoProcesso}' selecionado.")

    # Clicar no botão de tipo 'submit' com o valor 'Confirmar'
    page.locator("input[type='submit'][value='Confirmar']").click()


    page.wait_for_timeout(500)  # Aguarda o tempo suficiente para as opções aparecerem
    page.fill("#id_assunto", assunto)

    
    nivel_acesso_select = page.locator("#id_nivel_acesso")
    nivel_acesso_select.scroll_into_view_if_needed()

    nivel_acesso_select.click()
    # Espera o carregamento das opções dentro do select (opções devem aparecer depois do clique)

    # Espera até que as opções estejam visíveis e estáveis
    page.wait_for_selector('option', state='visible', timeout=5000)  # Aguarda até 5 segundos pelas opções ficarem visíveis
   # Agora, tenta clicar na opção "Restrito"
    restrito_option = page.locator('option', has_text="Restrito")

    # Rola até a opção "Restrito" se necessário para torná-la    visível
    restrito_option.scroll_into_view_if_needed()
    selected_value = nivel_acesso_select.input_value()
    print(f"Opção de Nível de Acesso selecionada: {selected_value}")
    



    select_hipotese = page.locator("#id_hipotese_legal")
    select_hipotese.scroll_into_view_if_needed()

    # Aguarda o carregamento das opções da hipótese legal após a seleção de "Restrito"
    page.wait_for_selector("#id_hipotese_legal", timeout=3000)  # Aguarda até 3 segundos pelo elemento

    # Clica no select para abrir as opções de hipótese legal
    page.locator("#id_hipotese_legal").click()

    # Aguarda o DOM ser carregado, ou use 'networkidle' se mais robustez for necessária
    page.wait_for_load_state('domcontentloaded')

    # Rola até o campo da "Hipótese Legal" para garantir que as opções estejam visíveis

    # Agora, selecione a opção com o texto "Informação Pessoal - dados pessoais"
    option_text = "Informação Pessoal - dados pessoais"
    informacao_pessoal_option = page.locator('option', has_text=option_text)

    # Rola até a opção para garantir que ela esteja visível
    informacao_pessoal_option.scroll_into_view_if_needed()

    # Clica na opção "Informação Pessoal - dados pessoais"
    informacao_pessoal_option.click()

    # Verifica o valor da opção selecionada
    selected_value = page.locator("#id_hipotese_legal").input_value()
    print(f"Opção selecionada: {selected_value}")


    
    # Primeiro, rola até o campo de seleção para garantir que ele esteja visível na tela
    setor_criacao_select = page.locator("#id_setor_criacao")
    setor_criacao_select.scroll_into_view_if_needed()

    # Agora, realiza a seleção do valor desejado (value="2487")
    page.select_option("#id_setor_criacao", value="2487")

    # Verifica qual valor foi selecionado (se necessário)
    selected_value = page.locator("#id_setor_criacao").input_value()
    print(f"Valor selecionado no Setor de Criação: {selected_value}")


    print("Dados preenchidos")
    input("Aperte enter para terminar")


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
