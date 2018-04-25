package com.mycompany.crawler;

import java.util.List;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.HttpRequest;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

    //https://github.com/BloodShura/RedditCrawlerSimple

public class Client {
    private final String userName;
    private final String password;
    private final String clientID;
    private final String clientSecret;
    
    private UserAgent userAgent;
    
    //Credentials
    private Credentials credential;
    // This is what really sends HTTP requests
    private NetworkAdapter adapter;
    // Authenticate and get a RedditClient instance
    private RedditClient redditClient;
    
    public Client(String userName, String password, String clientID, String clientSecret) {
        this.userName = userName;
        this.password = password;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public Credentials getCredential() {
        return credential;
    }

    public void setCredential(Credentials credential) {
        this.credential = credential;
    }

    public NetworkAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(NetworkAdapter adapter) {
        this.adapter = adapter;
    }

    public RedditClient getRedditClient() {
        return redditClient;
    }

    public void setRedditClient(RedditClient reddit) {
        this.redditClient = reddit;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgent userAgent) {
        this.userAgent = userAgent;
    }
    
    public void createUserAgent(){
        //falta pegar os valores corretos para criar o agente
        this.setUserAgent(new UserAgent("bot", "com.mycompany.crawler", "DEV", "renanvh"));
    }
    
    public void createCredential(){
        this.setCredential(Credentials.script(userName, password, clientID, clientSecret));
    }
    
    public void procedure(){
        this.createCredential();
        this.createUserAgent();
        this.setAdapter(new OkHttpNetworkAdapter(this.getUserAgent()));
        this.setRedditClient(OAuthHelper.automatic(this.getAdapter(), this.getCredential()));
        this.collectingPosts();
    }
    
    public void collectingPosts(){   
        DefaultPaginator.Builder<Submission, SubredditSort> paginator = this.getRedditClient().subreddit("java").posts().sorting(SubredditSort.TOP).timePeriod(TimePeriod.ALL);
        for (Listing<Submission> s : paginator.build()){
            List<Submission> subList = s.getChildren();
            for (Submission sub : subList){
                //cada sub é um post do reddit
                System.out.println(sub.getAuthor());
            }
        }
        
        //Listing<Submission> topsJava = paginator.next();
        //Subreddit sr = this.getRedditClient().subreddit("java").posts().sorting(SubredditSort.TOP).timePeriod(TimePeriod.ALL);
        //System.out.println(sr.getSubscribers());
    }
    
}
