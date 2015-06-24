import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class spider {
	private java.util.HashSet<String> hashset;

	private java.util.Queue<String> LinkList;

	private WebDriver driver;

	public void Spider() {

		hashset = new java.util.HashSet<String>();
		LinkList = new java.util.LinkedList<String>();
		driver = new ChromeDriver();

	}

	public void crawl(String startingUrl) {

		LinkList.add(startingUrl);

		String urlToVisit;

		while ((urlToVisit = LinkList.poll()) != null) {

			processPage(urlToVisit);

			java.util.List<WebElement> priceList = driver.findElements(By
					.className("price-box"));
			java.util.List<WebElement> productNameList = driver.findElements(By
					.className("product-name"));

			try {
				for (int i = 0; i <= priceList.size(); i++) {

					System.out.println("Item Number: "+i + "\n Product name = "
							+ productNameList.get(i).getText() + "\nProduct Price = "
							+ priceList.get(i).getText());

				}

			} catch (IndexOutOfBoundsException e) {
				System.out.println("\t\t\t************************************");
			}

		}
	}

	private void processPage(String url) {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(url);
		java.util.List<WebElement> listOfAnchors = driver.findElements(By
				.xpath("//div[1]/ol/li/a"));

		try {
			for (int i = 0; i <= listOfAnchors.size(); i++) {

				String href = listOfAnchors.get(i).getAttribute("href");

				if (isAddressValid(href)) {
					if (!hashset.contains(href)) {

                        hashset.add(href);
						LinkList.add(href);
					}
				}

			}

		} catch (IndexOutOfBoundsException e) {
			System.out.println();
		}
	}

	private boolean isAddressValid(String href) {

		return href.startsWith("http://www.shophive.com/apple/mac?p=");

	}

}