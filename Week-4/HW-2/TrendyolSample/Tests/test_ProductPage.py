from Pages.ProductPage import ProductPage
from Tests.test_base import BaseTest


class TestProductPage(BaseTest):

    def test_product_sections(self):
        self.productPage = ProductPage(self.driver)
        self.productPage.click_random_product()
