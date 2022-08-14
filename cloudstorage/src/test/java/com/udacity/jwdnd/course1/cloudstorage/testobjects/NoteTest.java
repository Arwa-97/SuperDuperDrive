package com.udacity.jwdnd.course1.cloudstorage.testobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteTest {
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "noteModalBtn")
    private WebElement noteSubmit;
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;
    @FindBy(id = "showNote")
    private WebElement showNote;

    public NoteTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getNoteTitle() {
        return noteTitle.getText();
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle.sendKeys(noteTitle);
    }
    public void clickNoteTitle() {
        this.noteTitle.click();
    }

    public String getNoteDescription() {
        return noteDescription.getText();
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription.sendKeys(noteDescription);
    }
    public void clickNoteDescription() {
        this.noteDescription.click();
    }

    public void submitNote() {
        noteSubmit.click();
    }
    public void clickNoteTab() {
        noteTab.click();
    }
    public void clickShowNote() {
        showNote.click();
    }
    public void clearNoteTitle() {
        this.noteTitle.clear();
    }
    public void clearNoteDescription() {
        this.noteDescription.clear();
    }


}
