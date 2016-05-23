package com.vpa.saas.filter.logger.requestlogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;


/**
 * @author PD42694
 *
 */
public class HttpServletRequestCopier extends HttpServletRequestWrapper {
	private static final Logger logger = Logger.getLogger(HttpServletRequestCopier.class);
	/**
	 * input stream
	 */
	private ServletInputStream inputStream;

	/**
	 * reader
	 */
	private BufferedReader reader;

	/**
	 * copy inputstream
	 */
	private ServletInputStreamCopier copier;

	/**
	 * copy inputstream
	 */
	private ServletReaderCopier readerCopier;

	/**
	 * @param request
	 */
	public HttpServletRequestCopier(HttpServletRequest request) {
		super(request);

	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (this.inputStream != null) {
			logger.error("getInputStream() has already been called on this response.");
			throw new IllegalStateException(
					"getInputStream() has already been called on this response.");
		}

		if (this.reader == null) {
			this.readerCopier = new ServletReaderCopier(getRequest()
					.getReader());
			this.reader = new BufferedReader(this.readerCopier);
		}

		return this.reader;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (this.reader != null) {
			logger.error("getReader() has already been called on this response.");
			throw new IllegalStateException(
					"getReader() has already been called on this response.");
		}
		if (this.inputStream == null) {
			this.inputStream = getRequest().getInputStream();
			this.copier = new ServletInputStreamCopier(this.inputStream);
		}
		return this.copier;
	}

	/**
	 * finish reading request if someone else didn't do it already
	 */
	public void finishReading() {
		try {
			if (this.inputStream == null) {
				Reader theReader = this.getReader();
				while (theReader.read() != -1) {
					// nothing
				}
			} else {
				InputStream theInputStream = this.getInputStream();
				while (theInputStream.read() != -1) {
					// nothing
				}
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * get copy
	 * 
	 * @return copy
	 */
	public byte[] getCopy() {
		if (this.copier != null) {
			return this.copier.getCopy();
		}
		if (this.readerCopier != null) {
			return this.readerCopier.getCopy();
		}
		return new byte[0];
	}

}
