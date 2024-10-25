from playwright.sync_api import sync_playwright

def pesquisar_google():
    
    with sync_playwright() as p:
        # Inicializa o navegador Chrome
        browser = p.chromium.launch(headless=False)  # Define headless=False para ver o navegador abrindo
        page = browser.new_page()

        # Abre o Google
        page.goto("https://www.google.com")
        page.wait_for_timeout(500)

        # Localiza a barra de pesquisa e insere o termo "Python automation"
        
        


        # Aguarda alguns segundos para ver o resultado (opcional)
        page.wait_for_timeout(5000)

        # Fecha o navegador
        browser.close()