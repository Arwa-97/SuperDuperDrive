package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public Integer createCredential(Credential credential, String username){
        var user = userService.getUserInfo(username);
        credential.setUserId(user.getUserid());
        var id = credentialMapper.insertCredential(credential);
        return id;
    }
    private String encryptPassword(String password){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        return encryptedPassword;
    }
    private String decryptPassword(String password){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        return decryptedPassword;
    }
    public void updateCredential(Credential credential){
        credentialMapper.updateCredential(credential);
    }

    public Credential getCredentialById(Integer credentialId){
        var credential= credentialMapper.getCredentialById(credentialId);
        var decryptedPassword = decryptPassword(credential.getPassword());
        credential.setPassword(decryptedPassword);
        return credential;
    }
    public List<Credential> getCredentials(String username){
        var user = userService.getUserInfo(username);
        var credentials = credentialMapper.getCredential(user.getUserid());
        for (var credential: credentials) {
            var password = credential.getPassword();
            var encryptedPassword = encryptPassword(password);
            credential.setPassword(encryptedPassword);
            var decryptedPassword = decryptPassword(password);
            credential.setDecryptedPassword(decryptedPassword);
        }
        return credentials;
    }

    public void deleteCredential(Integer deletedCredentialId){
        this.credentialMapper.deleteCredential(deletedCredentialId);
    }
}
