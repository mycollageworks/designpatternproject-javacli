package command;

public class ExitCommand implements Command {
  @Override
  public void execute() {
    System.out.println("👋 Exiting NoteManager CLI...");
    System.exit(0);
  }
}
