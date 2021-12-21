import pytest
from selenium import webdriver


@pytest.fixture(params=["chrome"], scope="class")
def init_driver(request):
    if request.param == "chrome":
        web_driver = webdriver.Chrome(executable_path="chromedriver.exe")
    request.cls.driver = web_driver
    yield
    web_driver.close()
    web_driver.quit()
