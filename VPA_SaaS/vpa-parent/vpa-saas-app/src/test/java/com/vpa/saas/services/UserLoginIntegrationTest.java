package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.UserDTO;

public class UserLoginIntegrationTest extends UserTest {

	@Test
	public void businessUserLoginHappyPath() throws Exception {

		businessUserLogin();

	}

	private void businessUserLogin() {
		CompanyDTO company = getCompany();
		registerBusinessUser(company);
		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		activateUser(savedUser);
		BusinessUser businessUser = businessUserDao.findInactiveUser(savedUser
				.getId());

		activateBusinessUser(businessUser);
		assertTrue(businessUser.getEnable() == VPASaaSConstant.TRUE);
		assertTrue(savedUser.getEnable() == VPASaaSConstant.TRUE);
		UserDTO user = businessUserService.login(EMAILD_ID, PASSWORD);
		assertActiveBusinessUser(user, savedUser, businessUser);
	}

	@Test(expected = UnauthorizedResourceException.class)
	public void businessUserInvalidPassWord() throws Exception {

		businessUserService.login("üser@test.com","test1234");

	}

	@Test(expected = UnauthorizedResourceException.class)
	public void businessUserInvalidUserId() throws Exception {

		businessUserService.login(EMAILD_ID + "invalid", PASSWORD);

	}

	@Test
	public void brandOwnerUserLoginHappyPath() throws Exception {
		brandOwnerUserLogin();
	}

	private void brandOwnerUserLogin() {
		registerBrandOwnerUser();
		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		activateUser(savedUser);
		BrandOwnerUser brandOwnerUser = brandOwnerUserDao
				.findInactiveUser(savedUser.getId());

		activateBrandOwnerUser(brandOwnerUser);
		assertTrue(brandOwnerUser.getEnable() == VPASaaSConstant.TRUE);
		assertTrue(savedUser.getEnable() == VPASaaSConstant.TRUE);
		UserDTO user = brandOwnerUserService.login(EMAILD_ID, PASSWORD);
		assertActiveBrandOwnerUser(user, savedUser, brandOwnerUser);
	}

	@Test(expected = UnauthorizedResourceException.class)
	public void brandOwnerUserInvalidPassWord() throws Exception {

		brandOwnerUserService.login("user@test.com","user1234");

	}

	@Test(expected = UnauthorizedResourceException.class)
	public void brandOwnerUserInvalidUserId() throws Exception {

		brandOwnerUserService.login(EMAILD_ID + "invalid", PASSWORD);

	}

	@Test(expected = UnauthorizedResourceException.class)
	public void registerBusinessUserButLoginWithBrandOwnerUser()
			throws Exception {

		businessUserLogin();

		brandOwnerUserService.login(EMAILD_ID, PASSWORD);

	}

	@Test
	public void registerBrandOwnerUserButLoginWithBusinessUser()
			throws Exception {

		brandOwnerUserLogin();

		UserDTO user=businessUserService.login(EMAILD_ID, PASSWORD);
	
		User savedUser = userDao.findByEmailAddress(EMAILD_ID);
		
		assertActiveUser(user, savedUser);
	}


/*	@Test
	@Ignore
	public void verifyMail() {

		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial",
				"com.sun.jndi.dns.DnsContextFactory");
		int i = 0;
		String hostName = "zensar.com";
		try {
			DirContext ictx = new InitialDirContext(env);
			String[] a = new String[getMX(hostName).size() + 1];
			a = getMX(hostName).toArray(a);
			Attributes attrs = ictx.getAttributes(hostName, a);
			Attribute attr = attrs.get("MX");
			if (attr == null) {
				System.out.println("1 ################### invalid email id");
			} else {
				i = attrs.size();
			}

		} catch (Exception e) {
			System.out.println("2 ################### invalid email id");
			e.printStackTrace();
		}
		if (i > 0) {
			System.out.println(" ################### " + i + " mail servers");
		}

	}

	private ArrayList<String> getMX(String hostName) throws NamingException {
		// Perform a DNS lookup for MX records in the domain
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial",
				"com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(hostName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");

		// if we don't have an MX record, try the machine itself
		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(hostName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null)
				throw new NamingException("No match for name '" + hostName
						+ "'");
		}

		// Huzzah! we have machines to try. Return them as an array list
		// NOTE: We SHOULD take the preference into account to be absolutely
		// correct. This is left as an exercise for anyone who cares.
		ArrayList res = new ArrayList();
		NamingEnumeration en = attr.getAll();

		while (en.hasMore()) {
			String x = (String) en.next();
			String f[] = x.split(" ");
			if (f[0].endsWith("."))
				f[0] = f[0].substring(0, (f[0].length() - 1));
			res.add(f[0]);
		}
		return res;
	}*/

}
