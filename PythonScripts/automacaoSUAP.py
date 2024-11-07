from playwright.sync_api import sync_playwright


with sync_playwright() as p:
    browser = p.chromium.launch(headless=False)  # Chromium padrão
    page = browser.new_page()
    page.goto("https://suap.ifsp.edu.br/admin/processo_eletronico/processo/?opcao=1")