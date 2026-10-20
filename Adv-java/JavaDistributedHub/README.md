# Java Distributed Systems & Enterprise Core Hub

This project is a complete reference implementation covering:
1. **RMI Architecture & Registry**: Client/Server distributed communication using `java.rmi.*`, remote stubs, dynamic registry binding, and invocation over TCP.
2. **Naming & Directory Services & JNDI**: Overview of `javax.naming.*`, InitialContext, SPI layer, and LDAP/RMI registry integration.
3. **Object Serialization**: `UserProfile.java` implementing `Serializable`, demonstrating `ObjectOutputStream`, `ObjectInputStream`, and `transient` attributes.
4. **Internationalization (I18N) & Localization (L10N)**: `ResourceBundle`, `Locale`, `NumberFormat`, and `DateFormat` with US, French, German, and Hindi bundles.

### Build and Run Instructions

```bash
# Compile using Maven:
mvn clean compile

# 1. Run Serialization Demo:
mvn exec:java -Dexec.mainClass="com.javadistributed.serialization.SerializationDemo"

# 2. Run I18N Demo:
mvn exec:java -Dexec.mainClass="com.javadistributed.i18n.InternationalizationDemo"

# 3. Run RMI Server (Terminal 1):
mvn exec:java -Dexec.mainClass="com.javadistributed.rmi.RmiServerApp"

# 4. Run RMI Client (Terminal 2):
mvn exec:java -Dexec.mainClass="com.javadistributed.rmi.RmiClientApp"
```
