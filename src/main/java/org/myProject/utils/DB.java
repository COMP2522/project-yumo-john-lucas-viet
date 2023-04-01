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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DB {
  private MongoDatabase database;
  private MongoCollection<Document> usersCollection;
  private ExecutorService executorService;
  
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
  
  public boolean validateName(String name) {
    Document user = usersCollection.find(new Document("User", name)).first();
    return user == null;
  }
  
  public void put(String key, Object value) {
    CompletableFuture.runAsync(() -> {
      Document filter = new Document("User", key);
      Document update = new Document("$set", new Document("Score", value));
      UpdateOptions options = new UpdateOptions().upsert(true);
      usersCollection.updateOne(filter, update, options);
    }, executorService);
  }
  
  public CompletableFuture<List<Document>> getTop5ScoresAsync() {
    return CompletableFuture.supplyAsync(() -> usersCollection
      .find()
      .projection(new Document("User", 1).append("Score", 1))
      .sort(new Document("Score", -1))
      .limit(5)
      .into(new ArrayList<>()), executorService);
  }
  
  
  
  public MongoDatabase getDatabase() {
    return this.database;
  }
  
  public void close() {
    executorService.shutdown();
  }
}
