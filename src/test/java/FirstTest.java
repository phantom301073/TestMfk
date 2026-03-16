import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testmfk.CreateUserRequest;
import org.testmfk.UserResponse;

import java.time.Duration;

@Slf4j
public class FirstTest extends BaseTest {

    @ParameterizedTest()
    @CsvSource({"CorrectUser, test@google.com",
            "InCorrectUserEmail, test",
            "CorrectUser, test_2@google.com"})
    public void firstTestMethod (String username, String email) throws NoSuchElementException {
        CreateUserRequest user = CreateUserRequest.builder()
                .username(username)
                .email(email)
                .build();
        UserResponse createUser = apiClient.createUser(user);
        log.info("CreateUserRequest {}", user);
        log.info("User created {}", createUser);
        driver.get(BASE_URL + "/admin/users");
        WebElement elementUserNamePresent = getDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text(), '" + user.getUsername() + "')]")));
        Assertions.assertNotNull(elementUserNamePresent);

       apiClient.deleteUser(createUser.getId());

        Assertions.assertFalse(getDriverWait()
                .until(ExpectedConditions.invisibilityOfAllElements(elementUserNamePresent)));
    }

    private @NonNull WebDriverWait getDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

}
