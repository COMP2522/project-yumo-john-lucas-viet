package org.myProject.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a utility class that provides methods to interact with
 * our Galaga MongoDB database
 *
 * @author John2T
 */
public class DB {
  private final MongoDatabase database;
  private final MongoCollection<Document> usersCollection;
  private final ExecutorService executorService;
  
  /**
   * Initializes a new instance of the DB class.
   */
  public DB() {
    ConnectionString connectionString = new ConnectionString("mongodb+srv://SleepyPanda:Mancool2@galaga.5q3f2jk.mongodb.net/?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
      .applyConnectionString(connectionString)
      .serverApi(ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build())
      .build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.database = mongoClient.getDatabase("Galaga");
    this.usersCollection = database.getCollection("Users");
    this.executorService = Executors.newSingleThreadExecutor();
  }
  
  /**
   * Validates the user's name to ensure there are no duplicates
   * @param name the name of the user to validate.
   * @return true if the name is unique, otherwise false.
   */
  public boolean validateName(String name) {
    Document user = usersCollection.find(new Document("User", name)).first();
    return user == null;
  }
  
  /**
   * Adds or updates a key-value pair in the database for a user.
   * @param key the key to add or update.
   * @param value the value to add or update.
   */
  public void put(String key, Object value) {
    CompletableFuture.runAsync(() -> {
      Document filter = new Document("User", key);
      Document update = new Document("$set", new Document("Score", value));
      UpdateOptions options = new UpdateOptions().upsert(true);
      usersCollection.updateOne(filter, update, options);
    }, executorService);
  }
  
  /**
   * Asynchronously retrieves the top 5 scores from the database.
   * @return a CompletableFuture that represents the top 5 scores.
   */
  public CompletableFuture<List<Document>> getTop5ScoresAsync() {
    return CompletableFuture.supplyAsync(() -> usersCollection
      .find()
      .projection(new Document("User", 1).append("Score", 1))
      .sort(new Document("Score", -1))
      .limit(5)
      .into(new ArrayList<>()), executorService);
  }
  
  /**
   * Synchronously retrieves the top 5 scores from the database.
   * @return the top 5 scores.
   * @throws InterruptedException if the current thread is interrupted while waiting.
   * @throws ExecutionException if an exception occurs while computing the result.
   */
  public List<Document> getTop5Scores() throws InterruptedException, ExecutionException {
    CompletableFuture<List<Document>> future = new DB().getTop5ScoresAsync();
    List<Document> result = future.get();
    return result;
  }
  
  
  /**
   * Gets the MongoDatabase instance associated with this DB instance.
   * @return the MongoDatabase instance.
   */
  public MongoDatabase getDatabase() {
    return this.database;
  }
  
  /**
   * Shuts down ExecutorService associated with the DB
   */
  public void close() {
    executorService.shutdown();
  }
}
