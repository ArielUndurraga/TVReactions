package cl.usach.lucene;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

import java.util.Arrays;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import cl.usach.tvreactions.entities.*;
import cl.usach.tvreactions.repository.*;
import cl.usach.tvreactions.rest.*;


import java.io.IOException;

@Component
@EnableScheduling
public class Searcher {
	
	@Autowired
	ChannelRepository channelRepo;
	
    public void search() throws IOException, ParseException {
   
        // 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);

        // 1. create the index
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

        IndexWriter w = new IndexWriter(index, config);


        // 3. Obtain Documents and add them.
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<org.bson.Document> collection = database.getCollection("tweet");

        MongoCursor<org.bson.Document> cursor = collection.find().iterator();

        while (cursor.hasNext())
        {
            org.bson.Document d = cursor.next();
            String tweetID = d.get("id").toString();
            String mongoID = d.get("_id").toString();
            String text = d.get("text").toString();
            //System.out.println(text);
            String[] date = d.get("date").toString().split(" ");
            String day = date[0];
            String month = date[1];
            String fecha = date[2];
            String time = date[3];
            String year = date[5];

            org.apache.lucene.document.Document docLucene = new org.apache.lucene.document.Document();
            docLucene.add(new StringField("id", tweetID, Field.Store.YES));
            docLucene.add(new StringField("_id", mongoID, Field.Store.YES));
            docLucene.add(new TextField("tweet", text, Field.Store.YES));
            docLucene.add(new StringField("day", day, Field.Store.YES));
            docLucene.add(new StringField("month", month, Field.Store.YES));
            docLucene.add(new StringField("fecha", fecha, Field.Store.YES));
            docLucene.add(new StringField("time", time, Field.Store.YES));
            docLucene.add(new StringField("year", year, Field.Store.YES));
            w.addDocument(docLucene);
        }
        w.close();

        // 2. query
        
        
        
        Iterable<Channel> all = channelRepo.findAll();
        System.out.println(all);
        
        for (Iterator<Channel> i = all.iterator(); i.hasNext();){
        	Channel item = i.next();
        	String query = "("+item.getChannelName()+" AND canal) "
        			+ "OR ("+item.getChannelName()+" AND tv) "
        					+ "OR ("+item.getChannelName()+" AND television)";
        	Query q = new QueryParser(Version.LUCENE_40, "tweet", analyzer).parse(query);
        	int hitsPerPage = 1000000;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            item.setChannelFrequency(hits.length);
            channelRepo.save(item);
            reader.close();
        	
        }
        
        //String querystr = args.length > 0 ? args[0] : "mega AND canal";
        
        String querystr = "mega AND canal";

        // the "title" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        Query q = new QueryParser(Version.LUCENE_40, "tweet", analyzer).parse(querystr);

        // 3. search
        int hitsPerPage = 1000000;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i)
        {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("id") + "\t" + d.get("tweet"));
        }
        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
    }
    
    
    @Scheduled(cron="0 0 3 * * *")
    public void invertedIndex(){
    	try {
			search();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }
}