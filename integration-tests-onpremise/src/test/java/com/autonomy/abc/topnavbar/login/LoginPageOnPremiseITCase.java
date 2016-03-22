package com.autonomy.abc.topnavbar.login;

import com.autonomy.abc.base.SOTestBase;
import com.hp.autonomy.frontend.selenium.config.TestConfig;
import com.autonomy.abc.selenium.iso.OPISOElementFactory;
import com.autonomy.abc.selenium.users.OPAccount;
import com.autonomy.abc.selenium.users.OPLoginPage;
import com.autonomy.abc.selenium.users.UserService;
import com.autonomy.abc.selenium.users.UsersPage;
import com.hp.autonomy.frontend.selenium.util.Waits;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hp.autonomy.frontend.selenium.framework.state.TestStateAssert.assertThat;
import static com.hp.autonomy.frontend.selenium.framework.state.TestStateAssert.verifyThat;
import static com.hp.autonomy.frontend.selenium.matchers.ControlMatchers.url;
import static com.hp.autonomy.frontend.selenium.matchers.ControlMatchers.urlContains;
import static com.hp.autonomy.frontend.selenium.matchers.ElementMatchers.hasAttribute;
import static com.hp.autonomy.frontend.selenium.matchers.ElementMatchers.modalIsDisplayed;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class LoginPageOnPremiseITCase extends SOTestBase {

	public LoginPageOnPremiseITCase(final TestConfig config) {
		super(config);
	}

	private OPLoginPage loginPage;

	@Override
	public OPISOElementFactory getElementFactory() {
		return (OPISOElementFactory) super.getElementFactory();
	}


	@Before
	public void setUp() throws InterruptedException {
        UserService userService = getApplication().userService();
		UsersPage usersPage = userService.goToUsers();
		userService.deleteOtherUsers();
		usersPage.createUserButton().click();
		assertThat(usersPage, modalIsDisplayed());
		usersPage.createNewUser("admin", "qwerty", "Admin");
		usersPage.closeModal();
		getApplication().loginService().logout();
		loginPage = getElementFactory().getLoginPage();
	}

	@Test
	public void testLoginAsNewlyCreatedAdmin() {
        loginPage.loginWith(new OPAccount("admin", "qwerty"));
		new WebDriverWait(getDriver(), 10).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".initial-loading-indicator")));
		assertThat("Overview page has not loaded", getWindow(), urlContains("overview"));
	}

	@Test
	public void testLoginNotCaseSensitive() {
        loginPage.loginWith(new OPAccount("ADmIn", "qwerty"));
		new WebDriverWait(getDriver(), 10).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".initial-loading-indicator")));
		assertThat("Overview page has not loaded - login should not be case sensitive", getWindow(), urlContains("overview"));
	}

	@Test
	public void testPasswordCaseSensitive() {
		loginPage.loginWith(new OPAccount("admin", "QWERTY"));
		assertThat("Navigated away from login page with invalid password", getWindow(), urlContains("login"));
		loginPage = getElementFactory().getLoginPage();
		assertThat("Correct error message not showing", loginPage.getText().contains("Please check your username and password"));
	}

	@Test
	public void testIncorrectPassword() {
		loginPage.loginWith(new OPAccount("admin", "WroNG"));
		assertThat("Navigated away from login page with invalid password", getWindow(), urlContains("login"));
		loginPage = getElementFactory().getLoginPage();
		assertThat("Correct error message not showing", loginPage.getText().contains("Please check your username and password"));
	}

	@Test
	public void testInvalidUsernames() {
		for (final String invalidUserName : Arrays.asList("aadmin", " ", "admin.", "admin*", "admin/")) {
			loginPage.loginWith(new OPAccount(invalidUserName, "qwerty"));
			assertThat("Navigated away from login page with invalid username " + invalidUserName, getWindow(), urlContains("login"));
			loginPage = getElementFactory().getLoginPage();
			assertThat("Correct error message not showing", loginPage.getText().contains("Please check your username and password"));
		}
	}

    private List<String> loadTextFileLineByLineIntoList(final String filePath) throws IOException {
        final FileInputStream fis = new FileInputStream(filePath);

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            final List<String> fiveWords = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                fiveWords.add(line);
            }

            return fiveWords;
        }
    }

    @Ignore //Do not have the correct txt file
	@Test
	public void testSQLInjection() throws IOException {
		for (final String password : loadTextFileLineByLineIntoList("C://dev//res//sqlInj.txt")) {
			loginPage.loginWith(new OPAccount("admin", password));
			assertThat("Navigated away from login page with invalid password", getWindow(), urlContains("login"));
			loginPage = getElementFactory().getLoginPage();
			assertThat("Correct error message not showing", loginPage.getText().contains("Please check your username and password"));
		}
	}

	@Test
	public void testLogoutNoAccessViaUrl() {
		getDriver().get(getAppUrl() + "overview");
		Waits.loadOrFadeWait();
		assertThat(getWindow(), url(not(containsString("overview"))));
		assertThat(getWindow(), urlContains("login"));

		getDriver().get(getAppUrl() + "keywords");
		Waits.loadOrFadeWait();
		assertThat(getWindow(), url(not(containsString("keywords"))));
		assertThat(getWindow(), urlContains("login"));
	}

	@Test
	public void testDefaultLoginDisabled() {
		getDriver().get(getAppUrl().substring(0, getAppUrl().length() - 2) + "login?defaultLogin=admin");
		Waits.loadOrFadeWait();
		loginPage = getElementFactory().getLoginPage();
		verifyThat(loginPage.usernameInput(), not(hasAttribute("readonly")));
		assertThat(getWindow(), urlContains("defaultLogin"));
	}
}
