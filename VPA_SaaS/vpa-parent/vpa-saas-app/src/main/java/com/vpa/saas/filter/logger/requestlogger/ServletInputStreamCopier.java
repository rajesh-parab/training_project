package com.vpa.saas.filter.logger.requestlogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

/**
 * copy bytes to a buffer
 */
/**
 * @author PD42694
 *
 */
public class ServletInputStreamCopier extends ServletInputStream {

	/**
	 * output stream
	 */
	private InputStream inputStream;

	/**
	 * keep a copy
	 * 
	 */
	private ByteArrayOutputStream copy;

	/**
	 * constructor
	 * 
	 * @param inputStream1
	 */
	public ServletInputStreamCopier(InputStream inputStream1) {
		this.inputStream = inputStream1;
		this.copy = new ByteArrayOutputStream(1024);
	}

	/**
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		int result = this.inputStream.read();
		this.copy.write(result);
		return result;
	}

	/**
	 * get the copy
	 * 
	 * @return bytes
	 */
	public byte[] getCopy() {
		return this.copy.toByteArray();
	}
}
