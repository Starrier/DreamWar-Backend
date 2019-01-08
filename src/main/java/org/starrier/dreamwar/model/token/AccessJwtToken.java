package org.starrier.dreamwar.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

/**
 * Raw representation of JWT Token
 *
 * @Author Starrier
 * @Time 2018/6/16.
 */
public class AccessJwtToken implements JwtToken{

    private final String rawToken;

    @JsonIgnore
    private Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken=token;
        this.claims=claims;
    }

    @Override
    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
