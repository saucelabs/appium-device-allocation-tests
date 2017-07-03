package ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class TestPrivateDeviceAllocation {

	AppiumDriver driver;

	@Before
	public void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		capabilities.setCapability("privateDevicesOnly", System.getenv("PRIVATE_DEVICES_ONLY"));

		driver = new IOSDriver(new URL(System.getenv("APPIUM_SERVER")), capabilities);
	}

	@Test
	public void printContext() throws Exception {
		System.out.println(driver.getContext());
	}

	@After
	public void tearDown() {
		driver.quit();
	}
}