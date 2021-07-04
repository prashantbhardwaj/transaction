package com.prashant.transactionsystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.transactionsystem.model.Transaction;
import com.prashant.transactionsystem.model.TransactionType;
import com.prashant.transactionsystem.repositories.TransactionRepository;
import com.prashant.transactionsystem.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class TransactionSystemApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TransactionRepository transactionRepository;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void accessIncorrectUrl() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isNotFound())
				.andDo(document("{method-name}"));
	}

	@Test
	public void addTransaction() throws Exception {
		Transaction transaction = TransactionBuilder.instance()
				.withRandomAccountId()
				.withGBP()
				.description("test description")
				.withAmount(1.234)
				.debit()
				.build();
		this.mockMvc
				.perform(
						post("/transaction/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(convertObjectToJsonString(transaction))
				)
				.andExpect(status().isOk())
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getTransactionByIdWhenNonePresent() throws Exception {
		this.mockMvc.perform(get("/transaction/bb01aaf4-e542-4c57-b94b-170b1f433b8b"))
				.andExpect(status().isOk())
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getTransactionByIdWhenOnePresent() throws Exception {
		Transaction transaction = TransactionBuilder.instance()
				.withRandomAccountId()
				.withGBP()
				.description("test description")
				.withAmount(1.234)
				.debit()
				.build();
		UUID id = transactionRepository.save(transaction).getId();
		this.mockMvc.perform(get("/transaction/" + id.toString()))
				.andExpect(status().isOk())
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getAllTransactionsWhenNonePresent() throws Exception {
		this.transactionRepository.deleteAll();
		this.mockMvc.perform(get("/transactions/"))
				.andExpect(status().isOk())
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void getAllTransactionsWhenOnePresent() throws Exception {
		Transaction transaction = TransactionBuilder.instance()
				.withRandomAccountId()
				.withGBP()
				.description("test description")
				.withAmount(1.234)
				.debit()
				.build();
		this.transactionRepository.save(transaction);
		this.mockMvc.perform(get("/transactions/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void updateTransaction() throws Exception {
		Transaction transaction = TransactionBuilder.instance()
				.withRandomAccountId()
				.withGBP()
				.description("test description")
				.withAmount(1.234)
				.debit()
				.build();
		transaction = transactionRepository.save(transaction);
		transaction.setType(TransactionType.CREDIT);

		this.mockMvc
				.perform(
						put("/transaction/" + transaction.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(convertObjectToJsonString(transaction))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is(TransactionType.CREDIT.name())))
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void deleteTransactionWhenPresent() throws Exception {
		Transaction transaction = TransactionBuilder.instance()
				.withRandomAccountId()
				.withGBP()
				.description("test description")
				.withAmount(1.234)
				.debit()
				.build();
		transaction = transactionRepository.save(transaction);

		this.mockMvc
				.perform(delete("/transaction/" + transaction.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(Constants.DELETED))
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	public void deleteTransactionWhenNotPresent() throws Exception {
		this.mockMvc
				.perform(delete("/transaction/" + UUID.randomUUID()))
				.andExpect(status().isOk())
				.andExpect(content().string(Constants.RECORD_NOT_EXISTS))
				.andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	public static String convertObjectToJsonString(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsString(object);
	}



}
