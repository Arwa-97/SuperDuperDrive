package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public Integer createNote(Note note, String username){
        var user = userService.getUserInfo(username);
        note.setUserId(user.getUserid());
        var id = noteMapper.insertNote(note);
        return id;
    }
    public void updateNote(Note note){
        noteMapper.updateNote(note);
    }

    public Note getNoteById(Integer noteId){
        return noteMapper.getNoteById(noteId);
    }
    public List<Note> getNotes(String username){
        var user = userService.getUserInfo(username);
        var notes = noteMapper.getNotes(user.getUserid());
        return notes;
    }

    public void deleteNote(Integer deletedNoteId){
        this.noteMapper.deleteNote(deletedNoteId);
    }

}
