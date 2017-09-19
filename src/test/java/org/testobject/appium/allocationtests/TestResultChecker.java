package org.testobject.appium.allocationtests;

import io.appium.java_client.AppiumDriver;
import org.testobject.api.TestObjectClient;
import org.testobject.rest.api.model.DeviceDescriptor;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestResultChecker extends EnvironmentVariables {

	private DeviceDescriptor.DeviceContainer device;

	public TestResultChecker(AppiumDriver driver) {
		int reportId = Integer.valueOf(driver.getCapabilities().getCapability("testobject_test_report_id").toString());
		String userId = driver.getCapabilities().getCapability("testobject_user_id").toString();
		String projectId = driver.getCapabilities().getCapability("testobject_project_id").toString();

		TestObjectClient client = TestObjectClient.Factory.create(getEnvOrFail(REST_API));
		client.login(userId, PASSWORD);
		device = client.getTestReport(userId, projectId, reportId).getDevice();
	}

	public TestResultChecker checkDeviceDescriptorId() {
		assertEquals("Device descriptor id should be " + DEVICE_DESCRIPTOR_ID + " but found " + device.id,
				device.id, DEVICE_DESCRIPTOR_ID);
		return this;
	}

	public TestResultChecker checkDeviceName() {
		assertTrue("Device name should be " + DEVICE_NAME + " but found " + device.name, device.name.equalsIgnoreCase(DEVICE_NAME));
		return this;
	}

	public TestResultChecker checkMatchesRegEx(String regEx) {
		assertTrue("Device name should matches " + regEx + " but found " + device.name,
				Pattern.compile(regEx, Pattern.CASE_INSENSITIVE).matcher(device.name).matches());
		return this;
	}

	public TestResultChecker checkPlatformName(String platformName) {
		assertTrue("OS name should be " + platformName + " but found " + device.os.name(),
				device.os.name().equalsIgnoreCase(platformName));
		return this;
	}

	public TestResultChecker checkPlatformVersion() {
		assertTrue("OS version should start with " + device.osVersion + " but found " + PLATFORM_VERSION,
				device.osVersion.startsWith(PLATFORM_VERSION));
		return this;
	}

	public TestResultChecker checkPrivateDeviceId() {
		assertEquals("Private device should be " + DEVICE_DESCRIPTOR_ID + " but found " + device.id, device.id,
				PRIVATE_DEVICE_DESCRIPTOR_ID);
		return this;
	}
}
