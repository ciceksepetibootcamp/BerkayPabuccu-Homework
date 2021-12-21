import random

from Pages.BasePage import BasePage
from selenium.webdriver.common.by import By
from Config.config import TestData


class ProductPage(BasePage):
    topBarMenCategory = (By.CSS_SELECTOR, "ul li[class='tab-link']:nth-of-type(2) a[class='category-header']")
    stickyWrapper = (By.CSS_SELECTOR, "div article[class='component-item']")
    closePopUp = (By.CSS_SELECTOR, "div[class='modal-close']")
    random_product = f'div article[class="component-item"])[{random.randint(1, 5)}]'

    def __init__(self, driver):
        super().__init__(driver)
        self.driver.get(TestData.BASE_URL)

    def click_random_product(self):
        self.do_click_by_locator(self.closePopUp)
        self.do_click_by_locator(self.topBarMenCategory)
        self.is_enabled(self.stickyWrapper)
        self.do_click_by_locator(self.stickyWrapper)
