import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testmfk.UserApiClient;

import java.time.Duration;

//@ExtendWith(SeleniumExtension.class)
@Slf4j
public class BaseTest {

    protected static final String PATH_TO_CHROME_DRIVER = "/usr/local/bin/chromedriver";
    protected static final String BASE_URL = "http://localhost:8080";
    protected WebDriver driver;
    protected UserApiClient apiClient;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver();
        log.info("WebDriver started");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        apiClient = UserApiClient.getInstance(BASE_URL);
        log.info("API client initialized with URL: {}", BASE_URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("WebDriver closed");
        }
    }
}