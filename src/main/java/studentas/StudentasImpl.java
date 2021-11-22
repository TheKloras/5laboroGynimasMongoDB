package studentas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class StudentasImpl implements Studentas{
    private int pazymejimoNr;
    private String vardas;
    private String pavarde;
    private int gimimoData;
    private String grupe;
    private double vidurkis;

    public StudentasImpl(int pazymejimoNr, String vardas, String pavarde, int gimimoData, String grupe, double vidurkis){
        try{
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase StudentasDB = mongoClient.getDatabase("studentas");
            try{
                StudentasDB.createCollection("studentas");
            }catch (Exception e){
                //System.out.println(e);
            }finally {
                MongoCollection<Document> studentasCollection = StudentasDB.getCollection("studentas");
                Document Studentas = new Document("_id", pazymejimoNr);
                Studentas.append("pazymejimoNr", pazymejimoNr)
                        .append("vardas", vardas)
                        .append("pavarde", pavarde)
                        .append("gimimoData", gimimoData)
                        .append("grupe", grupe)
                        .append("vidurkis", vidurkis);
                studentasCollection.insertOne(Studentas);
            }
        }
        catch (Exception e){
            System.out.println("---------KLAIDA!---------");
            System.out.println(e);
        }
    }

    @Override
    public int getPazymejimoNr() {
        try{
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase StudentasDB = mongoClient.getDatabase("studentas");
            try{
                StudentasDB.createCollection("studentas");
            }catch (Exception e){
                //System.out.println(e);
            }finally {
                MongoCollection<Document> studentasCollection = StudentasDB.getCollection("studentas");
                studentasCollection.find(new Document("_id", 1000));
            }
        }
        catch (Exception e){
            System.out.println("---------KLAIDA!---------");
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public void setPazymejimoNr(int pazymejimoNr) {

    }

    @Override
    public String getVardas() {
        return null;
    }

    @Override
    public void setVardas(String vardas) {

    }

    @Override
    public String getPavarde() {
        return null;
    }

    @Override
    public void setPavarde(String pavarde) {

    }

    @Override
    public int getGimimoData() {
        return 0;
    }

    @Override
    public void setGimimoData() {

    }

    @Override
    public String getGrupe() {
        return null;
    }

    @Override
    public void setGrupe(String grupe) {

    }

    @Override
    public double getVidurkis() {
        return 0;
    }

    @Override
    public void setVidurkis(double vidurkis) {

    }
}

