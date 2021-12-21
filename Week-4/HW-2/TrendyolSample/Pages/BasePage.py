from random import random

from selenium.webdriver import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class BasePage:

    def __init__(self, driver):
        self.driver = driver
        self.action = ActionChains(self.driver)

    def find_element(self, by_locator):
        web_element = WebDriverWait(self.driver, 10).until(EC.visibility_of_element_located(by_locator))
        return web_element

    def find_elements(self, by_locator):
        web_elements = WebDriverWait(self.driver, 10).until(EC.visibility_of_element_located(by_locator))
        return web_elements

    def hover_element(self, by_locator):
        el = self.find_element(by_locator)
        self.action.move_to_element(el).perform()

    def do_click_by_locator(self, by_locator):
        self.find_element(by_locator).click()

    def do_send_keys(self, by_locator, text):
        self.find_element(by_locator).send_keys(text)

    def get_element_text(self, by_locator):
        return self.find_element(by_locator).text

    def is_enabled(self, by_locator):
        return bool(self.find_element(by_locator))

    def get_title(self, title):
        WebDriverWait(self.driver, 10).until(EC.title_is(title))
        return self.driver.title

    def assert_error_message(self, by_locator, expected):
        element = self.get_element_text(by_locator)
        assert element == expected

    def random_pick(self, by_locator):
        elements = self.find_elements(by_locator)
        element = random.choice(elements)
        element.click()
