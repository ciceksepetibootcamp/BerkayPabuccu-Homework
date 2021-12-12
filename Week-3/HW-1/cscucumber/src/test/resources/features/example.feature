
Feature: Sample Protein

  Background:
    Given Open "https://protein.tech" URL
    Then  Check if title contains text "Protein Tech"


  Scenario: Sample Protein Test

    # User should see 4 main tab section on home page.
    When Scroll to "liveTodaySection" field
    Then Element with "liveTodaySection" is displayed on website
    When Scroll to "happyPeopleSection" field
    Then Element with "happyPeopleSection" is displayed on website
    When Scroll to "ourTechSection" field
    Then Element with "ourTechSection" is displayed on website
    When Scroll to "whoWeAreSection" field
    Then Element with "whoWeAreSection" is displayed on website
    When Scroll to "pTalksSection" field
    Then Element with "pTalksSection" is displayed on website
    When Scroll to "sayHeySection" field
    Then Element with "sayHeySection" is displayed on website

    # User clicks to ‘Our Technologies’ tab.
    And Click "ourTechTopBar" element
    And Wait 2000 millisecond


  Scenario:
    # User verifies that the relevant section is opened.
    Then Element with "ourTechSection" is enable on website
    * Element with "ourTechSection" is displayed on website
    * Check if element "ourTechTopBar" has attribute "active"
    * Check if "ourTechTopBar" element's attribute value "active" equal to "active"

    # User scrolls down the page and verify Open Roles section is present.
    * Scroll to "openRolesSubSection" field
    * Check if element "openRolesSubSection" exists else print message "Roles section is not present"

    # User verify that iOS Developer position is under the Software Development section.
    * Check position "softwareDevelopment" relative to "iOSDeveloper"

    # User clicks to iOS Developer position and verify the new tab is opened.
    * Click "iOSDeveloper" element
    * Check if new tab has verified url "yazilim-test-uzmani"