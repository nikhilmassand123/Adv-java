package com.javadistributed.serialization;

import com.javadistributed.model.UserProfile;

import java.io.*;

public class SerializationDemo {
    public static void main(String[] args) {
        UserProfile user = new UserProfile(101, "alex_dev", "ADMIN", "secret_token_9988");
        String filename = "user_profile.ser";

        System.out.println("--- 1. Object Serialization ---");
        System.out.println("Original Object: " + user);

        // Serialize Object to File
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(user);
            System.out.println("Object successfully serialized to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize Object from File
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            UserProfile deserializedUser = (UserProfile) ois.readObject();
            System.out.println("Deserialized Object: " + deserializedUser);
            System.out.println("Notice: sessionToken is null because it was marked transient!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            new File(filename).delete();
        }
    }
}
