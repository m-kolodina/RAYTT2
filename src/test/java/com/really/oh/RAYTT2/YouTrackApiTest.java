package com.really.oh.RAYTT2;

import com.really.oh.RAYTT2.dto.CreateIssueRequest;
import com.really.oh.RAYTT2.dto.IssueResponse;
import com.really.oh.RAYTT2.dto.Project;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestInstance(Lifecycle.PER_METHOD)
@DisplayName("Тесты YouTrack API: Создание задач (Issues)")
public class YouTrackApiTest {

    private static RequestSpecification requestSpec;
    private static final String BASE_URL = "http://193.233.193.42:9091/api";
    private static final String ISSUES_ENDPOINT = "/issues";
    private static final String AUTH_TOKEN = "Bearer perm-a29sb2RpbmFfbWFyaWE=.NDQtNw==.WsTlwI8iyMTna1M69PZPeoBb3lYjGB";

    @BeforeAll
    static void setup() {
        System.out.println("--- Настройка RestAssured ---");
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("Authorization", AUTH_TOKEN)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
        System.out.println("--- Настройка завершена ---");
    }

    @ParameterizedTest(name = "Позитивный тест #{index}: Создание задачи с темой: {0}")
    @CsvFileSource(resources = "/positiveTestCases.csv", numLinesToSkip = 1, delimiter = ',')
    @DisplayName("Проверка успешного создания задачи")
    void testCreateIssuePositive(String summary, String description, String projectId) {
        System.out.println("\n--- Запуск позитивного теста: " + summary + " ---");

        CreateIssueRequest issueRequest = new CreateIssueRequest();
        issueRequest.setSummary(summary);
        issueRequest.setDescription(description);
        issueRequest.setProject(new Project(projectId, null));

        System.out.println("--- DTO для запроса: " + issueRequest + " ---");

        given()
                .spec(requestSpec)
                .body(issueRequest)
                .when()
                .post(ISSUES_ENDPOINT)
                .then()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .log().all();

        System.out.println("--- Позитивный тест завершен ---");
    }

    @ParameterizedTest(name = "Негативный тест #{index}: Создание задачи с темой: {0}. Ожидаемый статус: {3}")
    @CsvFileSource(resources = "/negativeTestCases.csv", numLinesToSkip = 1, delimiter = ',')
    @DisplayName("Проверка обработки ошибок при создании задачи")
    void testCreateIssueNegative(String summary, String description, String projectId, int expectedStatusCode) {
        System.out.println("\n--- Запуск негативного теста: " + summary + ". Ожидаемый статус: " + expectedStatusCode + " ---");

        CreateIssueRequest issueRequest = new CreateIssueRequest();
        issueRequest.setSummary(summary == null || summary.isEmpty() ? null : summary);
        issueRequest.setDescription(description == null || description.isEmpty() ? null : description);
        issueRequest.setProject(projectId == null || projectId.isEmpty() ? null : new Project(projectId, null));

        given()
                .spec(requestSpec)
                .body(issueRequest)
                .when()
                .post(ISSUES_ENDPOINT)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .body(is(notNullValue())); //Проверка на существование ответа

        System.out.println("--- Негативный тест завершен. Получен ожидаемый статус: " + expectedStatusCode + " ---");
    }

    @Test
    @DisplayName("Простой позитивный тест: создание базовой задачи")
    void testSimplePositiveIssueCreation() {
        System.out.println("\n--- Запуск простого позитивного теста ---");

        String summary = "Simple Test Issue";
        String description = "This is a simple test issue created via API.";
        String projectId = "0-1"; // ID проекта по умолчанию в YouTrack

        CreateIssueRequest issueRequest = new CreateIssueRequest();
        issueRequest.setSummary(summary);
        issueRequest.setDescription(description);
        issueRequest.setProject(new Project(projectId, null));

        IssueResponse createdIssue = given()
                .spec(requestSpec)
                .body(issueRequest)
                .when()
                .post(ISSUES_ENDPOINT)
                .then()
                .statusCode(200)
                .log().all()
                .body("id", is(notNullValue()))
                .extract()
                .as(IssueResponse.class);

        System.out.println("--- Простая тестовая задача успешно создана. ID: " + createdIssue.getId() + " ---");
        System.out.println("--- Простой позитивный тест завершен ---");
    }
}