package com.vpa.saas.services.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.WebSocketContainer;

import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketUnitTest  {

	private StandardWebSocketClient wsClient;

	private WebSocketContainer wsContainer;

	private WebSocketHandler wsHandler;

	private WebSocketHttpHeaders headers;
	
	@Before
	public void setup() {
		this.headers = new WebSocketHttpHeaders();
		this.wsHandler = new AbstractWebSocketHandler() {
		};
		this.wsContainer = mock(WebSocketContainer.class);
		this.wsClient = new StandardWebSocketClient(this.wsContainer);
		//this.infoReceiver = mock(InfoReceiver.class);
	}

	@Test
	public void testGetLocalAddress() throws Exception {
		URI uri = new URI("ws://localhost/vpa-saas-app/live-scan-count");
		WebSocketSession session = this.wsClient.doHandshake(this.wsHandler,
				this.headers, uri).get();

		assertNotNull(session.getLocalAddress());
		assertEquals(80, session.getLocalAddress().getPort());
	}

	@Test
	public void testGetLocalAddressWss() throws Exception {
		URI uri = new URI("wss://localhost/vpa-saas-app/live-scan-count");
		WebSocketSession session = this.wsClient.doHandshake(this.wsHandler,
				this.headers, uri).get();

		assertNotNull(session.getLocalAddress());
		assertEquals(443, session.getLocalAddress().getPort());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLocalAddressNoScheme() throws Exception {
		URI uri = new URI("localhost/vpa-saas-app/live-scan-count");
		this.wsClient.doHandshake(this.wsHandler, this.headers, uri);
	}

	@Test
	public void testGetRemoteAddress() throws Exception {
		URI uri = new URI("wss://localhost/vpa-saas-app/live-scan-count");
		WebSocketSession session = this.wsClient.doHandshake(this.wsHandler,
				this.headers, uri).get();

		assertNotNull(session.getRemoteAddress());
		assertEquals("localhost", session.getRemoteAddress().getHostName());
		assertEquals(443, session.getRemoteAddress().getPort());
	}

	@Test
	public void handshakeHeaders() throws Exception {

		URI uri = new URI("ws://localhost/vpa-saas-app/live-scan-count");
		List<String> protocols = Collections.singletonList("live-scan-count");
		this.headers.setSecWebSocketProtocol(protocols);
		this.headers.add("live Authentication", "webSocket");

		WebSocketSession session = this.wsClient.doHandshake(this.wsHandler,
				this.headers, uri).get();

		assertEquals(1, session.getHandshakeHeaders().size());
		assertEquals("webSocket", session.getHandshakeHeaders().getFirst("live Authentication"));
	}

	@Test
	public void clientEndpointConfig() throws Exception {

		URI uri = new URI("ws://localhost/abc");
		List<String> protocols = Collections.singletonList("abc");
		this.headers.setSecWebSocketProtocol(protocols);

		this.wsClient.doHandshake(this.wsHandler, this.headers, uri).get();

		ArgumentCaptor<ClientEndpointConfig> captor = ArgumentCaptor
				.forClass(ClientEndpointConfig.class);
		verify(this.wsContainer).connectToServer(any(Endpoint.class),
				captor.capture(), any(URI.class));
		ClientEndpointConfig endpointConfig = captor.getValue();

		assertEquals(protocols, endpointConfig.getPreferredSubprotocols());
	}

	
	@Test
	public void standardWebSocketClientConfiguratorInsertsHandshakeHeaders()
			throws Exception {

		URI uri = new URI("ws://localhost/abc");
		this.headers.add("foo", "bar");

		this.wsClient.doHandshake(this.wsHandler, this.headers, uri).get();

		ArgumentCaptor<ClientEndpointConfig> captor = ArgumentCaptor
				.forClass(ClientEndpointConfig.class);
		verify(this.wsContainer).connectToServer(any(Endpoint.class),
				captor.capture(), any(URI.class));
		ClientEndpointConfig endpointConfig = captor.getValue();

		Map<String, List<String>> headers = new HashMap<>();
		endpointConfig.getConfigurator().beforeRequest(headers);
		assertEquals(1, headers.size());
	}

	@Test
	public void WebSocketSession() throws Exception {

		URI uri = new URI("ws://localhost/abc");
		this.wsClient.setTaskExecutor(new SimpleAsyncTaskExecutor());
		WebSocketSession session = this.wsClient.doHandshake(this.wsHandler,
				this.headers, uri).get();

		assertNotNull(session);
	}
}
