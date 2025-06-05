package command;

import java.io.IOException;
import java.util.Scanner;

import model.Note;
import network.ApiClient;

public class CreateNoteCommand implements Command {
  private final ApiClient apiClient;
  private Note newNote;

  public CreateNoteCommand(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public void setNewNote(Note newNote) {
    this.newNote = newNote;
  }

  @Override
  public void execute() {
    // Ensure newNote is set before attempting to create it, creating a note if it
    // is null
    if (newNote == null) {
      newNote = new Note();
    }

    // Prompt user for note details
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter note date: ");
    String title = scanner.nextLine();
    System.out.print("Enter note content: ");
    String content = scanner.nextLine();

    newNote.date = title;
    newNote.content = content;

    try {
      if (newNote == null) {
        System.out.println("No note data available.");
        return;
      }
      apiClient.createNote(newNote);
      System.out.println("Note created successfully.");
    } catch (IOException | InterruptedException e) {
      System.err.println("❌ Error creating note: " + e.getMessage());
      System.err.println("💡 Coba pastikan server PHP-mu sedang berjalan di " + apiClient.getBaseUrl());
    } catch (Exception e) {
      System.err.println("❌ Unexpected error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
