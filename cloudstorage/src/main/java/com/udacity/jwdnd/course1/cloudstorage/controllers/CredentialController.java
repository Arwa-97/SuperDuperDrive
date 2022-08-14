package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }
    @PostMapping()
    public String credential(Authentication auth, @ModelAttribute Credential newCredential, Model model){
        var username = auth.getName();
        if(newCredential.getCredentialId() == null){
            credentialService.createCredential(newCredential, username);
        }
        else{
            credentialService.updateCredential(newCredential);
        }
        model.addAttribute("credentials", credentialService.getCredentials(username));
        handleCredentialAttribute(model);
        return "home";
    }

    @GetMapping("/deleteCredential/{deletedCredentialId}")
    public String deleteCredential(Authentication auth, @PathVariable(value = "deletedCredentialId") Integer deletedCredentialId, Model model){
        credentialService.deleteCredential(deletedCredentialId);
        model.addAttribute("credentials", credentialService.getCredentials(auth.getName()));
        handleCredentialAttribute(model);
        return "home";
    }

    private void handleCredentialAttribute(Model model){
        model.addAttribute("credentialActive", "active show");
        model.addAttribute("credentialSelected", "true");
        model.addAttribute("fileActive", "");
        model.addAttribute("fileSelected", "false");
    }
}
