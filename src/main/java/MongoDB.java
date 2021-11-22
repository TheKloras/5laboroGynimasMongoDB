import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.Scanner;

public class MongoDB {
    public static void MongoDB(){
        System.out.println("MongoDB");
        try{
            MongoClient mongoClient = MongoClients.create("mongodb://thekloras:QL2BFccHFsECDjU@cluster0-shard-00-00.limus.mongodb.net:27017,cluster0-shard-00-01.limus.mongodb.net:27017,cluster0-shard-00-02.limus.mongodb.net:27017/Cluster0?ssl=true&replicaSet=atlas-lu16yr-shard-0&authSource=admin&retryWrites=true&w=majority");
            MongoDatabase StudentasDB = mongoClient.getDatabase("studentas");
            try{
                StudentasDB.createCollection("studentas");
            }catch (Exception e){
                System.out.println(e);
            }finally {
                MongoCollection<Document> studentasCollection = StudentasDB.getCollection("studentas");
                StudentasDB.drop();
                kurtiStudenta(studentasCollection, 1000, "Jonas", "Jonaitis", 20000220, "J21/2", 8.8);
                kurtiStudenta(studentasCollection, 1001, "Povilas", "Nemenka", 19990201, "J21/2", 2);
                kurtiStudenta(studentasCollection, 1002, "Rolandas", "Gapsys", 19981005, "J21/1", 7);
                kurtiStudenta(studentasCollection, 1003, "Jonas", "Maciulis", 19880205, "J21/1", 3.9);
                kurtiStudenta(studentasCollection, 1004, "Vaidas","Grincevicius", 19950505, "J21/2", 10);
                kurtiStudenta(studentasCollection, 1005, "Petras", "Grazulis", 20000825, "J21/2", 3);
                kurtiStudenta(studentasCollection, 1006, "Gitanas", "Nuseda", 19960505, "J21/1", 5);
                kurtiStudenta(studentasCollection, 1007, "Andrius","Andraitis", 19980405, "J21/2", 6);
                kurtiStudenta(studentasCollection, 1008, "Andrius", "Guoga", 19980405, "J21/2", 6.6);
                kurtiStudenta(studentasCollection, 1009, "Andrius","Lapinas", 19950606, "J21/1", 9);

                spausdinti(studentasCollection);

                String grupe;
                Scanner S = new Scanner(System.in);
                System.out.println("Įveskite grupę kurios skaičiuoti studentus");
                grupe = S.next();
                System.out.println(grupe + " grupėje yra " + gautiFakultetoStudentus(studentasCollection,grupe) + " mokiniai");

                spausdintiPagalVidurki(studentasCollection);

                System.out.println("----------------------------------------");

                studentasCollection.deleteMany(Filters.lt("vidurkis", 4));
                spausdintiPagalVidurki(studentasCollection);
            }
        }
        catch (Exception e){
            System.out.println("---------KLAIDA!---------");
            System.out.println(e);
        }
    }
    public static void kurtiStudenta(MongoCollection<Document> studentasCollection, int pazymejimoNr, String vardas, String pavarde, int gimimoData, String grupe, double vidurkis){
                try{
                    Document Studentas = new Document("_id", pazymejimoNr);
                    Studentas.append("pazymejimoNr", pazymejimoNr)
                            .append("vardas", vardas)
                            .append("pavarde", pavarde)
                            .append("gimimoData", gimimoData)
                            .append("grupe", grupe)
                            .append("vidurkis", vidurkis);
                    studentasCollection.insertOne(Studentas);
                }catch (Exception e){
                    System.out.println("Klaida kuriant studentą " + pazymejimoNr);
                }
    }
    public static void spausdinti(MongoCollection<Document> studentasCollection){
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        studentasCollection.find(new Document()).forEach(printBlock);
    }
    public static int gautiFakultetoStudentus(MongoCollection<Document> studentasCollection, String grupe){
        return (int) studentasCollection.countDocuments(Filters.eq("grupe",grupe));
    }
    public static void spausdintiPagalVidurki(MongoCollection<Document> studentasCollection){
        FindIterable<Document> cursor = studentasCollection.find().sort(new BasicDBObject("vidurkis",-1));
        MongoCursor<Document> iterator = cursor.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
