package com.your.packages.admin;

import org.ballcat.springsecurity.oauth2.server.authorization.autoconfigure.OAuth2AuthorizationServerAutoConfiguration;
import org.ballcat.springsecurity.oauth2.server.resource.OAuth2ResourceServerAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.test.annotation.Rollback;

import java.time.Duration;
import java.util.UUID;

/**
 * @author hccake
 */
@JdbcTest(excludeAutoConfiguration = { OAuth2AuthorizationServerAutoConfiguration.class,
		OAuth2ResourceServerAutoConfiguration.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OAuth2RegisteredClientTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@Rollback(false)
	void createUiClient() {
		JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(
				jdbcTemplate);

		String clientId = "ui";
		String clientSecret = "{noop}ui";

		RegisteredClient client = jdbcRegisteredClientRepository.findByClientId(clientId);
		if (client == null) {
			RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId(clientId)
				.clientSecret(clientSecret)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.redirectUri("http://127.0.0.1:8080/authorized")
				// .scope("skip_captcha") // 跳过验证码
				// .scope("skip_password_decode") // 跳过 AES 密码解密
				.tokenSettings(TokenSettings.builder()
					// 使用不透明令牌
					.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
					.accessTokenTimeToLive(Duration.ofDays(1))
					.refreshTokenTimeToLive(Duration.ofDays(3))
					.build())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();
			jdbcRegisteredClientRepository.save(registeredClient);

			client = jdbcRegisteredClientRepository.findByClientId(clientId);
			Assertions.assertNotNull(client);
		}
	}

	@Test
	@Rollback(false)
	void createTestClient() {
		JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(
				jdbcTemplate);

		RegisteredClient client = jdbcRegisteredClientRepository.findByClientId("test");
		if (client == null) {
			RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("test")
				.clientSecret("{noop}test")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.redirectUri("http://127.0.0.1:8080/authorized")
				.scope("skip_captcha") // 跳过验证码
				.scope("skip_password_decode") // 跳过 AES 密码解密
				.tokenSettings(TokenSettings.builder()
					// 使用不透明令牌
					.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
					.accessTokenTimeToLive(Duration.ofDays(1))
					.refreshTokenTimeToLive(Duration.ofDays(3))
					.build())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();
			jdbcRegisteredClientRepository.save(registeredClient);

			client = jdbcRegisteredClientRepository.findByClientId("test");
			Assertions.assertNotNull(client);
		}
	}

}
