package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private CredentialService credentialService;
    private FileUploadService fileUploadService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileUploadService fileUploadService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public String home(Authentication auth, Model model){
        var username = auth.getName();
        model.addAttribute("notes", noteService.getNotes(username));
        model.addAttribute("credentials", credentialService.getCredentials(username));
        model.addAttribute("files", fileUploadService.getFiles(username));
        model.addAttribute("fileActive", "active show");
        model.addAttribute("fileSelected", "true");
        return "home";
    }
}
