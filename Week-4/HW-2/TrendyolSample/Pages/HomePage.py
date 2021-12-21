from Pages.BasePage import BasePage
from selenium.webdriver.common.by import By
from Config.config import TestData


class HomePage(BasePage):
    closePopUp = (By.CSS_SELECTOR, "div[class='modal-close']")
    hoverToLoginSection = (By.CSS_SELECTOR, "div[class='link account-user']")
    loginButton = (By.CSS_SELECTOR, "div[class='login-button']")
    userName = (By.CSS_SELECTOR, "input[id='login-email']")
    password = (By.CSS_SELECTOR, "input[id='login-password-input']")
    submitLogIn = (By.CSS_SELECTOR, "button[type='submit']")
    errorMessage = (By.CSS_SELECTOR, "span[class='message']")

    def __init__(self, driver):
        super().__init__(driver)
        self.driver.get(TestData.BASE_URL)

    def click_close_popup(self):
        self.do_click_by_locator(self.closePopUp)

    def hover_to_login_section(self):
        self.hover_element(self.hoverToLoginSection)

    def click_login(self):
        self.do_click_by_locator(self.loginButton)

    def send_user_credentials(self):
        expected_error = "E-posta adresiniz ve/veya şifreniz hatalı."
        self.do_send_keys(self.userName, "berkaypabuccu@test.com")
        self.do_send_keys(self.password, "test1234")
        self.do_click_by_locator(self.submitLogIn)
        self.assert_error_message(self.errorMessage, expected_error)
