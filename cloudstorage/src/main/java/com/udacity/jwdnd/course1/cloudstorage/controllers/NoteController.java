package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping()
    public String postNote(Authentication auth, @ModelAttribute Note newNote, Model model){
        var username = auth.getName();
        if(newNote.getNoteId() == null){
            noteService.createNote(newNote, username);
        }
        else{
            noteService.updateNote(newNote);
        }
        model.addAttribute("notes", noteService.getNotes(username));
        handleNoteAttribute(model);
        return "home";
    }
/*    @GetMapping("note/findAll")
    public String getNotes(Authentication auth, Model model){
        var username = auth.getName();
        model.addAttribute("notes", noteService.getNotes(username));
        model.addAttribute("noteActive", true);
        return "home";
    }*/
    @GetMapping("/deleteNote/{deletedNoteId}")
    public String deleteNote(Authentication auth, @PathVariable(value = "deletedNoteId") Integer deletedNoteId, Model model){
        noteService.deleteNote(deletedNoteId);
        model.addAttribute("notes", noteService.getNotes(auth.getName()));
        handleNoteAttribute(model);
        return "home";
    }
    private void handleNoteAttribute(Model model){
        model.addAttribute("noteActive", "active show");
        model.addAttribute("noteSelected", "true");
        model.addAttribute("fileActive", "");
        model.addAttribute("fileSelected", "false");
    }
}
