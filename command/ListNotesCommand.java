package command;

import java.io.IOException;

import model.Note;
import network.ApiClient;

public class ListNotesCommand implements Command {
  private final ApiClient apiClient;

  public ListNotesCommand(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public void execute() {
    try {
      Note[] notes = apiClient.fetchNotes();
      if (notes.length == 0) {
        System.out.println("No notes available.");
      } else {
        for (Note note : notes) {
          System.out.println(note);
        }
      }
    } catch (IOException | InterruptedException e) {
      System.err.println("❌ Error fetching notes: " + e.getMessage());
      System.err.println("💡 Coba pastikan server PHP-mu sedang berjalan di " + apiClient.getBaseUrl());
    } catch (Exception e) {
      System.err.println("❌ Unexpected error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
