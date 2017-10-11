package webapp;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresentWithText;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertFormElementPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertFormPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresentWithExactText;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.getElementsByXPath;
import static net.sourceforge.jwebunit.junit.JWebUnit.selectOption;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;

import net.sourceforge.jwebunit.api.IElement;

public class WebServerTest {

	DbSetup db;
	DriverManagerDestination dmd;
	Operation operation;
	String url, user, password;
	public static final Operation DELETE_ALL = deleteAllFrom("Customer");
	boolean message1, message2, message3, message4, message5, message6, message7, message8, message9;

	@Before
	public final void setup() {
		message1 = message2 = message3 = message4 = message5 = message6 = message7 = message8 = message9 = false;
		setBaseUrl("http://localhost:8080/sale-sys-web");
		url = "jdbc:mysql://dbserver.alunos.di.fc.ul.pt:3306/vvs003";
		user = password = "vvs003";
		dmd = new DriverManagerDestination(url, user, password);

		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();

		// Populate tables
		Insert insert = Insert.into("Customer")
				.withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L).incrementingBy(1))
				.columns("designation", "phoneNumber", "vatNumber", "discount_id")
				.values("Inês Matos", "918158537", "223587451", "3")
				.values("Tiago Moucho", "910889744", "252404467", "1").build();

		db = new DbSetup(dmd, insert);
		db.launch();
	}

	@Test
	public final void testIndexPage() {
		beginAt("/");
		assertTitleEquals("SaleSys: Welcome page");
		assertTextPresent("Welcome to SaleSys(c)");
		assertLinkPresentWithExactText("Create client");
		assertLinkPresentWithExactText("Make a sale");
	}

	/**
	 * C1B1-C2B1-C3B1-C4B1
	 */
	@Test
	public void test1() {
		// Cleans some tables
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "Tiago Moucho");
		setTextField("npc", "252404467");
		setTextField("telefone", "910889744");
		selectOption("desconto", "No discount");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message1);
	}

	/**
	 * C1B1-C2B1-C3B2-C4B2
	 */
	@Test
	public void test2() {
		// Cleans some tables
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "Tiago Moucho");
		setTextField("npc", "252404467");
		setTextField("telefone", "91se547d8");
		selectOption("desconto", "Percentage (above threshold)");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message6);
		assertTrue(message2);
	}

	/**
	 * C1B1-C2B1-C3B2-C4B3
	 */
	@Test
	public void test3() {
		// Cleans some tables
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "Tiago Moucho");
		setTextField("npc", "252404467");
		setTextField("telefone", "91se547d8");
		selectOption("desconto", "Percentage of total eligible");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message6);
		assertTrue(message2);
	}

	/**
	 * C1B1-C2B2-C3B2-C4B1
	 */
	@Test
	public void test4() {
		// Cleans some tables
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "Tiago Moucho");
		setTextField("npc", "252404467");
		setTextField("telefone", "91se547d8");
		selectOption("desconto", "No discount");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message2);
		assertTrue(message6);
	}

	/**
	 * C1B2-C2B1-C3B2-C4B1
	 */
	@Test
	public void test5() {
		// Cleans some tables
		db = new DbSetup(dmd, DELETE_ALL);
		db.launch();
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "");
		setTextField("npc", "252404467");
		setTextField("telefone", "91se547d8");
		selectOption("desconto", "No discount");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message3);
		assertTrue(message6);
		assertTrue(message2);
	}

	@Test
	public void bonusTestBD() {
		beginAt("/action/clientes/novoCliente");
		assertFormPresent();
		assertButtonPresentWithText("Create client");
		assertFormElementPresent("designacao");
		assertFormElementPresent("npc");
		assertFormElementPresent("telefone");
		assertFormElementPresent("desconto");
		setTextField("designacao", "Tiago Moucho");
		setTextField("npc", "252404467");
		setTextField("telefone", "910889744");
		selectOption("desconto", "No discount");
		submit();
		List<IElement> list = getElementsByXPath("//li");
		checkMessages(list);
		assertTrue(message9);
	}

	public void checkMessages(List<IElement> list) {
		for (IElement i : list) {
			if (i.getTextContent().split("\n")[0].equals("Customer successfully added. ")) {
				message1 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("Error validating customer data ")) {
				message2 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("Designation must be filled. ")) {
				message3 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("VAT number must be filled ")) {
				message4 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("VAT number with invalid characters ")) {
				message5 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("Phone number contains invalid characters ")) {
				message6 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("Discount type must be filled. ")) {
				message7 = true;
			}
			if (i.getTextContent().split("\n")[0].equals("Discount type with invalid characters ")) {
				message8 = true;
			}
			if (i.getTextContent().split("\n")[0].contains("Error adding customer")) {
				message9 = true;
			}
		}
	}
}