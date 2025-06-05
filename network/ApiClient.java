package network;

import com.google.gson.Gson;
import model.Note;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

  public String getBaseUrl() {
    return util.Config.getInstance().get("API_BASE_URL", "http://127.0.0.1:8000");
  }

  public Note[] fetchNotes() throws Exception {
    // Ensure the server is running at the specified URL
    URL url = new URL(getBaseUrl() + "/notes");

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setConnectTimeout(5000); // 5 seconds timeout
    conn.setReadTimeout(5000); // 5 seconds timeout
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));

    StringBuilder response = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null)
      response.append(line);
    in.close();

    Gson gson = new Gson();
    NotesResponse parsed = gson.fromJson(response.toString(), NotesResponse.class);
    return parsed.notes;
  }

  public void createNote(Note note) throws Exception {
    // Ensure the server is running at the specified URL
    URL url = new URL(getBaseUrl() + "/notes");

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setConnectTimeout(5000); // 5 seconds timeout
    conn.setReadTimeout(5000); // 5 seconds timeout
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestMethod("POST");

    Gson gson = new Gson();
    String jsonInputString = gson.toJson(note);

    // the request schema is like this:
    // {
    // "note": {
    // "date": "2023-10-01",
    // "content": "This is a note"
    // }
    // }
    jsonInputString = "{\"note\": " + jsonInputString + "}";

    conn.setDoOutput(true);
    conn.getOutputStream().write(jsonInputString.getBytes("UTF-8"));

    int responseCode = conn.getResponseCode();
    if (responseCode != HttpURLConnection.HTTP_OK) {
      throw new RuntimeException("Failed to create note: " + responseCode);
    }
  }

  private static class NotesResponse {
    Note[] notes;
  }
}
