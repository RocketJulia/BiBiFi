package de.tz.demo.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
