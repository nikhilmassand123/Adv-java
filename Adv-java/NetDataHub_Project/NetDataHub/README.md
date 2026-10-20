# NetDataHub: Java Network & Database Project

This project combines:
1. **Swing GUI & Pluggable Look and Feel**: `SwingDemo.java` demonstrating TextFields, Buttons, ToggleButtons, Checkboxes, RadioButtons, and UIManager Look & Feel.
2. **Database Programming (`java.sql` & JDBC)**: `DatabaseService.java` with H2 in-memory DB, `PreparedStatement`, `Statement`, `ResultSet`, and metadata extraction.
3. **Network Programming (`java.net`)**: Multithreaded TCP `ServerApp.java` and interactive `ClientApp.java`.
4. **Custom Protocol & Content Handlers**: `NetDbURLHandler.java`, `CustomContentHandler.java`, and `ProtocolRunner.java` executing URL-based interactions over custom protocol `netdb://`.

### Compilation & Execution

```bash
# Compile with Maven
mvn clean compile

# Or compile manually with JDBC driver on classpath:
javac -d bin src/main/java/com/netdatahub/**/*.java
```
