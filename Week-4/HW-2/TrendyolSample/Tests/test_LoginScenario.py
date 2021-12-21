from Pages.HomePage import HomePage
from Tests.test_base import BaseTest


class TestHomePage(BaseTest):

    def test_go_airpods(self):
        self.homepage = HomePage(self.driver)
        self.homepage.click_close_popup()
        self.homepage.hover_to_login_section()
        self.homepage.click_login()
        self.homepage.send_user_credentials()
