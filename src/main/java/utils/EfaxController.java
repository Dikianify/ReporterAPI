package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.reporter.model.OrderedItems;
import com.reporter.repository.OrderedItemsRepository;

public class EfaxController {
	private String authToken = "";
	
	@Autowired
	OrderedItemsRepository ordereditemsrepository;
	
//	public OrderedItems sendFax(OrderedItems item) {
//		item.set
//		ordereditemsrepository.createItem()
//		return item;
//	}
	
	public void getAuthToken() throws IOException {
		URL url = new URL("https://api.securedocex.com/tokens");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		authToken = "";
	}
}
