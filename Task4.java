package IntervivewAssessment;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task4 {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		
		//System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\drivers\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void verifyAddCart() throws Exception {

		WebElement searchBox = driver.findElement(By.id("search_query_top"));
		searchBox.sendKeys("summer dresses");
		WebElement searchButton = driver.findElement(By.name("submit_search"));
		searchButton.click();
		WebElement iconList = driver.findElement(By.className("icon-th-list"));
		iconList.click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement ContinueShopping = driver.findElement(By.xpath("//span[@title='Continue shopping']"));
		WebElement proceedToCheckout = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> products = driver.findElements(By.xpath("//ul[@class='product_list row list']/li"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> addCart = driver.findElements(By.xpath("//a[@class='button ajax_add_to_cart_button btn btn-default']/span"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
		int count = 0;
		for (int i = 0; i <= products.size(); i++) {
			count++;
			addCart.get(i).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (count == products.size()) {
				proceedToCheckout.click();
				break;
			}
			ContinueShopping.click();

		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement productNum =driver.findElement(By.id("summary_products_quantity"));
		String numProducsInCart = productNum.getText();
		String expectedResult = "4 Products";
		Assert.assertEquals(numProducsInCart, expectedResult);

		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement proceedToCheckoutInCart = driver.findElement(By.xpath("//*[@id='center_column']/p[2]/a[1]/span"));
		proceedToCheckoutInCart.click();

		WebElement signinPage = driver.findElement(By.xpath("//div[@id='center_column']/h1"));
		
		boolean passing= true;
		
		if(signinPage.getText().equalsIgnoreCase("AUTHENTICATION")) {
			
			System.out.println("User is on the Sign Page : "+ passing);
			
		}else {
			passing =false;
			
			System.out.println("User is NOT on the Sign Page : "+passing);
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@AfterMethod
	public void tearDown() {
		if (driver != null)
			driver.quit();
		driver = null;
	}
}
