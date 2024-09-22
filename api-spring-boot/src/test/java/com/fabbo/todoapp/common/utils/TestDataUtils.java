package com.fabbo.todoapp.common.utils;

import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtils {
    private static final Random random = new Random();

    public static UUID randomUuid() {
        return UUID.randomUUID();
    }

    public static boolean randomBool() {
        return random.nextBoolean();
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(final int min, final int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static long randomLong() {
        return random.nextLong();
    }

    public static long randomLong(final long min, final long max) {
        return random.nextLong((max - min) + 1L) + min;
    }

    public static float randomFloat() {
        return random.nextFloat();
    }

    public static float randomFloat(final float min, final float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double randomDouble() {
        return random.nextDouble();
    }

    public static double randomDouble(final double min, final double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static String randomText() {
        return randomText(randomInt(0, 1000));
    }

    public static String randomText(final int max) {
        final boolean useLetters = true;
        final boolean useNumbers = false;
        return RandomStringUtils.random(max, useLetters, useNumbers);
    }

    public static String randomText(final int min, final int max) {
        return randomText(randomInt(min, max));
    }

    public static String randomNullableText(final int max) {
        return randomBool() ? randomText(max) : null;
    }

    public static String randomEmail() {
        return String.format("%s@%s.%s", randomText(15), randomText(6), randomText(3));
    }

    @SneakyThrows
    public static URL randomUrl() {
        return new URI(String.format("https://%s.com", randomText(15)))
                .toURL();
    }

    public static LocalDateTime randomPastDateTime() {
        final Instant now = Instant.now();
        final Instant past = Instant.now().minusSeconds(
                (long) randomInt(10, 1000) * 60 * 24);
        return randomDateTimeBetween(past, now);
    }

    public static LocalDateTime randomFutureDateTime() {
        final Instant now = Instant.now();
        final Instant future = Instant.now().plusSeconds(
                (long) randomInt(10, 1000) * 60 * 24);
        return randomDateTimeBetween(now, future);
    }

    public static LocalDateTime randomDateTimeBetween(
            final Instant startInclusive, final Instant endExclusive
    ) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long randomSeconds = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(randomSeconds),
                ZoneOffset.UTC
        );
    }

    public static JwtAuthenticationToken getMockJwtToken(final String subject) {
        final Jwt jwt = Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claims(
                        claims -> {
                            claims.put(
                                    User.EMAIL_VERIFIED_CLAIM,
                                    true
                            );
                        }
                )
                .subject(subject)
                .build();
        return new JwtAuthenticationToken(jwt);
    }

    @SneakyThrows
    public static String randomJwt(final String subject) {
        final SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);

        final JWSSigner signer = new MACSigner(sharedSecret);

        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
        claimsSet.subject(subject);
        claimsSet.issuer("http://example.com");
        claimsSet.issueTime(new Date(new Date().getTime() + 60 * 1000));
        claimsSet.expirationTime(new Date(new Date().getTime() + 60 * 1000));

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256), claimsSet.build());

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public static <T> ApiPage<T> singleApiPage(final List<T> content) {
        return new ApiPage<>(
                content.size(),
                content.size(),
                0,
                0,
                0,
                content
        );
    }

    public static <T> ApiPage<T> multiLastApiPage(
            final List<T> content,
            final int pageIndex
    ) {
        return new ApiPage<>(
                (pageIndex * 10L) + content.size(),
                content.size(),
                pageIndex,
                0,
                pageIndex,
                content
        );
    }
}
