package com.vpa.saas.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vpa.saas.test.utils.SaaSDTOFacotry;

/**
 * This test check logic for truth table which calculate value for
 * scanVarificationFlag field in scan table
 * 
 * @author rp25984
 *
 */
public class ScanVerificationFlagTest {

	private static final String DUMMY = "DUMMY";
	ProductScanDTO scannedProduct;

	@Before
	public void setUp() {
		scannedProduct = SaaSDTOFacotry.createProductScanDtoForTruthTable(11L,
				"Router", "AZ12345", "BOX");
	}

	@Test
	public void genuineProduct() {

		assertTrue(scannedProduct.calculateVerificationFlag() == 31);

	}

	// Theft
	@Test
	public void tamperedtWithLavelLocationNotMached() {
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 30);

	}

	// Serial Number Tampered
	@Test
	public void tamperedWithSerialNumberNotMached() {
		setDummySerialNumber();
		assertTrue(scannedProduct.calculateVerificationFlag() == 29);

	}

	// Serial Number Tampered
	@Test
	public void tamperedWithSerialNumberAndLevelLocationNotMached() {
		setDummySerialNumber();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 28);

	}

	// Counterfeit Upgrade
	@Test
	public void tamperedWithProductNameNotMached() {
		setDummyProduct();
		assertTrue(scannedProduct.calculateVerificationFlag() == 27);

	}

	// Counterfeit Upgrade
	@Test
	public void tamperedWithProductNameAndLevelLocationNotMached() {
		setDummyProduct();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 26);

	}

	// Counterfeit Upgrade
	@Test
	public void tamperedWithProductNameAndSerialNumberNotMached() {
		setDummyProduct();
		setDummySerialNumber();
		assertTrue(scannedProduct.calculateVerificationFlag() == 25);

	}

	// Counterfeit Upgrade
	@Test
	public void tamperedWithOnlyBrandMached() {
		setDummyProduct();
		setDummySerialNumber();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 24);

	}
	// Theft
	@Test
	public void tamperedWithOnlyBrandNotMached() {
		setDummyBrand();
		assertTrue(scannedProduct.calculateVerificationFlag() == 23);

	}

	// Theft
	@Test
	public void tamperedWithBrandAndLavelLocationNotMached() {
		setDummyBrand();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 22);

	}

	// Theft
	@Test
	public void tamperedWithBrandAndSerialNumberNotMached() {
		setDummyBrand();
		setDummySerialNumber();
		assertTrue(scannedProduct.calculateVerificationFlag() == 21);

	}

	// Theft
	@Test
	public void tamperedWithOnlyProductNameMached() {
		setDummyBrand();
		setDummySerialNumber();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 20);

	}
	
	// Theft
	@Test
	public void tamperedWithBrandAndProductNameNotMached() {
		setDummyBrand();
		setDummyProduct();
		assertTrue(scannedProduct.calculateVerificationFlag() == 19);

	}

	// Theft
	@Test
	public void tamperedWithOnlySerialNumberMached() {
		setDummyBrand();
		setDummyProduct();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 18);

	}

	// Theft
	@Test
	public void tamperedWithOnlyLavelLocationMached() {
		setDummyBrand();
		setDummyProduct();
		setDummySerialNumber();
		assertTrue(scannedProduct.calculateVerificationFlag() == 17);

	}

	// Theft
	@Test
	public void tamperedWithNothingMached() {
		setDummyBrand();
		setDummyProduct();
		setDummySerialNumber();
		setDummyLevelLocation();
		assertTrue(scannedProduct.calculateVerificationFlag() == 16);

	}

	private void setDummyProduct() {
		scannedProduct.getProduct().setName(DUMMY);
	}

	private void setDummySerialNumber() {
		scannedProduct.setSerialNumber(DUMMY);
	}

	private void setDummyLevelLocation() {
		scannedProduct.getProduct().setProductIdLabelLocation(DUMMY);
	}

	private void setDummyBrand() {
		scannedProduct.getBrand().setId(0L);
	}
}
