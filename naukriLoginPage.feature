@naukriLoginPage
  Feature: User login to the naukri login page and refresh.

    @LoginWithArifuddinProfile
    Scenario: Login to Arifuddin profile to check job profiles
      Given 'mdarif.hyd@gmail.com' logs in the naukri system with 'test@1234'
      #Given a user navigates to 'Account Groups' page
      #When user select the filters dropdown
