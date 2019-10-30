package client;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class ClientModelTest {
	

	@Test
	public void createJsonArrayTest() {
		ClientModel model = new ClientModel();
		JSONObject json = model.createJsonArray("turn", "hallo","hello","hi");
		assertTrue(json != null);
		System.out.println(json.toJSONString());
	}
	
	

}
