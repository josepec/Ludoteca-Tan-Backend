package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import com.devonfw.module.beanmapping.common.api.BeanMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan/";

    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long NOT_EXISTS_GAME_ID = 0L;
    private static final String NOT_EXISTS_TITLE = "NotExists";
    private static final String EXISTS_TITLE = "Aventureros";
    private static final String NEW_TITLE = "Nuevo juego";
    private static final Long NOT_EXISTS_CLIENT = 0L;
    private static final Long EXISTS_CLIENT = 2L;
    private static final String NOT_EXISTS_DATE = "3092-10-09";
    private static final String EXISTS_DATE = "2022-10-09";
    public static final Long DELETE_LOAN_ID = 6L;
    private static final Long NOT_EXISTS_LOAN_ID = 0L;

    private static final int TOTAL_LOANS = 10;
    private static final int PAGE_SIZE = 5;

    private static final String TITLE_PARAM = "title";
    private static final String CLIENT_PARAM = "idClient";
    private static final String DATE_PARAM = "date";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    GameService gameService;

    @Autowired
    BeanMapper beanMapper;

    ParameterizedTypeReference<List<GameDto>> responseType = new ParameterizedTypeReference<List<GameDto>>() {
    };

    ParameterizedTypeReference<Page<LoanDto>> responseTypePage = new ParameterizedTypeReference<Page<LoanDto>>() {
    };

    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(TITLE_PARAM, "{" + TITLE_PARAM + "}").queryParam(CLIENT_PARAM, "{" + CLIENT_PARAM + "}")
                .queryParam(DATE_PARAM, "{" + DATE_PARAM + "}").encode().toUriString();
    }

    @Test
    public void findFirstPageWithFiveSizeWithoutFiltersShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeWithoutFiltersShouldReturnLastResult() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(1, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findWithTitleFilterShouldReturnLoansOfTitle() {

        int LOANS_WITH_FILTER = 3;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, EXISTS_TITLE);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findWithClientFilterShouldReturnLoansOfClient() {

        int LOANS_WITH_FILTER = 2;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CLIENT_PARAM, EXISTS_CLIENT);
        params.put(DATE_PARAM, null);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findWithDateFilterShouldReturnLoansOfDate() {

        int LOANS_WITH_FILTER = 1;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, EXISTS_DATE);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsTitleShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, NOT_EXISTS_TITLE);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CLIENT_PARAM, NOT_EXISTS_CLIENT);
        params.put(DATE_PARAM, null);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsDateShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, NOT_EXISTS_DATE);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void saveShouldCreateNewLoan() {

        LoanDto dto = new LoanDto();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(EXISTS_CLIENT);
        clientDto.setName("Vaishnavi Ngải");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        dto.setClient(clientDto);
        dto.setGame(beanMapper.map(gameService.get(2L), GameDto.class));
        try {
            dto.setStartDate(formatter.parse("2023-01-03"));
            dto.setEndDate(formatter.parse("2023-01-06"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, gameService.get(2L).getTitle());
        params.put(CLIENT_PARAM, EXISTS_CLIENT);
        params.put(DATE_PARAM, null);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(0, response.getBody().getNumberOfElements());

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto),
                responseTypePage, params);

        assertNotNull(response);
        assertEquals(1, response.getBody().getNumberOfElements());
    }

    @Test
    public void saveWithWrongDateRangeShouldThrowException() {

        LoanDto dto = new LoanDto();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(EXISTS_CLIENT);
        clientDto.setName("Vaishnavi Ngải");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        dto.setClient(clientDto);
        dto.setGame(beanMapper.map(gameService.get(2L), GameDto.class));
        try {
            dto.setStartDate(formatter.parse("2023-01-03"));
            dto.setEndDate(formatter.parse("2023-01-30"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    public void saveWithGameWithLoanInRangeDatesShouldThrowException() {

        LoanDto dto = new LoanDto();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(EXISTS_CLIENT);
        clientDto.setName("Vaishnavi Ngải");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        dto.setClient(clientDto);
        dto.setGame(beanMapper.map(gameService.get(6L), GameDto.class));
        try {
            dto.setStartDate(formatter.parse("2022-06-03"));
            dto.setEndDate(formatter.parse("2022-06-05"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    public void saveWithClientWithLoanInRangeDatesShouldThrowException() {

        LoanDto dto = new LoanDto();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(EXISTS_CLIENT);
        clientDto.setName("Vaishnavi Ngải");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        dto.setClient(clientDto);
        dto.setGame(beanMapper.map(gameService.get(6L), GameDto.class));
        try {
            dto.setStartDate(formatter.parse("2022-08-03"));
            dto.setEndDate(formatter.parse("2022-08-05"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + DELETE_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS - 1, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsLoanIdShouldInternalError() {

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + NOT_EXISTS_LOAN_ID,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
