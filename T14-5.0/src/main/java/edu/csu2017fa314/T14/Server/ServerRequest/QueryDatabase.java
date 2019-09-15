package edu.csu2017fa314.T14.Server.ServerRequest;

public class QueryDatabase {

    private String query;
    private String id;

    public QueryDatabase(String query, String id){
        this.query = query;
        this.id = id;
    }

    public String getQuery(){
        return this.query;
    }

    public void setQuery(String queryRequest){
        this.query = queryRequest;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String toString() {
        return "Request{" +
                "query='" + this.query + '\'' +
                "id='" + this.id + '\'' +
                '}';
    }

}
