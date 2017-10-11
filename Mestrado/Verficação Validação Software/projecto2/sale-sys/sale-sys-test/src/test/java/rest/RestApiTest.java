package rest;

import static com.jayway.restassured.RestAssured.given;
import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;

public class RestApiTest {

	DbSetup db;
	DriverManagerDestination dmd;
	Operation operation;
	String url, dbuser, dbpassword;
	public static final Operation DELETE_ALL = deleteAllFrom("Customer");

	String username;
	String password;
	String token;
	List<Object> cBD = new ArrayList<Object>();
	Map<Object, Object> map1 = new HashMap<>();
	Map<Object, Object> map2 = new HashMap<>();

	@Before
	public void setup() throws UnsupportedEncodingException {

		// Setup à DB
		url = "jdbc:mysql://dbserver.alunos.di.fc.ul.pt:3306/vvs003";
		dbuser = dbpassword = "vvs003";
		dmd = new DriverManagerDestination(url, dbuser, dbpassword);

		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();

		// Populate tables
		Insert insert = Insert.into("Customer").columns("id", "designation", "phoneNumber", "vatNumber", "discount_id")
				.values("1", "Inês Matos", "918158537", "223587451", "3")
				.values("2", "Tiago Moucho", "910889744", "252404467", "1").build();

		db = new DbSetup(dmd, insert);
		db.launch();

		// Criação da lista através de hashmaps
		cBD.clear();
		map1.clear();
		map2.clear();
		map1.put("id", 1);
		map1.put("href", "http://localhost:8080/sale-sys/rest/v1/customers/1");
		map1.put("designation", "Inês Matos");
		map1.put("vatNumber", 223587451);
		cBD.add(map1);
		map2.put("id", 2);
		map2.put("href", "http://localhost:8080/sale-sys/rest/v1/customers/2");
		map2.put("designation", "Tiago Moucho");
		map2.put("vatNumber", 252404467);
		cBD.add(map2);

		// Criação do token através das credenciais
		username = "admin";
		password = "vvs000";
		String credentials = new String(Base64.getEncoder().encode((username + ":" + password).getBytes("UTF-8")));

		token = given().contentType(ContentType.JSON).body("{ \"credentials\": \"" + credentials + "\" }").when()
				.post("/sale-sys/rest/v1/login").then().statusCode(200).extract().path("token");
	}

	/**
	 * [1,2] - GET http://localhost:8080/sale-sys/
	 */
	@Test
	public void testBasic() {
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		Response res = given().get("/sale-sys/").then().extract().response();

		assertEquals(403, res.getStatusCode());
	}

	/**
	 * [1,3,4] - GET http://localhost:8080/sale-sys/rest/v1
	 */
	@Test
	public void testHome() {
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		Response res = given().get("/sale-sys/rest/v1").then().extract().response();
		String apiname = res.getBody().xmlPath().get("api.productInfo.name");
		int status = res.getStatusCode();
		assertEquals(200, status);
		assertEquals("Sale Sys", apiname);
	}

	/**
	 * [1,3,5,6] - GET http://localhost:8080/sale-sys/rest/v1/customers Status =
	 * 200 && com customers
	 */
	@Test
	public void testGetCustomers() {
		Response res = given().headers("Authorization", token).contentType(ContentType.JSON)
				.get("/sale-sys/rest/v1/customers").then().extract().response();
		List<Object> listObjects = res.getBody().jsonPath().getList("objects");
		int status = res.getStatusCode();
		assertEquals(200, status);
		assertEquals(2, listObjects.size());
		assertTrue(cBD.equals(listObjects));
	}

	/**
	 * [1,3,5,6] - GET http://localhost:8080/sale-sys/rest/v1/customers Status =
	 * 200 && sem customers
	 */
	@Test
	public void testGetCustomersEmpty() {
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		Response res = given().headers("Authorization", token).contentType(ContentType.JSON)
				.get("/sale-sys/rest/v1/customers").then().extract().response();
		List<Object> listObjects = res.getBody().jsonPath().getList("objects");
		int status = res.getStatusCode();
		assertEquals(200, status);
		assertEquals(0, listObjects.size());
		assertTrue(listObjects.isEmpty());
	}

	/**
	 * [1,3,5,7] - POST http://localhost:8080/sale-sys/rest/v1/customers Status
	 * = 201 && unique customers
	 */
	@Test
	public void testPostCustomersUnique() {
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.body("{ \"vat\": \"223587451\", \"denomination\": \"Inês Matos\", "
						+ "\"phoneNumber\": \"910889744\", \"discountType\": \"2\"}")
				.post("/sale-sys/rest/v1/customers").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(201, status);
	}

	/**
	 * [1,3,5,7] - POST http://localhost:8080/sale-sys/rest/v1/customers Status
	 * = 412 && not unique customers
	 */
	@Test
	public void testPostCustomersNotUnique() {
		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.body("{ \"vat\": \"223587451\", \"denomination\": \"Inês Matos\", "
						+ "\"phoneNumber\": \"910889744\", \"discountType\": \"2\"}")
				.post("/sale-sys/rest/v1/customers").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(412, status);
	}

	/**
	 * [1,3,5,7] - POST http://localhost:8080/sale-sys/rest/v1/customers Status
	 * = 404 && invalid input
	 */
	@Test
	public void testPostCustomersInvalidInput() {
		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.body("{ \"vat\": \"22a58b45c\", \"denomination\": \"Inês Matos\", "
						+ "\"phoneNumber\": \"91a88b74c\", \"discountType\": \"1\"}")
				.post("/sale-sys/rest/v1/customers").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(404, status);
	}

	/**
	 * [1,3,5,8,9] - GET http://localhost:8080/sale-sys/rest/v1/customers/id
	 * Status = 200
	 */
	@Test
	public void testGetCustomersByID() {

		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.get("/sale-sys/rest/v1/customers/1").then().extract().response();
		int id = res.getBody().jsonPath().getInt("id");
		int status = res.getStatusCode();
		assertEquals(200, status);
		assertEquals(1, id);
	}

	/**
	 * [1,3,5,8,9] - GET http://localhost:8080/sale-sys/rest/v1/customers/id
	 * Status = 404 && id nao existe
	 */
	@Test
	public void testGetCustomersByNonExistentID() {

		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.get("/sale-sys/rest/v1/customers/30").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(404, status);
	}

	/**
	 * [1,3,5,8,10] - GET http://localhost:8080/sale-sys/rest/v1/customers/id
	 * Status = 200
	 */
	@Test
	public void testDeleteCustomersByID() {

		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.delete("/sale-sys/rest/v1/customers/1").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(200, status);
	}

	/**
	 * [1,3,5,8,10] - GET http://localhost:8080/sale-sys/rest/v1/customers/id
	 * Status = 404 && id nao existe
	 */
	@Test
	public void testDeleteCustomersByNonExistentID() {

		Response res = given().headers("Authorization", token).contentType(ContentType.JSON + "; charset=utf-8")
				.delete("/sale-sys/rest/v1/customers/30").then().extract().response();
		int status = res.getStatusCode();
		assertEquals(404, status);
	}

}
