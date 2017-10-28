package cl.usach.twitter.streaming;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.JSONObject;
import twitter4j.json.DataObjectFactory;

import com.mongodb.*;
import com.mongodb.util.JSON;

@Component
@EnableScheduling
public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	int iter_stop;

	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException{
		Mongo m = new Mongo("127.0.0.1");
		DB db = m.getDB("test");
		final DBCollection twet = db.getCollection("tweet");
		iter_stop = 0;

		StatusListener listener = new StatusListener() {

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) {
				if (iter_stop==30000){
					return;
				}
				//System.out.println(status.getId());
				//System.out.println(status.getText());
				String textFixed = status.getText().replaceAll("\"", "\\\\\"");
				String comment = "{\"id\" : \""+status.getId()+"\","
						+ " \"text\" : \""+textFixed+"\","
								+ " \"location\" : \""+status.getGeoLocation()+"\","
										+ " \"date\" : \""+status.getCreatedAt()+"\"}";
				//String tweet = DataObjectFactory.getRawJSON(status);
				DBObject doc = (DBObject)JSON.parse(comment);
				System.out.println(comment);
				twet.insert(doc);
				iter_stop+=1;
			}
		};

		
		FilterQuery fq = new FilterQuery();
		String[] language = new String[]{"es"};
		
		fq.track(keywords.toArray(new String[0])).language(language);
		//fq.language(new String[]{"es"});

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	@Scheduled(cron="0 0 10 * * *")
	@Scheduled(cron="0 0 22 * * *")
	public void extractTweets() {
		try {
			new TwitterStreaming().init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
	